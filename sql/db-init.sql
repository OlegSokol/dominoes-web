drop database if exists dominoes;
create database dominoes;
use dominoes;

CREATE TABLE domino_tiles (
    index_val INT NOT NULL PRIMARY KEY,
    left_value INT NOT NULL,
    right_value INT NOT NULL
);

CREATE TABLE chains (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    size int not null
);

CREATE TABLE dominoes_chains (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    chain_id INT NOT NULL,
    dmono_id INT NOT NULL,
    position INT NOT NULL,
    swap BOOLEAN DEFAULT FALSE NOT NULL
);

insert into domino_tiles values (1, 0, 0);
insert into domino_tiles values (2, 1, 0);
insert into domino_tiles values (3, 1, 1);
insert into domino_tiles values (4, 2, 0);
insert into domino_tiles values (5, 2, 1);
insert into domino_tiles values (6, 2, 2);
insert into domino_tiles values (7, 3, 0);
insert into domino_tiles values (8, 3, 1);
insert into domino_tiles values (9, 3, 2);
insert into domino_tiles values (10, 3, 3);
insert into domino_tiles values (11, 4, 0);
insert into domino_tiles values (12, 4, 1);
insert into domino_tiles values (13, 4, 2);
insert into domino_tiles values (14, 4, 3);
insert into domino_tiles values (15, 4, 4);
insert into domino_tiles values (16, 5, 0);
insert into domino_tiles values (17, 5, 1);
insert into domino_tiles values (18, 5, 2);
insert into domino_tiles values (19, 5, 3);
insert into domino_tiles values (20, 5, 4);
insert into domino_tiles values (21, 5, 5);
insert into domino_tiles values (22, 6, 0);
insert into domino_tiles values (23, 6, 1);
insert into domino_tiles values (24, 6, 2);
insert into domino_tiles values (25, 6, 3);
insert into domino_tiles values (26, 6, 4);
insert into domino_tiles values (27, 6, 5);
insert into domino_tiles values (28, 6, 6);