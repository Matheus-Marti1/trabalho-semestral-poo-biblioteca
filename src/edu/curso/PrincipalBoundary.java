package edu.curso;

import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PrincipalBoundary extends Application {

    private Map<String, Tela> telas = new HashMap<>();

    @Override
    public void start(Stage stage) throws Exception {

        telas.put("emprestimo", new EmprestimoBoundary());
        telas.put("livro", new LivroBoundary());
        telas.put("creditos", new CreditosBoundary());

        BorderPane panePrincipal = new BorderPane();

        MenuBar menuBar = new MenuBar();

        Menu menuCadastro = new Menu("Cadastro");
        Menu menuAjuda = new Menu("Ajuda");

        MenuItem menuItemLivro = new MenuItem("Livros");
        menuItemLivro.setOnAction(e -> panePrincipal.setCenter(telas.get("livro").render()));
        MenuItem menuItemEmprestimo = new MenuItem("Empréstimos");
        menuItemEmprestimo.setOnAction(e -> panePrincipal.setCenter(telas.get("emprestimo").render()));
        MenuItem menuItemCreditos = new MenuItem("Créditos");
        menuItemCreditos.setOnAction(e -> panePrincipal.setCenter(telas.get("creditos").render()));

        menuCadastro.getItems().addAll(menuItemLivro);
        menuCadastro.getItems().addAll(menuItemEmprestimo);
        menuAjuda.getItems().add(menuItemCreditos);

        menuBar.getMenus().addAll(menuCadastro, menuAjuda);

        panePrincipal.setTop(menuBar);
        Scene scn = new Scene(panePrincipal, 800, 600);
        stage.setScene(scn);
        stage.setTitle("Sistema de Empréstimos de Livros");
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
