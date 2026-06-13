-- ================================================================
-- ms-fees schema – Phase 1
-- ================================================================

CREATE TABLE IF NOT EXISTS services (
    id                  UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id           UUID        NOT NULL,
    name                VARCHAR(255) NOT NULL,
    slug                VARCHAR(255),
    short_description   TEXT,
    full_description    TEXT,
    category            VARCHAR(50),
    pricing_strategy    VARCHAR(50) NOT NULL,
    base_amount         NUMERIC(19,2),
    percentage_amount   NUMERIC(5,2),
    currency            VARCHAR(10) NOT NULL DEFAULT 'COP',
    duration_minutes    INTEGER,
    is_free             BOOLEAN     NOT NULL DEFAULT FALSE,
    is_public           BOOLEAN     NOT NULL DEFAULT FALSE,
    is_active           BOOLEAN     NOT NULL DEFAULT TRUE,
    created_at          TIMESTAMPTZ,
    updated_at          TIMESTAMPTZ,
    deleted_at          TIMESTAMPTZ
);

CREATE INDEX idx_services_tenant ON services(tenant_id) WHERE deleted_at IS NULL;

-- ----------------------------------------------------------------

CREATE TABLE IF NOT EXISTS fees (
    id                  UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id           UUID        NOT NULL,
    client_id           UUID        NOT NULL,
    case_id             UUID,
    service_id          UUID        REFERENCES services(id),
    assigned_user_id    UUID        NOT NULL,
    description         TEXT,
    fee_type            VARCHAR(50) NOT NULL,
    agreed_amount       NUMERIC(19,2) NOT NULL,
    currency            VARCHAR(10) NOT NULL DEFAULT 'COP',
    due_date            DATE,
    status              VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    notes               TEXT,
    is_active           BOOLEAN     NOT NULL DEFAULT TRUE,
    created_at          TIMESTAMPTZ,
    updated_at          TIMESTAMPTZ,
    deleted_at          TIMESTAMPTZ
);

CREATE INDEX idx_fees_tenant         ON fees(tenant_id)   WHERE deleted_at IS NULL;
CREATE INDEX idx_fees_client         ON fees(client_id)   WHERE deleted_at IS NULL;
CREATE INDEX idx_fees_case           ON fees(case_id)     WHERE deleted_at IS NULL;
CREATE INDEX idx_fees_assigned_user  ON fees(assigned_user_id) WHERE deleted_at IS NULL;

-- ----------------------------------------------------------------

CREATE TABLE IF NOT EXISTS payments (
    id              UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    fee_id          UUID        NOT NULL REFERENCES fees(id),
    amount          NUMERIC(19,2) NOT NULL,
    payment_date    DATE        NOT NULL,
    payment_method  VARCHAR(50) NOT NULL,
    reference       VARCHAR(255),
    attachment_url  VARCHAR(500),
    notes           TEXT,
    is_active       BOOLEAN     NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMPTZ,
    updated_at      TIMESTAMPTZ,
    deleted_at      TIMESTAMPTZ
);

CREATE INDEX idx_payments_fee ON payments(fee_id) WHERE deleted_at IS NULL;

-- ----------------------------------------------------------------

CREATE TABLE IF NOT EXISTS time_entries (
    id          UUID    PRIMARY KEY DEFAULT gen_random_uuid(),
    fee_id      UUID    NOT NULL REFERENCES fees(id),
    user_id     UUID    NOT NULL,
    description TEXT,
    hours       INTEGER NOT NULL DEFAULT 0,
    minutes     INTEGER NOT NULL DEFAULT 0,
    work_date   DATE    NOT NULL,
    is_active   BOOLEAN NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMPTZ,
    updated_at  TIMESTAMPTZ,
    deleted_at  TIMESTAMPTZ
);

CREATE INDEX idx_time_entries_fee ON time_entries(fee_id) WHERE deleted_at IS NULL;

-- ----------------------------------------------------------------

CREATE TABLE IF NOT EXISTS expenses (
    id           UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id    UUID        NOT NULL,
    case_id      UUID,
    description  TEXT        NOT NULL,
    amount       NUMERIC(19,2) NOT NULL,
    expense_date DATE        NOT NULL,
    reimbursable BOOLEAN     NOT NULL DEFAULT FALSE,
    category     VARCHAR(50) NOT NULL,
    is_active    BOOLEAN     NOT NULL DEFAULT TRUE,
    created_at   TIMESTAMPTZ,
    updated_at   TIMESTAMPTZ,
    deleted_at   TIMESTAMPTZ
);

CREATE INDEX idx_expenses_tenant ON expenses(tenant_id) WHERE deleted_at IS NULL;
CREATE INDEX idx_expenses_case   ON expenses(case_id)   WHERE deleted_at IS NULL;
