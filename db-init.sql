DROP DATABASE IF EXISTS dominoes;
CREATE DATABASE IF NOT EXISTS dominoes;
USE dominoes;

CREATE TABLE domino_tiles (
  index_val   INT NOT NULL PRIMARY KEY,
  left_value  INT NOT NULL,
  right_value INT NOT NULL
);

CREATE TABLE chains (
  id   INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  size INT                NOT NULL
);

CREATE TABLE dominoes_chains (
  id        INT AUTO_INCREMENT    NOT NULL PRIMARY KEY,
  chain_id  INT                   NOT NULL,
  domino_id INT                   NOT NULL,
  swap      BOOLEAN DEFAULT FALSE NOT NULL
);

INSERT INTO domino_tiles VALUES (1, 0, 0);
INSERT INTO domino_tiles VALUES (2, 1, 0);
INSERT INTO domino_tiles VALUES (3, 1, 1);
INSERT INTO domino_tiles VALUES (4, 2, 0);
INSERT INTO domino_tiles VALUES (5, 2, 1);
INSERT INTO domino_tiles VALUES (6, 2, 2);
INSERT INTO domino_tiles VALUES (7, 3, 0);
INSERT INTO domino_tiles VALUES (8, 3, 1);
INSERT INTO domino_tiles VALUES (9, 3, 2);
INSERT INTO domino_tiles VALUES (10, 3, 3);
INSERT INTO domino_tiles VALUES (11, 4, 0);
INSERT INTO domino_tiles VALUES (12, 4, 1);
INSERT INTO domino_tiles VALUES (13, 4, 2);
INSERT INTO domino_tiles VALUES (14, 4, 3);
INSERT INTO domino_tiles VALUES (15, 4, 4);
INSERT INTO domino_tiles VALUES (16, 5, 0);
INSERT INTO domino_tiles VALUES (17, 5, 1);
INSERT INTO domino_tiles VALUES (18, 5, 2);
INSERT INTO domino_tiles VALUES (19, 5, 3);
INSERT INTO domino_tiles VALUES (20, 5, 4);
INSERT INTO domino_tiles VALUES (21, 5, 5);
INSERT INTO domino_tiles VALUES (22, 6, 0);
INSERT INTO domino_tiles VALUES (23, 6, 1);
INSERT INTO domino_tiles VALUES (24, 6, 2);
INSERT INTO domino_tiles VALUES (25, 6, 3);
INSERT INTO domino_tiles VALUES (26, 6, 4);
INSERT INTO domino_tiles VALUES (27, 6, 5);
INSERT INTO domino_tiles VALUES (28, 6, 6);

INSERT INTO chains VALUES (1, 2);
INSERT INTO chains VALUES (2, 3);
INSERT INTO chains VALUES (3, 4);

INSERT INTO dominoes_chains VALUES (DEFAULT, 1, 1,0);
INSERT INTO dominoes_chains VALUES (DEFAULT, 1, 2,1);

INSERT INTO dominoes_chains VALUES (DEFAULT, 2, 2,0);
INSERT INTO dominoes_chains VALUES (DEFAULT, 2, 4,1);
INSERT INTO dominoes_chains VALUES (DEFAULT, 2, 9,1);

INSERT INTO dominoes_chains VALUES (DEFAULT, 3, 10,0);
INSERT INTO dominoes_chains VALUES (DEFAULT, 3, 14,1);
INSERT INTO dominoes_chains VALUES (DEFAULT, 3, 15,0);
INSERT INTO dominoes_chains VALUES (DEFAULT, 3, 20,1);