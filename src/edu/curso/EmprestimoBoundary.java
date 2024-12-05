package edu.curso;

import java.time.LocalDate;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
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
import javafx.util.converter.LongStringConverter;

public class EmprestimoBoundary implements Tela {

    private Label lblId = new Label("");
    private TextField txtNomeCliente = new TextField();
    private DatePicker dpDataEmprestimo = new DatePicker();
    private DatePicker dpDataDevolucao = new DatePicker();
    private TextField txtIdLivro = new TextField();
    private ComboBox<Boolean> cbStatus = new ComboBox<>();
    private EmprestimoControl control = null;
    private TableView<Emprestimo> tableView = new TableView<>();

    @Override
    public Pane render() {
        try {
            control = new EmprestimoControl();
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
                new Alert(AlertType.ERROR, "Erro ao gravar o empréstimo", ButtonType.OK).showAndWait();
            }
            tableView.refresh();
        });
        Button btnPesquisar = new Button("Pesquisar");
        btnPesquisar.setOnAction(e -> {
            try {
                control.pesquisar();
            } catch (BibliotecaException err) {
                new Alert(AlertType.ERROR, "Erro ao pesquisar por nome do cliente", ButtonType.OK).showAndWait();
            }
        });

        Button btnNovo = new Button("*");
        btnNovo.setOnAction(e -> control.limparTudo());

        paneForm.add(new Label("Id: "), 0, 0);
        paneForm.add(lblId, 1, 0);
        paneForm.add(new Label("Nome do cliente: "), 0, 1);
        paneForm.add(txtNomeCliente, 1, 1);
        paneForm.add(btnNovo, 2, 1);
        paneForm.add(new Label("Data do empréstimo: "), 0, 2);
        paneForm.add(dpDataEmprestimo, 1, 2);
        paneForm.add(new Label("Data da devolução: "), 0, 3);
        paneForm.add(dpDataDevolucao, 1, 3);
        paneForm.add(new Label("Id do Livro: "), 0, 4);
        paneForm.add(txtIdLivro, 1, 4);
        paneForm.add(new Label("Status: "), 0, 5);
        paneForm.add(cbStatus, 1, 5);
        cbStatus.getItems().addAll(false, true);

        carregarCbStatus();

        paneForm.add(btnGravar, 0, 6);
        paneForm.add(btnPesquisar, 1, 6);

        ligacoes();
        gerarColunas();

        panePrincipal.setTop(paneForm);
        panePrincipal.setCenter(tableView);

        return panePrincipal;
    }

    private void carregarCbStatus() {
        cbStatus.getItems().clear();
        cbStatus.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Boolean status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setText(null);
                } else {
                    setText(status ? "Concluído" : "Pendente");
                }
            }
        });

        cbStatus.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Boolean status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setText(null);
                } else {
                    setText(status ? "Concluído" : "Pendente");
                }
            }
        });
        cbStatus.getItems().addAll(true, false);
    }

    public void gerarColunas() {
        if (tableView.getColumns().isEmpty()){
        TableColumn<Emprestimo, Long> col1 = new TableColumn<>("Id");
        col1.setCellValueFactory(new PropertyValueFactory<Emprestimo, Long>("id"));

        TableColumn<Emprestimo, String> col2 = new TableColumn<>("Nome do cliente");
        col2.setCellValueFactory(new PropertyValueFactory<Emprestimo, String>("nomeCliente"));

        TableColumn<Emprestimo, LocalDate> col3 = new TableColumn<>("Data do empréstimo");
        col3.setCellValueFactory(new PropertyValueFactory<Emprestimo, LocalDate>("dataEmprestimo"));

        TableColumn<Emprestimo, LocalDate> col4 = new TableColumn<>("Data da devolução");
        col4.setCellValueFactory(new PropertyValueFactory<Emprestimo, LocalDate>("dataDevolucao"));

        TableColumn<Emprestimo, Long> col5 = new TableColumn<>("Livro");
        col5.setCellValueFactory(new PropertyValueFactory<Emprestimo, Long>("idLivro"));

        TableColumn<Emprestimo, Boolean> col6 = new TableColumn<>("Status");
        col6.setCellValueFactory(new PropertyValueFactory<Emprestimo, Boolean>("status"));
        col6.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "Concluído" : "Pendente");
                }
            }
        });

        tableView.getSelectionModel().selectedItemProperty()
                .addListener((obs, antigo, novo) -> {
                    if (novo != null) {
                        System.out.println("Nome do cliente: " + novo.getNomeCliente());
                        control.paraTela(novo);
                    }
                });
        Callback<TableColumn<Emprestimo, Void>, TableCell<Emprestimo, Void>> cb = new Callback<>() {
            @Override
            public TableCell<Emprestimo, Void> call(
                    TableColumn<Emprestimo, Void> param) {
                TableCell<Emprestimo, Void> celula = new TableCell<>() {
                    final Button btnApagar = new Button("Apagar");

                    {
                        btnApagar.setOnAction(e -> {
                            Emprestimo Emprestimo = tableView.getItems().get(getIndex());
                            try {
                                control.excluir(Emprestimo);
                            } catch (BibliotecaException err) {
                                new Alert(AlertType.ERROR, "Erro ao excluir o empréstimo", ButtonType.OK).showAndWait();
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

        TableColumn<Emprestimo, Void> col7 = new TableColumn<>("Ação");
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
        LongStringConverter longConverter = new LongStringConverter();
        Bindings.bindBidirectional(control.nomeClienteProperty(), txtNomeCliente.textProperty());
        Bindings.bindBidirectional(control.dataEmprestimoProperty(), dpDataEmprestimo.valueProperty());
        Bindings.bindBidirectional(control.dataDevolucaoProperty(), dpDataDevolucao.valueProperty());
        Bindings.bindBidirectional(txtIdLivro.textProperty(), control.idLivroProperty(),
                (StringConverter) longConverter);
        Bindings.bindBidirectional(control.statusProperty(), cbStatus.valueProperty());
    }

}
