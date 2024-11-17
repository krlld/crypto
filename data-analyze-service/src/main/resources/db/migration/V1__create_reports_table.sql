CREATE TABLE data_analyze_service_reports
(
    id              BIGSERIAL PRIMARY KEY,
    title           TEXT    NOT NULL,
    description     TEXT    NOT NULL,
    source_file_id  TEXT    NOT NULL,
    result_file_id  TEXT,
    user_id         BIGINT  NOT NULL,
    is_public       BOOLEAN NOT NULL,
    created_at_date DATE    NOT NULL DEFAULT NOW()
);