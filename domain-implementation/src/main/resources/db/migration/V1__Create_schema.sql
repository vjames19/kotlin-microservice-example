DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username varchar(255) NOT NULL,
    UNIQUE(username)
);

CREATE TABLE posts (
    id BIGSERIAL PRIMARY KEY,
    user_id bigint REFERENCES users(id) NOT NULL,
    content TEXT NOT NULL
);

CREATE TABLE comments (
    id BIGSERIAL PRIMARY KEY,
    user_id bigint REFERENCES users(id) NOT NULL,
    post_id bigint REFERENCES posts(id) NOT NULL,
    content varchar(1000) NOT NULL
);