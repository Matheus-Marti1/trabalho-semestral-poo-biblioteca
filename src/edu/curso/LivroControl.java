package edu.curso;

import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LivroControl {

    private ObservableList<Livro> lista = FXCollections.observableArrayList();
    private long contador = 2;

    private LongProperty id = new SimpleLongProperty(0l);
    private StringProperty titulo = new SimpleStringProperty("");
    private StringProperty autor = new SimpleStringProperty("");
    private StringProperty editora = new SimpleStringProperty("");
    private IntegerProperty anoPublicacao = new SimpleIntegerProperty(0);
    private StringProperty isbn = new SimpleStringProperty("");

    private LivroDAO livroDAO = null;

    public LivroControl() throws BibliotecaException {
        livroDAO = new LivroDAOImpl();
    }

    public Livro telaParaEntidade() {
        Livro livro = new Livro();
        livro.setId(id.get());
        livro.setTitulo(titulo.get());
        livro.setAutor(autor.get());
        livro.setEditora(editora.get());
        livro.setAnoPublicacao(anoPublicacao.get());
        livro.setIsbn(isbn.get());
        return livro;
    }

    public void excluir(Livro l) throws BibliotecaException {
        livroDAO.remover(l);
        pesquisarTodos();
    }

    public void gravar() throws BibliotecaException {
        Livro livro = telaParaEntidade();
        if (livro.getId() == 0) {
            this.contador += 1;
            livro.setId(this.contador);
            livroDAO.inserir(livro);
        } else {
            livroDAO.atualizar(livro);
        }
        limparTudo();
        pesquisarTodos();
    }

    public void pesquisar() throws BibliotecaException {
        System.out.println("Iniciando pesquisa...");
        if (titulo.get() == null || titulo.get().isEmpty()) {
            System.out.println("O título fornecido está vazio ou é nulo.");
            throw new BibliotecaException("O título não pode estar vazio.");
        }

        System.out.println("Pesquisando por título: " + titulo.get());
        try {
            List<Livro> listaTemp = livroDAO.pesquisarPorTitulo(titulo.get());
            System.out.println("Resultados encontrados: " + listaTemp.size());
            lista.clear();
            lista.addAll(listaTemp);
        } catch (Exception e) {
            System.out.println("Erro ao pesquisar no DAO: " + e.getMessage());
            throw new BibliotecaException("Erro ao acessar os dados: " + e.getMessage());
        }
    }

    public void pesquisarTodos() throws BibliotecaException {
        List<Livro> listaTemp = livroDAO.pesquisarTodosLivros();
        lista.clear();
        lista.addAll(listaTemp);
    }

    public void limparTudo() {
        id.set(0l);
        titulo.set("");
        autor.set("");
        editora.set("");
        anoPublicacao.set(0);
        isbn.set("");
    }

    public void entidadeParaTela(Livro l) {
        if (l != null) {
            id.set(l.getId());
            titulo.set(l.getTitulo());
            autor.set(l.getAutor());
            editora.set(l.getEditora());
            anoPublicacao.set(l.getAnoPublicacao());
            isbn.set(l.getIsbn());
        }
    }

    public ObservableList<Livro> getLista() {
        return this.lista;
    }

    public LongProperty idProperty() {
        return this.id;
    }

    public StringProperty tituloProperty() {
        return this.titulo;
    }

    public StringProperty autorProperty() {
        return this.autor;
    }

    public StringProperty editoraProperty() {
        return this.editora;
    }

    public IntegerProperty anoPublicacaoProperty() {
        return this.anoPublicacao;
    }

    public StringProperty isbnProperty() {
        return this.isbn;
    }
}