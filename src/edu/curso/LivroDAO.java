package edu.curso;

import java.util.List;

public interface LivroDAO {

    void inserir(Livro l) throws BibliotecaException;

    void atualizar(Livro l) throws BibliotecaException;

    void remover(Livro l) throws BibliotecaException;

    List<Livro> pesquisarPorTitulo(String titulo) throws BibliotecaException;

    List<Livro> pesquisarTodosLivros() throws BibliotecaException;

}
