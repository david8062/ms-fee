package com.IusCloud.msFees.core.features.fee.domain.model;

import com.IusCloud.msFees.core.base.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "time_entries")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeEntryEntity extends BaseModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fee_id", nullable = false)
    private FeeEntity fee;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "hours", nullable = false)
    private Integer hours = 0;

    @Column(name = "minutes", nullable = false)
    private Integer minutes = 0;

    @Column(name = "work_date", nullable = false)
    private LocalDate workDate;
}
