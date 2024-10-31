package ca.qc.bdeb.sim.invasionagricole;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class InvasionAgricoleJavaFX extends Application {
    private final static int LARGEUR_FENETRE = 900;
    private final static int HAUTEUR_FENETRE = 520;


    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(creerScencAcceuil(stage));
        stage.show();
    }
    
    

    private Scene creerScencAcceuil(Stage stage) throws IOException {
        var root = new VBox();
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, LARGEUR_FENETRE, HAUTEUR_FENETRE);

        // Arriere plan
        Image arrierePlanImageAcceuil = new Image("intro.png");
        root.setBackground(new Background(new BackgroundImage(arrierePlanImageAcceuil,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));

        // Buttons
        VBox containeurDeButtons = new VBox();
        containeurDeButtons.setAlignment(Pos.CENTER);
        containeurDeButtons.setSpacing(20);

        Button buttonJouer = creerButton("Jouer!");
        Button buttonInfos = creerButton("Infos");

        // Interactions avec les buttons
        buttonInfos.setOnMouseClicked(event -> {
            try {
                stage.setScene(creerSceneInfos(stage));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


        buttonJouer.setOnMouseClicked(event -> {
            try {
                stage.setScene(creerSceneJouer(stage));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        containeurDeButtons.getChildren().addAll(buttonJouer,buttonInfos);

        root.getChildren().addAll(containeurDeButtons);
        return scene;
    }

    private Scene creerSceneInfos(Stage stage) throws IOException {
        var root = new VBox();
        Scene scene = new Scene(root, LARGEUR_FENETRE, HAUTEUR_FENETRE);

        // Positionner les images d'allien et vache
        Image visageAllien = new Image("icone.png");
        Image visageVache = new Image("mini-vache.png");

        //Generer un sur deux les images et les placer en haut
        HBox hBoxVisagesAnimaux = new HBox();
        hBoxVisagesAnimaux.setAlignment(Pos.CENTER);

        double tailleTotalImageAjouter = 0;
        boolean estCeTourVache = false;
        while (tailleTotalImageAjouter < LARGEUR_FENETRE) {
            if (estCeTourVache){
                ImageView imageViewVache = new ImageView(visageVache);
                hBoxVisagesAnimaux.getChildren().add(imageViewVache);
                tailleTotalImageAjouter += visageVache.getWidth();
                estCeTourVache = false;
            }else{
                ImageView imageViewAllien = new ImageView(visageAllien);
                hBoxVisagesAnimaux.getChildren().add(imageViewAllien);
                tailleTotalImageAjouter += visageAllien.getWidth();
                estCeTourVache = true;
            }
        }

        // Buttons
        Button buttonRetournerAcceuil = creerButton("Retourner");

        // Interactions avec les buttons
        buttonRetournerAcceuil.setOnMouseClicked(event -> {
            try {
                stage.setScene(creerScencAcceuil(stage));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        root.getChildren().addAll(hBoxVisagesAnimaux, buttonRetournerAcceuil);
        return scene;
    }

    private Scene creerSceneJouer(Stage stage) throws IOException {
        var root = new VBox();
        Scene scene = new Scene(root, LARGEUR_FENETRE, HAUTEUR_FENETRE);

        return scene;
    }

    private Button creerButton(String texteButton) throws IOException {
        Button button = new Button(texteButton);
        return button;
    }

    public static void main(String[] args) {
        launch();
    }
}