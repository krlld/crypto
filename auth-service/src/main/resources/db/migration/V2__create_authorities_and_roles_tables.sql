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
    role_id      BIGINT NOT NULL,
    authority_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, authority_id),
    FOREIGN KEY (role_id) REFERENCES auth_service_roles (id) ON DELETE CASCADE,
    FOREIGN KEY (authority_id) REFERENCES auth_service_authorities (id) ON DELETE CASCADE
);

CREATE TABLE auth_service_users_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES auth_service_users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES auth_service_roles (id) ON DELETE CASCADE
);