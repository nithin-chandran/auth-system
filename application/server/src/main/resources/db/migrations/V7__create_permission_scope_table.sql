CREATE TABLE permission_scope (
    id SERIAL PRIMARY KEY,
    permission_id INTEGER,
    resource_id INTEGER,
    created_at TIMESTAMP NOT NULL DEFAULT (NOW() at time zone 'utc'),
    updated_at TIMESTAMP NOT NULL DEFAULT (NOW() at time zone 'utc')
);

CREATE UNIQUE INDEX idx_permission_scope_permission_id_resource_id ON permission_scope(permission_id, resource_id);