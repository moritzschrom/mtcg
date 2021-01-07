/* Messages */

DROP TABLE IF EXISTS messages CASCADE;
CREATE TABLE messages
(
    id      SERIAL PRIMARY KEY,
    message VARCHAR(255) NULL
);

/* Users */

DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    token    VARCHAR(255) NULL,
    coins    INT          NOT NULL DEFAULT 20,
    UNIQUE (username)
);

/* Packages */

DROP TABLE IF EXISTS packages CASCADE;
CREATE TABLE packages
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(255)  NOT NULL DEFAULT 'Card Package',
    price DECIMAL(6, 2) NOT NULL DEFAULT 5
);

/* Cards */

DROP TABLE IF EXISTS cards CASCADE;
CREATE TABLE cards
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(255)  NOT NULL,
    damage       DECIMAL(6, 2) NOT NULL,
    element_type VARCHAR(255)  NOT NULL,
    card_type    VARCHAR(255)  NOT NULL,
    package_id   INT,
    user_id      INT,
    CONSTRAINT fk_package FOREIGN KEY (package_id) REFERENCES packages (id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id)
);
