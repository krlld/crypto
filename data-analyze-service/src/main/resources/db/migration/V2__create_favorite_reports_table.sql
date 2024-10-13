CREATE TABLE data_analyze_service_favorite_reports
(
    id        BIGSERIAL PRIMARY KEY,
    user_id   BIGINT NOT NULL,
    report_id BIGINT NOT NULL,
    UNIQUE (user_id, report_id),
    FOREIGN KEY (report_id) REFERENCES data_analyze_service_reports (id)
);