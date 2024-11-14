package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.canvas.GraphicsContext;

public class Girouette extends Projectile {

    public Girouette(double posY, String pathImageProjectile, double largeurNiveau, double hauteurNiveau) {
        super(posY, pathImageProjectile, largeurNiveau, hauteurNiveau);
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void dessiner(GraphicsContext context, Camera camera) {

    }
}
