ALTER TABLE request RENAME COLUMN stater TO status;

UPDATE request SET status = 'CREATED' WHERE status IS NULL;