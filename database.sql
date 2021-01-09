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
    in_deck      BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_package FOREIGN KEY (package_id) REFERENCES packages (id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id)
);

/* Battle */

DROP TABLE IF EXISTS battle CASCADE;
CREATE TABLE battle
(
    id       SERIAL PRIMARY KEY,
    player_1 INT,
    player_2 INT,
    winner   INT,
    CONSTRAINT fk_player_1 FOREIGN KEY (player_1) REFERENCES users (id),
    CONSTRAINT fk_player_2 FOREIGN KEY (player_2) REFERENCES users (id),
    CONSTRAINT winner FOREIGN KEY (winner) REFERENCES users (id)
);

DROP TABLE IF EXISTS battle_rounds CASCADE;
CREATE TABLE battle_rounds
(
    id          SERIAL PRIMARY KEY,
    card_1      INT NOT NULL,
    card_2      INT NOT NULL,
    winner_card INT NOT NULL,
    CONSTRAINT fk_card_1 FOREIGN KEY (card_1) REFERENCES cards (id),
    CONSTRAINT fk_card_2 FOREIGN KEY (card_2) REFERENCES cards (id),
    CONSTRAINT fk_winner_card FOREIGN KEY (winner_card) REFERENCES cards (id)
);
