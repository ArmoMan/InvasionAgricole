package ca.qc.bdeb.sim.invasionagricole.objetsjeu.background;

import ca.qc.bdeb.sim.invasionagricole.Camera;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ObjetImage extends ObjetArrierePlan{
    private double largeur, longeur;
    private Image image;

    public ObjetImage(int posX, int posY,String imagePath, double largeur, double longeur) {
        super(posX, posY);
        this.image = new Image(imagePath);
        this.largeur = largeur;
        this.longeur = longeur;
    }

    @Override
    public void dessiner(GraphicsContext context, Camera camera) {
        super.dessiner(context, camera);
        context.drawImage(image, this.xEcran, this.yEcran, largeur, longeur);
    }
}
