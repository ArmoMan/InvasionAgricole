package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.canvas.GraphicsContext;

public class AimantFerCheval extends Projectile {

    public AimantFerCheval(double posX, double posY, String pathImageProjectile, double largeurNiveau, double hauteurNiveau, Vaisseau vaisseau) {
        super(posX, posY, pathImageProjectile, largeurNiveau, hauteurNiveau, vaisseau);
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void dessiner(GraphicsContext context, Camera camera) {

    }
}
