INSERT INTO auth_service_roles (name, description)
VALUES ('ADMIN_ROLE', 'Администратор'),
       ('DATA_ANALYST_ROLE', 'Аналитик данных');

INSERT INTO auth_service_authorities (name, description)
VALUES ('MANAGE_ROLES', 'Возможность управления ролями'),
       ('MANAGE_USERS', 'Возможность управления пользователями'),
       ('SUBSCRIBE_TO_PRICE', 'Возможность подписки на получение уведомленя при достижении цены криптовалюты определенного уровня'),
       ('MANAGE_CURRENCIES', 'Возможность управления криптовалютами');

INSERT INTO auth_service_role_authorities (role_id, authority_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (2, 3),
       (2, 4);