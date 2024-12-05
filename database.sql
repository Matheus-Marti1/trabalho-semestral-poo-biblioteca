CREATE DATABASE bibliotecadb;

USE bibliotecadb;

CREATE TABLE livros ( 
    livro_id INT AUTO_INCREMENT NOT NULL,
    titulo VARCHAR(100),
    autor VARCHAR(100),
    editora VARCHAR(100),
    ano_publicacao YEAR,
    isbn CHAR(17),
    PRIMARY KEY(livro_id)
);

CREATE TABLE emprestimos (
    emprestimo_id INT AUTO_INCREMENT NOT NULL,
    nome_cliente VARCHAR(100),
    data_emprestimo DATE,
    data_devolucao DATE,
    id_livro INT,
    status BOOLEAN,
    PRIMARY KEY (emprestimo_id),
    FOREIGN KEY (id_livro) REFERENCES livros(livro_id)
);