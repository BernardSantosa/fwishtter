CREATE TABLE likes (
    user_id CHAR(36) NOT NULL,
    tweet_id CHAR(36) NOT NULL,
    CONSTRAINT fk_like_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_like_tweet FOREIGN KEY (tweet_id) REFERENCES tweets(id),
    PRIMARY KEY (user_id, tweet_id)
);