# messages
DROP TABLE IF EXISTS messages;
CREATE TABLE messages
(
    id      INTEGER AUTO_INCREMENT PRIMARY KEY,
    message VARCHAR(255) NULL
);

# users
DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id       INTEGER AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    token    VARCHAR(255) NULL,
    UNIQUE (username)
);
