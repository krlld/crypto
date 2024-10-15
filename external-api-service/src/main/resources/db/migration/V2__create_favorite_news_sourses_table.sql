CREATE TABLE external_api_service_favorite_news_sources
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT NOT NULL,
    news_source TEXT   NOT NULL,
    UNIQUE (user_id, news_source)
);