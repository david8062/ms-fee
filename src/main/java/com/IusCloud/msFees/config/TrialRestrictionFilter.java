package com.IusCloud.msFees.config;

import com.IusCloud.msFees.config.security.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Backstop de "trial vencido / cuenta bloqueada" para las escrituras de honorarios.
 *
 * Lee el estado de los claims del JWT ({@code tenantStatus}, {@code trialEndsAt}),
 * que auth ya emite, sin acoplar servicios. Espeja el filtro homónimo de auth y
 * legal-core. {@code trialEndsAt} es un timestamp fijo del token, así que un trial
 * que vence a mitad de sesión se detecta aunque el token no se haya refrescado.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TrialRestrictionFilter extends OncePerRequestFilter {

    private static final Set<String> WRITE_METHODS = Set.of(
            HttpMethod.POST.name(),
            HttpMethod.PUT.name(),
            HttpMethod.PATCH.name(),
            HttpMethod.DELETE.name()
    );

    private final JwtService jwtService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        if (!WRITE_METHODS.contains(request.getMethod().toUpperCase())) {
            return true;
        }
        String path = request.getServletPath();
        return path.startsWith("/api/v1/onboard")
                || path.startsWith("/api/v1/internal/");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String status;
        Instant trialEndsAt;
        try {
            Claims claims = jwtService.extractClaims(authHeader.substring(7));
            status = claims.get("tenantStatus", String.class);
            String trial = claims.get("trialEndsAt", String.class);
            trialEndsAt = (trial != null && !trial.isBlank()) ? Instant.parse(trial) : null;
        } catch (Exception ex) {
            filterChain.doFilter(request, response);
            return;
        }

        if (isAccessBlocked(status, trialEndsAt)) {
            writeAccessBlockedResponse(request, response, status);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isAccessBlocked(String status, Instant trialEndsAt) {
        if (status == null) {
            return false;
        }
        if (status.equals("SUSPENDED") || status.equals("CANCELLED") || status.equals("PAST_DUE")) {
            return true;
        }
        return status.equals("TRIAL")
                && trialEndsAt != null
                && Instant.now().isAfter(trialEndsAt);
    }

    private void writeAccessBlockedResponse(HttpServletRequest request, HttpServletResponse response,
                                            String status) throws IOException {
        response.setStatus(HttpStatus.PAYMENT_REQUIRED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String message = switch (status == null ? "" : status) {
            case "SUSPENDED" -> "Your account has been suspended. Please contact support.";
            case "CANCELLED" -> "Your account has been cancelled.";
            case "PAST_DUE"  -> "Your subscription payment is overdue. Access is read-only until payment is settled.";
            default          -> "Your trial has ended. Upgrade your plan to continue making changes.";
        };

        String body = String.format(
                "{\"timestamp\":\"%s\",\"status\":402,\"error\":\"Access Blocked\"," +
                "\"message\":\"%s\",\"tenantStatus\":\"%s\",\"path\":\"%s\"}",
                LocalDateTime.now(), message, status, request.getServletPath()
        );
        response.getWriter().write(body);
    }
}
