CREATE TABLE tweet_media (
    id CHAR(36),
    tweet_id CHAR(36),
    media_url VARCHAR(255) NOT NULL,
    CONSTRAINT fk_tweet_media FOREIGN KEY (tweet_id) REFERENCES tweets(id) ON DELETE CASCADE
);