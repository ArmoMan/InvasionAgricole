package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.canvas.GraphicsContext;

public class AimantFerCheval extends Projectile {
    private final double CONSTANTE_COULOMB = 1500;
    private final double CHARGE_AIMANT = 100;

    public AimantFerCheval(double posX, double posY, String pathImageProjectile, double largeurNiveau, double hauteurNiveau, Vaisseau vaisseau) {
        super(posX, posY, pathImageProjectile, largeurNiveau, hauteurNiveau, vaisseau);

    }

    @Override
    public void update(double deltaTime) {
        // Verifier que pas de division par 0
        if (this.distance < 1){
            distance = 1;
        }
        double forceElectrique = (CONSTANTE_COULOMB * CHARGE_AIMANT * this.vaisseauTarget.getCHARGE_VAISSEAU()) / (distance * distance);;

        // On calcule la proportion de la force en X vs en Y
        double forceEnX = forceElectrique * proportionX;
        double forceEnY = forceElectrique * proportionY;

        // La force ici est égale à l'accélération directement
        this.aY = forceElectrique * proportionY;
        this.aX = forceElectrique * proportionX;

    }

    @Override
    public void dessiner(GraphicsContext context, Camera camera) {

    }
}
