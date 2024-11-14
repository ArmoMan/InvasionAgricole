package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.canvas.GraphicsContext;

public class PotDeFleurs extends Projectile {

    public PotDeFleurs(double posY, String pathImageProjectile, double largeurNiveau, double hauteurNiveau) {
        super(posY, pathImageProjectile, largeurNiveau, hauteurNiveau);
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void dessiner(GraphicsContext context, Camera camera) {

    }
}
