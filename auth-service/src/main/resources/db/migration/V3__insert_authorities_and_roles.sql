INSERT INTO auth_service_roles (name)
VALUES ('ADMIN_ROLE'),
       ('DATA_ANALYST_ROLE');

INSERT INTO auth_service_authorities (name)
VALUES ('MANAGE_ROLES'),
       ('SUBSCRIBE_TO_PRICE'),
       ('MANAGE_CURRENCIES');

INSERT INTO auth_service_role_authorities (role_id, authority_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 2),
       (2, 3);