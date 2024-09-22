CREATE TABLE auth_service_users
(
    id       BIGINT PRIMARY KEY,
    email    TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL
);