CREATE TABLE tweets (
    id CHAR(36) NOT NULL PRIMARY KEY,
    author_id CHAR(36) NOT NULL,
    parent_id CHAR(36),
    content VARCHAR(280),
    type VARCHAR(20),
    created_time TIMESTAMP NOT NULL,
    updated_time TIMESTAMP,
    created_by VARCHAR(255) NOT NULL,
    updated_by VARCHAR(255),
    CONSTRAINT fk_parent_tweet FOREIGN KEY (parent_id) REFERENCES tweets(id)
);