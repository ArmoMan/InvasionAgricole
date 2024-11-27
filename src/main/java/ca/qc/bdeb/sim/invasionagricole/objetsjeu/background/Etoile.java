package ca.qc.bdeb.sim.invasionagricole.objetsjeu.background;

import ca.qc.bdeb.sim.invasionagricole.Camera;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Random;

/**
 * Classe pour une etoile. Taille générée aléatoirement.
 */
public class Etoile extends ObjetArrierePlan {
    // Le symbole de base est *
    private String symboleAffichage = "*";

    private int taillePolice;


    public Etoile(int posX, int posY) {
        super(posX, posY);

        // Donner taille entre 8 et 15
        Random random = new Random();
        taillePolice = random.nextInt(8,15);
    }

    @Override
    public void dessiner(GraphicsContext context, Camera camera) {
        super.dessiner(context, camera);
        context.setFill(Color.WHITE);
        context.setFont(new Font(taillePolice));
        context.fillText(symboleAffichage, xEcran, yEcran);
    }
}
