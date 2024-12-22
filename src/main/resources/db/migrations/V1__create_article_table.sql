CREATE TABLE article
(
    id    BIGSERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    tags TEXT[],
    comments_id BIGINT[],
    trending BOOLEAN NOT NULL
);