CREATE TABLE auth_service_users
(
    id        BIGSERIAL PRIMARY KEY,
    email     TEXT NOT NULL UNIQUE,
    password  TEXT NOT NULL,
    name      TEXT NOT NULL,
    lastname  TEXT NOT NULL,
    avatar_id TEXT NOT NULL
);