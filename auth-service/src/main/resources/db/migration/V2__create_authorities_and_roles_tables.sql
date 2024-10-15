CREATE TABLE auth_service_authorities
(
    id   BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE auth_service_roles
(
    id   BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE auth_service_role_authorities
(
    id           BIGSERIAL PRIMARY KEY,
    role_id      BIGINT NOT NULL,
    authority_id BIGINT NOT NULL,
    UNIQUE (role_id, authority_id),
    FOREIGN KEY (role_id) REFERENCES auth_service_roles (id),
    FOREIGN KEY (authority_id) REFERENCES auth_service_authorities (id)
);

CREATE TABLE auth_service_users_roles
(
    id      BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    UNIQUE (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES auth_service_users (id),
    FOREIGN KEY (role_id) REFERENCES auth_service_roles (id)
);