drop table USERS;

CREATE TABLE USERS (
ID bigint NOT NULL,
PHONE character varying(255) NOT NULL,
ADDRESS character varying(255) NOT NULL,
NAME character varying(255) NOT NULL,
CONSTRAINT ID PRIMARY KEY (ID)
);

