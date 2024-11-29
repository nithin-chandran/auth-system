CREATE TABLE role (
    id SERIAL PRIMARY KEY,
    name VARCHAR(40),
    created_at TIMESTAMP NOT NULL DEFAULT (NOW() at time zone 'utc'),
    updated_at TIMESTAMP NOT NULL DEFAULT (NOW() at time zone 'utc')
);

CREATE UNIQUE INDEX idx_role_name ON role(name);