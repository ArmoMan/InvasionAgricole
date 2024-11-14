package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Humain extends ObjetPhysique {


    public Humain(double posY, double largeurNiveau, double hauteurNiveau, String pathImageHumain) {
        super(0 ,posY, pathImageHumain,largeurNiveau,hauteurNiveau);
        this.nombreDeVie = 1;
        this.largeur = 83;
        this.hauteur = 185;
        this.posX = genererPositionX();
        this.posY = posY - this.hauteur;
    }

    @Override
    public void update(double deltaTime) {

    }


    @Override
    public void dessiner(GraphicsContext context, Camera camera) {
        double xEcran = camera.calculerEcranX(posX);
        double yEcran = camera.calculerEcranY(posY);  // le code ici se repete, il faudrait faire un super.dessiner()

        context.drawImage(this.imagePersonnage, xEcran, yEcran);
    }
}
