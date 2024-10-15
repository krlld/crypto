CREATE TABLE data_analyze_service_favorite_currencies
(
    id       BIGSERIAL PRIMARY KEY,
    user_id  BIGINT NOT NULL,
    currency TEXT   NOT NULL,
    UNIQUE (user_id, currency),
    FOREIGN KEY (user_id) REFERENCES data_analyze_service_user_profiles (id)

);

CREATE TABLE data_analyze_service_favorite_news_sources
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT NOT NULL,
    news_source TEXT   NOT NULL,
    UNIQUE (user_id, news_source),
    FOREIGN KEY (user_id) REFERENCES data_analyze_service_user_profiles (id)
);