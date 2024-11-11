package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

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
    protected void dessiner(GraphicsContext context,Camera camera) {
        super.dessiner(context, camera);
        context.setFill(Color.WHITE);
        context.fillText(symboleAffichage, posX, posY);
    }
}
