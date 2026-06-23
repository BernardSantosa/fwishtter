CREATE TABLE users (
    id CHAR(36) NOT NULL PRIMARY KEY,
    display_name VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    handle VARCHAR(50) UNIQUE NOT NULL,
    phone_number VARCHAR(20),
    bio VARCHAR(160),
    profile_picture_url TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);