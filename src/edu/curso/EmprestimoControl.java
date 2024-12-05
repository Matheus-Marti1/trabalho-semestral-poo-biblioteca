package edu.curso;

import java.time.LocalDate;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EmprestimoControl {

    private LongProperty id = new SimpleLongProperty(0l);
    private StringProperty nomeCliente = new SimpleStringProperty("");
    private ObjectProperty<LocalDate> dataEmprestimo = new SimpleObjectProperty<>(LocalDate.now());
    private ObjectProperty<LocalDate> dataDevolucao = new SimpleObjectProperty<>(LocalDate.now().plusWeeks(1));
    private LongProperty idLivro = new SimpleLongProperty(0l);
    private BooleanProperty status = new SimpleBooleanProperty(false);
    private ObservableList<Emprestimo> lista = FXCollections.observableArrayList();

    private EmprestimoDAO emprestimoDAO;
    private int contador = 0;

    public EmprestimoControl() throws BibliotecaException {
        emprestimoDAO = new EmprestimoDAOImpl();
    }

    public Emprestimo paraEntidade() {
        Emprestimo e = new Emprestimo();
        e.setId(id.get());
        e.setNomeCliente(nomeCliente.get());
        e.setDataEmprestimo(dataEmprestimo.get());
        e.setDataDevolucao(dataDevolucao.get());
        e.setIdLivro(idLivro.get());
        e.setStatus(status.get());
        return e;
    }

    public void excluir(Emprestimo e) throws BibliotecaException {
        emprestimoDAO.remover(e);
        pesquisarTodos();
    }

    public void limparTudo() {
        id.set(0);
        nomeCliente.set("");
        dataEmprestimo.set(LocalDate.now());
        dataDevolucao.set(LocalDate.now().plusWeeks(1));
        idLivro.set(0);
        status.set(false);
    }

    public void paraTela(Emprestimo e) {
        if (e != null) {
            id.set(e.getId());
            nomeCliente.set(e.getNomeCliente());
            dataEmprestimo.set(e.getDataEmprestimo());
            dataDevolucao.set(e.getDataDevolucao());
            idLivro.set(e.getIdLivro());
            status.set(e.isStatus());
        }
    }

    public void gravar() throws BibliotecaException {
        Emprestimo e = paraEntidade();
        if (e.getId() == 0) {
            this.contador += 1;
            e.setId(this.contador);
            emprestimoDAO.inserir(e);
        } else {
            emprestimoDAO.atualizar(e);
        }
        pesquisarTodos();
    }

    public void pesquisar() throws BibliotecaException {
        lista.clear();
        lista.addAll(emprestimoDAO.pesquisarPorNomeCliente(nomeCliente.get()));
    }

    public void pesquisarTodos() throws BibliotecaException {
        lista.clear();
        lista.addAll(emprestimoDAO.pesquisarPorNomeCliente(""));
    }

    public ObservableList<Emprestimo> getLista() {
        return this.lista;
    }

    public LongProperty idProperty() {
        return this.id;
    }

    public StringProperty nomeClienteProperty() {
        return this.nomeCliente;
    }

    public ObjectProperty<LocalDate> dataEmprestimoProperty() {
        return this.dataEmprestimo;
    }

    public ObjectProperty<LocalDate> dataDevolucaoProperty() {
        return this.dataDevolucao;
    }

    public LongProperty idLivroProperty() {
        return this.idLivro;
    }

    public BooleanProperty statusProperty() {
        return this.status;
    }
}
