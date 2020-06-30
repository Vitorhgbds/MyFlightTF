package pucrs.myflight.gui;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class ExemploPopUp extends Application {

    @Override
    public void start(Stage primaryStage) {
        FlowPane pane = new FlowPane();
        Label label = new Label("Clique em ok para continuar!");
        Button buttonOk = new Button("Ok");
        pane.setHgap(10);
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(label,buttonOk);
        Scene sc = new Scene(pane);
        Stage st = new Stage();
        st.setScene(sc);
        st.setTitle("Mensagem");
        buttonOk.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                st.close();
            }
        });

        Button btn = new Button();
        btn.setText("Clique aqui");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                st.showAndWait();
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Exemplo PopUp Window");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}