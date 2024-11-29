CREATE TABLE permission (
    id SERIAL PRIMARY KEY,
    action VARCHAR(40),
    resource_type VARCHAR(40),
    is_global_scope BOOLEAN,
    created_at TIMESTAMP NOT NULL DEFAULT (NOW() at time zone 'utc'),
    updated_at TIMESTAMP NOT NULL DEFAULT (NOW() at time zone 'utc')
);

CREATE UNIQUE INDEX idx_permission_action_resource_type_scope ON permission(action, resource_type, is_global_scope) where is_global_scope = true;