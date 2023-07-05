CREATE
DATABASE library COLLATE utf8mb4_polish_ci;

CREATE TABLE library.book
(
    id     BIGINT PRIMARY KEY AUTO_INCREMENT,
    isbn   VARCHAR(13) UNIQUE,
    title  VARCHAR(5000) NOT NULL,
    author VARCHAR(150)  NOT NULL,
    year YEAR NOT NULL
);

INSERT INTO book (isbn, title, author, year)
VALUES ("1234567890123", "W pustyni i w puszczy", "Henryk Sienkiewicz", 2012),
       ("2345678901231", "Przygody Plastusia", "Maria Kownacka", 2005),
       ("3456789012312", "Metro 2033", "Dmitry Glukhovsky", 2017);