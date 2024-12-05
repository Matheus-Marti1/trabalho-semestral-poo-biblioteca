package edu.curso;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class CreditosBoundary implements Tela {

    @Override
    public Pane render() {
        BorderPane panePrincipal = new BorderPane();
        panePrincipal.setTop(new Label("Matheus Augusto Marti"));
        return panePrincipal;
    }
    
    
}
