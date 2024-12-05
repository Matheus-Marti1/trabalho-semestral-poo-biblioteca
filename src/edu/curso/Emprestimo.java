package edu.curso;

import java.time.LocalDate;

public class Emprestimo {
    private long id = 0;
    private String nomeCliente = "";
    private LocalDate dataEmprestimo = LocalDate.now();
    private LocalDate dataDevolucao = LocalDate.now().plusWeeks(1);
    private long idLivro = 0;
    private boolean status = false;

    public Emprestimo() {
    }

    public Emprestimo(long id, String nomeCliente, LocalDate dataEmprestimo, LocalDate dataDevolucao, long idLivro,
            boolean status) {
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.idLivro = idLivro;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public long getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(long idLivro) {
        this.idLivro = idLivro;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
