CREATE TABLE account_role (
    id SERIAL PRIMARY KEY,
    account_id INTEGER,
    role_id INTEGER,
    created_at TIMESTAMP NOT NULL DEFAULT (NOW() at time zone 'utc'),
    updated_at TIMESTAMP NOT NULL DEFAULT (NOW() at time zone 'utc')
);

CREATE UNIQUE INDEX idx_account_role_account_id_role_id ON account_role(account_id, role_id);