CREATE DATABASE library COLLATE utf8mb4_polish_ci;

CREATE TABLE library.book (
	isbn VARCHAR(13) PRIMARY KEY ,
    title VARCHAR(5000) NOT NULL,
    author VARCHAR(150) NOT NULL,
    year YEAR NOT NULL
);