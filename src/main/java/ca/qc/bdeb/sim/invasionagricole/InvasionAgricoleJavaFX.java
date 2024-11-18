package ca.qc.bdeb.sim.invasionagricole;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class InvasionAgricoleJavaFX extends Application {
    public final static int LARGEUR_FENETRE = 900;
    public final static int HAUTEUR_FENETRE = 520;


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
        root.setBackground(new Background(new BackgroundImage(arrierePlanImageAcceuil, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));

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

        containeurDeButtons.getChildren().addAll(buttonJouer, buttonInfos);

        root.getChildren().addAll(containeurDeButtons);
        ajouterDetectionTouche(scene);
        return scene;
    }

    private Scene creerSceneInfos(Stage stage) throws IOException {
        var root = new VBox();
        Scene scene = new Scene(root, LARGEUR_FENETRE, HAUTEUR_FENETRE);

        // Baniere de visage d'animaux
        HBox hBoxVisagesAnimaux = creerBanniereAliensVaches();

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
        ajouterDetectionTouche(scene);
        return scene;
    }

    public void ajouterDetectionTouche(Scene scene) {
        // Ajouter touche a input ou sortir du programme
        scene.setOnKeyPressed((e) -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                Platform.exit();
            } else {
                Input.setKeyPressed(e.getCode(), true);
            }
        });

        //Enlever la touche quand on n'appuie plus
        scene.setOnKeyReleased((e) -> {
            Input.setKeyPressed(e.getCode(), false);
        });
    }

    private Scene creerSceneJouer(Stage stage) throws IOException {
        var root = new VBox();
        Scene scene = new Scene(root, LARGEUR_FENETRE, HAUTEUR_FENETRE);
        Canvas canvas = new Canvas(LARGEUR_FENETRE, HAUTEUR_FENETRE);
        GraphicsContext context = canvas.getGraphicsContext2D();

        // Ca cest un test !! À effacer plus tard !!!!!!!
        ControleurJeu controleurJeu = new ControleurJeu();

        var timer = new AnimationTimer() {
            long lastTime = System.nanoTime();

            @Override
            public void handle(long now) {
                if (controleurJeu.estJeuFini()){
                    return;
                }
                double deltaTemps = (now - lastTime) * 1e-9;
                Input.gererToggleModeDebogage();
                controleurJeu.dessiner(context);
                controleurJeu.update(deltaTemps);
                lastTime = now;
            }
        };
        timer.start();
        ///////////////////

        root.getChildren().add(canvas);
        ajouterDetectionTouche(scene);

        return scene;
    }

    private HBox creerBanniereAliensVaches(){
        // Positionner les images d'allien et vache

        Image visageAlien = new Image("icone.png");
        Image visageVache = new Image("mini-vache.png");

        //Generer un sur deux les images et les placer en haut
        HBox hBoxVisagesAnimaux = new HBox();
        hBoxVisagesAnimaux.setAlignment(Pos.CENTER);

        double tailleTotalImageAjouter = 0;
        boolean estCeTourVache = false;
        while (tailleTotalImageAjouter < LARGEUR_FENETRE) {
            // Choisir l'image d'apres le boolean
            ImageView imageView = estCeTourVache ? new ImageView(visageVache) : new ImageView(visageAlien);
            hBoxVisagesAnimaux.getChildren().add(imageView);

            // Augmenter le nombre de la taille des images
            tailleTotalImageAjouter += imageView.getImage().getWidth();

            // Changer le boolean par son inverse, comme ca, ca va faire des images un sur deux
            estCeTourVache = !estCeTourVache;
        }

        return hBoxVisagesAnimaux;
    }

    private Button creerButton(String texteButton) throws IOException {
        Button button = new Button(texteButton);
        return button;
    }

    public static void main(String[] args) {
        launch();
    }
}