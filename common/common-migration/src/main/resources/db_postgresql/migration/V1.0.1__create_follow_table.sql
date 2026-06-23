CREATE TABLE follows (
    created_at TIMESTAMP NOT NULL,
    follower_id CHAR(36) NOT NULL,
    followed_id CHAR(36) NOT NULL,
    CONSTRAINT fk_follower_user FOREIGN KEY (follower_id) REFERENCES users(id),
    CONSTRAINT fk_followed_user FOREIGN KEY (followed_id) REFERENCES users(id),
    PRIMARY KEY (follower_id, followed_id)
);