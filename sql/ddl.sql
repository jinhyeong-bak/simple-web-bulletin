CREATE TABLE post (
    id	BIGINT AUTO_INCREMENT	NOT NULL,
    title VARCHAR(256)  NOT NULL,
    content	TEXT NOT NULL,
    register_date DATETIME NOT NULL,
    username VARCHAR(30) NOT NULL,
    password VARCHAR(15) NOT NULL,
    PRIMARY KEY(id)
 );

CREATE TABLE comment (
    id	BIGINT AUTO_INCREMENT NOT NULL,
    pid	BIGINT NOT NULL,
    username VARCHAR(30) NULL,
    password VARCHAR(15) NOT NULL,
    content	TEXT NOT NULL,
    register_date DATETIME NOT NULL,
    PRIMARY KEY(id)
);

