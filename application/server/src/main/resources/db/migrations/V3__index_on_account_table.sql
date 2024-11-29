CREATE UNIQUE INDEX idx_account_phone ON account(phone) WHERE phone IS NOT NULL;

CREATE UNIQUE INDEX idx_account_email ON account(email) WHERE email IS NOT NULL;