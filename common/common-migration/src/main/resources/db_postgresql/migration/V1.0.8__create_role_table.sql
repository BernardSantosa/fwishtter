CREATE TABLE roles (
    id CHAR(36) NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_time TIMESTAMP(6) NOT NULL,
    updated_time TIMESTAMP
);