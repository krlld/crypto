INSERT INTO auth_service_authorities (name)
VALUES ('MANAGE_ROLES');

INSERT INTO auth_service_roles (name)
VALUES ('ADMIN');

INSERT INTO auth_service_role_authorities (role_id, authority_id)
VALUES (1, 1);