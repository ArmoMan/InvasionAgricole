package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Vaisseau extends ObjetPhysique {
    private Color couleurVitre =  Color.rgb(255, 255, 0, 0.6);
    private Image vaisseauLumiereOn = new Image("base-vaisseau-on.png");
    private Image vaisseauLumiereOff = new Image("base-vaisseau-off.png");

    public Vaisseau(int posX, int posY, String pathImageAlien) {
        super(posX, posY, pathImageAlien);
        this.largeur = 100;
        this.hauteur = 140;
    }

    @Override
    protected void updatePhysique(double deltaTemps) {

    }

    @Override
    protected void dessiner(GraphicsContext context, Camera camera) {

    }
}
