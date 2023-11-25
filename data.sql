DROP TABLE IF EXISTS character;

DROP SEQUENCE IF EXISTS character_id_seq;

CREATE SEQUENCE character_id_seq;

CREATE TABLE character
(
    id        integer PRIMARY KEY DEFAULT nextval('character_id_seq'),
    firstname varchar(32),
    lastname  varchar(32) NOT NULL,
    code      varchar(10) NOT NULL UNIQUE
);

ALTER SEQUENCE character_id_seq OWNED BY character.id;

INSERT INTO character VALUES (nextval('character_id_seq'), 'Kyo', 'Kusanagi', 'Kyo-Ku');
INSERT INTO character VALUES (nextval('character_id_seq'), 'Mai', 'Shiranui', 'Mai-Sh');
INSERT INTO character VALUES (nextval('character_id_seq'), 'Iori', 'Yagami', 'Ior-Ya');
INSERT INTO character VALUES (nextval('character_id_seq'), 'Athena', 'Asamiya', 'Ath-As');
INSERT INTO character VALUES (nextval('character_id_seq'), 'Terry', 'Bogard', 'Ter-Bo');
INSERT INTO character VALUES (nextval('character_id_seq'), 'Yuri', 'Sakazaki', 'Yur-Sa');