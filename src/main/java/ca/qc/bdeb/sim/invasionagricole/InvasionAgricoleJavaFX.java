package ca.qc.bdeb.sim.invasionagricole;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class InvasionAgricoleJavaFX extends Application {
    public final static int LARGEUR_FENETRE = 900;
    public final static int HAUTEUR_FENETRE = 520;


    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(creerSceneAcceuil(stage));

        stage.show();
    }


    private Scene creerSceneAcceuil(Stage stage) throws IOException {
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
                montrerMessageErreur("Erreur: impossible de visiter infos");
                throw new RuntimeException(e);
            }
        });


        buttonJouer.setOnMouseClicked(event -> {
            try {
                stage.setScene(creerSceneJouer(stage));
            } catch (IOException e) {
                montrerMessageErreur("Erreur: impossible de jouer");
                throw new RuntimeException(e);
            }
        });

        containeurDeButtons.getChildren().addAll(buttonJouer, buttonInfos);

        root.getChildren().addAll(containeurDeButtons);

        // Ajouter les touches a input et sur escape retourner acceuil
        ajouterDetectionTouche(scene,stage, ToucheEscape.QUITTER_PROGRAMME);


        return scene;
    }

    private Scene creerSceneInfos(Stage stage) throws IOException {
        var root = new VBox();
        root.setBackground(Background.fill(Color.BLACK));

        Scene scene = new Scene(root, LARGEUR_FENETRE, HAUTEUR_FENETRE);

        // Baniere de visage d'animaux
        HBox hBoxVisagesAnimaux = creerBanniereAliensVaches();

        // Container pour le texte et button
        VBox containerTextButton = new VBox();
        containerTextButton.setAlignment(Pos.CENTER);
        containerTextButton.setSpacing(20);
        containerTextButton.setPadding(new Insets(80,0,0,0));

        // Buttons
        Button buttonRetournerAcceuil = creerButton("Retourner");

        //Textes
        Text titreProjet = new Text("Invasion Agricole");
        Text nomDesCoequipier = new Text("Par Hayk Alaverdyan\net Alexandre Lupascu");
        Text textDesriptionProjet = new Text("Travail remis à Nicolas Hurtubise et Salwa Mbarek. Graphismes " +
                "adaptés de https://game-icons.net/ et de https://openclipart.org/. Développé dans le cadre du cours " +
                "420-203-RE • Développement de programmes dans un environnement graphique, au Collège de " +
                "Bois-de-Boulogne.");

        // Ajouter les fonts
        mettreFontTitre(titreProjet);
        mettreFontSousTitre(nomDesCoequipier);
        mettreFontTextDescription(textDesriptionProjet);

        // Ajouter au container
        containerTextButton.getChildren().addAll(titreProjet, nomDesCoequipier, textDesriptionProjet, buttonRetournerAcceuil);

        // Interactions avec les buttons
        buttonRetournerAcceuil.setOnMouseClicked(event -> {
            try {
                stage.setScene(creerSceneAcceuil(stage));
            } catch (IOException e) {
                montrerMessageErreur("Erreur: impossible de retourner vers acceuil");
                throw new RuntimeException(e);
            }
        });

        root.getChildren().addAll(hBoxVisagesAnimaux, containerTextButton);

        // Ajouter les touches a input et sur escape retourner acceuil
        ajouterDetectionTouche(scene, stage, ToucheEscape.RETOURNER_ACCEUIL );


        return scene;
    }


    private Scene creerSceneJouer(Stage stage) throws IOException {
        var root = new VBox();
        Scene scene = new Scene(root, LARGEUR_FENETRE, HAUTEUR_FENETRE);
        Canvas canvas = new Canvas(LARGEUR_FENETRE, HAUTEUR_FENETRE);
        GraphicsContext context = canvas.getGraphicsContext2D();

        // Creation du controlleur de jeu
        ControleurJeu controleurJeu = new ControleurJeu();

        // Commencer a jouer
        var timer = new AnimationTimer() {
            long lastTime = System.nanoTime();

            @Override
            public void handle(long now) {
                // Verifier si jeu est fini (perdu)
                if (controleurJeu.estJeuFini()){
                    // Arreter et retourner vers acceuile
                    stop();
                    try {
                        stage.setScene(creerSceneAcceuil(stage));
                    } catch (IOException ex) {
                        montrerMessageErreur("Erreur: impossible de retourner vers acceuil");
                        throw new RuntimeException(ex);
                    }
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

        root.getChildren().add(canvas);

        // Ajouter les touches a input et sur escape retourner acceuil
        ajouterDetectionTouche(scene, stage, ToucheEscape.RETOURNER_ACCEUIL);

        return scene;
    }


    private void ajouterDetectionTouche(Scene scene, Stage stage, ToucheEscape toucheEscape) {
        // Ajouter touche a input
        scene.setOnKeyPressed((e) -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                switch (toucheEscape) {
                    case QUITTER_PROGRAMME -> {
                        Platform.exit();
                    }
                    case RETOURNER_ACCEUIL -> {
                        try {
                            stage.setScene(creerSceneAcceuil(stage));
                        } catch (IOException ex) {
                            montrerMessageErreur("Erreur: impossible de retourner vers acceuil");
                            throw new RuntimeException(ex);
                        }
                    }
                }

            }
            Input.setKeyPressed(e.getCode(), true);
        });

        //Enlever la touche quand on n'appuie plus
        scene.setOnKeyReleased((e) -> {
            Input.setKeyPressed(e.getCode(), false);
        });
    }

    private void mettreFontTitre(Text text){
        text.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD,30));
        text.setWrappingWidth(LARGEUR_FENETRE * 0.7);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFill(Color.WHITE);
    }

    private void mettreFontSousTitre(Text text){
        text.setFont(Font.font("Arial", FontWeight.BOLD,20));
        text.setWrappingWidth(LARGEUR_FENETRE * 0.7);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFill(Color.WHITE);
    }

    private void mettreFontTextDescription(Text text){
        text.setFont(Font.font("Arial", FontPosture.ITALIC, 15));
        text.setWrappingWidth(LARGEUR_FENETRE * 0.7);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFill(Color.LIGHTGRAY);
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

    private void montrerMessageErreur(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.show();
    }


    /**
     * Methode pour creer un Button avec un certain formattage
     * @param texteButton est le texte du boutton
     * @return un button formatter
     */
    private Button creerButton(String texteButton) {
        Button button = new Button(texteButton);
        button.setTextFill(Color.GREEN);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        button.setCursor(Cursor.HAND);

        button.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), null)));
        button.setBorder(new Border
                (new BorderStroke
                        (Color.GREEN, BorderStrokeStyle.SOLID, new CornerRadii(10),
                                new BorderWidths(1))));
        button.setOnMouseEntered(event -> {
            button.setBackground(new Background(new BackgroundFill(Color.GREEN, new CornerRadii(10), null)));
            button.setTextFill(Color.WHITE);
        });
        button.setOnMouseExited(event -> {
            button.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), null)));
            button.setTextFill(Color.GREEN);
        });
        return button;
    }


    public static void main(String[] args) {
        launch();
    }
}