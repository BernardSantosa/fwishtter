CREATE TABLE notifications (
    id CHAR(36) NOT NULL,
    receiver_id CHAR(36) NOT NULL,
    sender_id CHAR(36) NOT NULL,
    tweet_id CHAR(36),
    type CHAR(10) NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);