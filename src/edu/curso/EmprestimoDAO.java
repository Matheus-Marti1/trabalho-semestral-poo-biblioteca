package edu.curso;

import java.util.List;

public interface EmprestimoDAO {

    void inserir(Emprestimo e) throws BibliotecaException;

    void atualizar(Emprestimo e) throws BibliotecaException;

    void remover(Emprestimo e) throws BibliotecaException;

    List<Emprestimo> pesquisarPorNomeCliente(String nomeCliente) throws BibliotecaException;

}
