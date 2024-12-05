package edu.curso;

import javafx.beans.binding.Bindings;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

public class LivroBoundary implements Tela {

    private Label lblId = new Label("");
    private TextField txtTitulo = new TextField();
    private TextField txtAutor = new TextField();
    private TextField txtEditora = new TextField();
    private TextField txtAnoPublicacao = new TextField();
    private TextField txtIsbn = new TextField();

    private LivroControl control = null;

    private TableView<Livro> tableView = new TableView<>();

    @Override
    public Pane render() {
        try {
            control = new LivroControl();
        } catch (BibliotecaException e) {
            new Alert(AlertType.ERROR, "Erro ao iniciar o sistema", ButtonType.OK).showAndWait();
        }
        BorderPane panePrincipal = new BorderPane();
        GridPane paneForm = new GridPane();

        Button btnGravar = new Button("Gravar");
        btnGravar.setOnAction(e -> {
            try {
                control.gravar();
            } catch (BibliotecaException err) {
                new Alert(AlertType.ERROR, "Erro ao gravar o livro", ButtonType.OK).showAndWait();
            }
            tableView.refresh();
        });
        Button btnPesquisar = new Button("Pesquisar");
        btnPesquisar.setOnAction(e -> {
            try {
                control.pesquisar();
            } catch (BibliotecaException err) {
                String errorMessage = "Erro ao pesquisar por título:\n" + err.getMessage();
                System.out.println(errorMessage);
                new Alert(AlertType.ERROR, errorMessage, ButtonType.OK).showAndWait();
            } catch (Exception unexpected) {
                String errorMessage = "Erro inesperado:\n" + unexpected.getMessage();
                System.out.println(errorMessage);
                new Alert(AlertType.ERROR, errorMessage, ButtonType.OK).showAndWait();
            }
        });

        Button btnNovo = new Button("*");
        btnNovo.setOnAction(e -> control.limparTudo());

        paneForm.add(new Label("Id: "), 0, 0);
        paneForm.add(lblId, 1, 0);
        paneForm.add(new Label("Título: "), 0, 1);
        paneForm.add(txtTitulo, 1, 1);
        paneForm.add(btnNovo, 2, 1);
        paneForm.add(new Label("Autor: "), 0, 2);
        paneForm.add(txtAutor, 1, 2);
        paneForm.add(new Label("Editora: "), 0, 3);
        paneForm.add(txtEditora, 1, 3);
        paneForm.add(new Label("Ano de publicação: "), 0, 4);
        paneForm.add(txtAnoPublicacao, 1, 4);
        paneForm.add(new Label("ISBN: "), 0, 5);
        paneForm.add(txtIsbn, 1, 5);

        paneForm.add(btnGravar, 0, 8);
        paneForm.add(btnPesquisar, 1, 8);

        ligacoes();
        gerarColunas();

        panePrincipal.setTop(paneForm);
        panePrincipal.setCenter(tableView);

        return panePrincipal;
    }

    public void gerarColunas() {
        if (tableView.getColumns().isEmpty()){
        TableColumn<Livro, Long> col1 = new TableColumn<>("Id");
        col1.setCellValueFactory(new PropertyValueFactory<Livro, Long>("id"));

        TableColumn<Livro, String> col2 = new TableColumn<>("Título");
        col2.setCellValueFactory(new PropertyValueFactory<Livro, String>("titulo"));

        TableColumn<Livro, String> col3 = new TableColumn<>("Autor");
        col3.setCellValueFactory(new PropertyValueFactory<Livro, String>("autor"));

        TableColumn<Livro, String> col4 = new TableColumn<>("Editora");
        col4.setCellValueFactory(new PropertyValueFactory<Livro, String>("editora"));

        TableColumn<Livro, Integer> col5 = new TableColumn<>("Ano de publicação");
        col5.setCellValueFactory(new PropertyValueFactory<Livro, Integer>("anoPublicacao"));

        TableColumn<Livro, String> col6 = new TableColumn<>("ISBN");
        col6.setCellValueFactory(new PropertyValueFactory<Livro, String>("isbn"));

        tableView.getSelectionModel().selectedItemProperty()
                .addListener((obs, antigo, novo) -> {
                    if (novo != null) {
                        System.out.println("Título: " + novo.getTitulo());
                        control.entidadeParaTela(novo);
                    }
                });
        Callback<TableColumn<Livro, Void>, TableCell<Livro, Void>> cb = new Callback<>() {
            @Override
            public TableCell<Livro, Void> call(
                    TableColumn<Livro, Void> param) {
                TableCell<Livro, Void> celula = new TableCell<>() {
                    final Button btnApagar = new Button("Apagar");

                    {
                        btnApagar.setOnAction(e -> {
                            Livro Livro = tableView.getItems().get(getIndex());
                            try {
                                control.excluir(Livro);
                            } catch (BibliotecaException err) {
                                new Alert(AlertType.ERROR, "Erro ao excluir o livro", ButtonType.OK).showAndWait();
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        if (!empty) {
                            setGraphic(btnApagar);
                        } else {
                            setGraphic(null);
                        }
                    }

                };
                return celula;
            }
        };

        TableColumn<Livro, Void> col7 = new TableColumn<>("Ação");
        col7.setCellFactory(cb);

        tableView.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7);
        tableView.setItems(control.getLista());
        try {
            control.pesquisarTodos();
        } catch (BibliotecaException e) {
            e.printStackTrace();
        }
    }
    }

    public void ligacoes() {
        control.idProperty().addListener((obs, antigo, novo) -> {
            lblId.setText(String.valueOf(novo));
        });

        IntegerStringConverter integerConverter = new IntegerStringConverter();
        Bindings.bindBidirectional(control.tituloProperty(), txtTitulo.textProperty());
        Bindings.bindBidirectional(control.autorProperty(), txtAutor.textProperty());
        Bindings.bindBidirectional(control.editoraProperty(), txtEditora.textProperty());
        Bindings.bindBidirectional(txtAnoPublicacao.textProperty(), control.anoPublicacaoProperty(),
                (StringConverter) integerConverter);
        Bindings.bindBidirectional(control.isbnProperty(), txtIsbn.textProperty());
    }

}
