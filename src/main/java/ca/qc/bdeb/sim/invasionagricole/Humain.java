package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Humain extends ObjetPhysique {


    public Humain(int posX, int posY, String pathImageHumain) {
        super(posX, posY, pathImageHumain);
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    protected void updatePhysique(double deltaTemps) {

    }

    @Override
    public void dessiner(GraphicsContext context, Camera camera) {

    }
}
