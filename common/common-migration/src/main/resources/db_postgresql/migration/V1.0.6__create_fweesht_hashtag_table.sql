CREATE TABLE tweet_hashtag (
    tag_id CHAR(36) NOT NULL,
    tweet_id CHAR(36) NOT NULL,
    CONSTRAINT fk_tweethashtag_hashtag FOREIGN KEY (tag_id) REFERENCES hashtags(id),
    CONSTRAINT fk_tweethashtag_tweet FOREIGN KEY (tweet_id) REFERENCES tweets(id),
    PRIMARY KEY (tag_id, tweet_id)
);