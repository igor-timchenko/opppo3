CREATE TABLE IF NOT EXISTS test_table (
    id SERIAL PRIMARY KEY,
    message VARCHAR(255) NOT NULL
);

INSERT INTO test_table (message) VALUES ('Hello from PostgreSQL!');
