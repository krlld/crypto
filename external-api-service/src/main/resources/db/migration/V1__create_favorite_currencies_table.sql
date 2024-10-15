CREATE TABLE external_api_service_favorite_currencies
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT NOT NULL,
    currency_id TEXT   NOT NULL,
    UNIQUE (user_id, currency_id)
);