CREATE TABLE role_permission (
    id SERIAL PRIMARY KEY,
    role_id INTEGER,
    permission_id INTEGER,
    created_at TIMESTAMP NOT NULL DEFAULT (NOW() at time zone 'utc'),
    updated_at TIMESTAMP NOT NULL DEFAULT (NOW() at time zone 'utc')
);

CREATE UNIQUE INDEX idx_role_permission_role_id_permission_id ON role_permission(role_id, permission_id);