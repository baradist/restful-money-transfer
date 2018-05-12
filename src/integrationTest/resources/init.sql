DROP TABLE IF EXISTS user;

CREATE TABLE user (
 id LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
 name VARCHAR(30) NOT NULL,
 email VARCHAR(30) NOT NULL
);

CREATE UNIQUE INDEX idx_ue on user(email);

INSERT INTO user (name, email) VALUES ('John','john_doe@gmail.com');
INSERT INTO user (name, email) VALUES ('Jack','jack_smith@gmail.com');
INSERT INTO user (name, email) VALUES ('Jane','jane@gmail.com');
