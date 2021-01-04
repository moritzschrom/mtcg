/* Messages */

DROP TABLE IF EXISTS messages;
CREATE TABLE messages
(
    id      SERIAL PRIMARY KEY,
    message VARCHAR(255) NULL
);

/* Users */

DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    token    VARCHAR(255) NULL,
    UNIQUE (username)
);
