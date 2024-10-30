package ca.qc.bdeb.sim.invasionagricole;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class InvasionAgricole extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        var root = new VBox();
        Scene scene = new Scene(root, 720, 640);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}