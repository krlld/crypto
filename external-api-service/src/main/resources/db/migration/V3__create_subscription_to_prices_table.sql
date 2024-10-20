CREATE TABLE external_api_service_subscription_to_prices
(
    id              BIGSERIAL PRIMARY KEY,
    user_id         BIGINT         NOT NULL,
    currency_id     TEXT           NOT NULL,
    comparison_type TEXT           NOT NULL,
    price           NUMERIC(10, 2) NOT NULL
);