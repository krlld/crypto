CREATE TABLE auth_service_users
(
    id       BIGSERIAL PRIMARY KEY,
    email    TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL
);