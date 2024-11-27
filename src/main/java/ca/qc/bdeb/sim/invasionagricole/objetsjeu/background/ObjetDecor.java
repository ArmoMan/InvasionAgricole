package ca.qc.bdeb.sim.invasionagricole.objetsjeu.background;

import ca.qc.bdeb.sim.invasionagricole.Camera;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Classe pour les objets qui sont des éléments de décor à afficher. Exemple : grange, tracteur, etc.
 */
public class ObjetDecor extends ObjetArrierePlan {
    private double largeur, longeur;
    private Image image;

    public ObjetDecor(int posX, int posY, String imagePath, double largeur, double longeur) {
        super(posX, posY);
        this.image = new Image(imagePath);
        this.largeur = largeur;
        this.longeur = longeur;
    }

    @Override
    public void dessiner(GraphicsContext context, Camera camera) {
        super.dessiner(context, camera);
        context.drawImage(image, xEcran, yEcran, largeur, longeur);
    }
}