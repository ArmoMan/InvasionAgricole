package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.canvas.GraphicsContext;

public abstract class Projectile extends ObjetPhysique {

    public Projectile(double posX, double posY, String pathImageProjectile, double largeurNiveau, double hauteurNiveau) {
        super(posX, posY, pathImageProjectile, largeurNiveau, hauteurNiveau);
    }

    @Override
    public void dessiner(GraphicsContext context, Camera camera) {
        double xEcran = camera.calculerEcranX(posX);
        double yEcran = camera.calculerEcranY(posY);  // le code ici se repete, il faudrait faire un super.dessiner()

        context.drawImage(this.image, xEcran, yEcran);
    }
}
