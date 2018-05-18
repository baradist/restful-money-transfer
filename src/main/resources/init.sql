DROP TABLE IF EXISTS user;

CREATE TABLE user (
  id LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
  name  VARCHAR(30) NOT NULL,
  email VARCHAR(30) NOT NULL
);

CREATE UNIQUE INDEX idx_ue on user(email);

INSERT INTO user (name, email) VALUES ('John', 'john_doe@gmail.com');
INSERT INTO user (name, email) VALUES ('Jack', 'jack_smith@gmail.com');
INSERT INTO user (name, email) VALUES ('Jane', 'jane@gmail.com');


DROP TABLE IF EXISTS currency;

CREATE TABLE currency (
  iso4217_code    SMALLINT PRIMARY KEY NOT NULL,
  iso4217_symcode CHAR(5)              NOT NULL,
  name            VARCHAR(20)
);

CREATE UNIQUE INDEX idx_curr
  on currency (iso4217_code, iso4217_symcode);

INSERT INTO currency (iso4217_code, iso4217_symcode, name) VALUES (840, 'USD', 'United States dollar');
INSERT INTO currency (iso4217_code, iso4217_symcode, name) VALUES (978, 'EUR', 'Euro');
INSERT INTO currency (iso4217_code, iso4217_symcode, name) VALUES (826, 'GBP', 'Pound sterling');


DROP TABLE IF EXISTS account;

CREATE TABLE account (
  id LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
  userId LONG NOT NULL,
  balance  DECIMAL(17, 2) DEFAULT 0 NOT NULL,
  currency SMALLINT NOT NULL
);

ALTER TABLE account
  ADD FOREIGN KEY (userId)
REFERENCES user (id) ON DELETE CASCADE;

ALTER TABLE account
  ADD FOREIGN KEY (currency)
REFERENCES currency (iso4217_code);

INSERT INTO account (userId, balance, currency) VALUES (1, 77.7, 840);
INSERT INTO account (userId, balance, currency) VALUES (1, 0.0, 840);
INSERT INTO account (userId, balance, currency) VALUES (2, 77.7, 840);
INSERT INTO account (userId, balance, currency) VALUES (2, 42.3, 978);
INSERT INTO account (userId, balance, currency) VALUES (3, 77.7, 978);


DROP TABLE IF EXISTS transfer;

CREATE TABLE transfer (
  id LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
  currency SMALLINT NOT NULL,
  amount DECIMAL(17, 2) DEFAULT 0 NOT NULL,
  fromAccountId LONG NOT NULL,
  toAccountId LONG NOT NULL
);

ALTER TABLE transfer
  ADD FOREIGN KEY (fromAccountId)
REFERENCES account (id) ON DELETE CASCADE;

ALTER TABLE transfer
  ADD FOREIGN KEY (toAccountId)
REFERENCES account (id) ON DELETE CASCADE;

ALTER TABLE transfer
  ADD FOREIGN KEY (currency)
REFERENCES currency (iso4217_code) ON DELETE CASCADE;

INSERT INTO transfer (currency, amount, fromAccountId, toAccountId) VALUES (840, 77.7, 3, 1);
INSERT INTO transfer (currency, amount, fromAccountId, toAccountId) VALUES (978, 5.5, 4, 5);