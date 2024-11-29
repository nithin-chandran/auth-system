CREATE EXTENSION IF NOT EXISTS pgcrypto;

WITH inserted_account AS (
    INSERT INTO account (email, phone, password_hash)
    VALUES
        ('test@example.com', '9778182869', crypt('initial_password', gen_salt('bf')))
    RETURNING id AS account_id
),
inserted_role AS (
    INSERT INTO role (name)
    VALUES ('admin')
    RETURNING id AS role_id
),
inserted_permissions AS (
    INSERT INTO permission (action, resource_type, is_global_scope)
    VALUES
        ('manage', 'permission', true),
        ('manage', 'account', true),
        ('manage', 'role', true),
        ('manage', 'structure', true)
    RETURNING id AS permission_id
),
role_permissions AS (
    INSERT INTO role_permission (role_id, permission_id)
    SELECT role_id, permission_id
    FROM inserted_role, inserted_permissions
    RETURNING role_id, permission_id
)
INSERT INTO account_role (account_id, role_id)
SELECT account_id, role_id
FROM inserted_account, inserted_role;







