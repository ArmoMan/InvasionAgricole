package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Humain extends ObjetPhysique {


    public Humain(int posX, int posY, String pathImageHumain) {
        super(posX, posY, pathImageHumain);
    }

    @Override
    protected void updatePhysique(double deltaTemps) {

    }

    @Override
    protected void dessiner(GraphicsContext context, Camera camera) {

    }
}
