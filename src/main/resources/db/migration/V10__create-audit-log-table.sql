CREATE TABLE audit_log (
    id SERIAL PRIMARY KEY,
    author_id INT REFERENCES users(id) ON DELETE SET NULL,
    action VARCHAR(30) NOT NULL,
    entity_type VARCHAR(30) NOT NULL,
    entity_id BIGINT,
    occurred_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    before_state TEXT,
    after_state TEXT
);

CREATE INDEX idx_audit_log_entity ON audit_log (entity_type, entity_id);
CREATE INDEX idx_audit_log_occurred_at ON audit_log (occurred_at);
