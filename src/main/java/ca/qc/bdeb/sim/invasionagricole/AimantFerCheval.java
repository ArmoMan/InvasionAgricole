package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.canvas.GraphicsContext;

public class AimantFerCheval extends Projectile {
    private final double CONSTANTE_COULOMB = 1500;
    private final double CHARGE_AIMANT = 100;

    public AimantFerCheval(double posX, double posY, String pathImageProjectile, double largeurNiveau, double hauteurNiveau, Vaisseau vaisseau) {
        super(posX, posY, pathImageProjectile, largeurNiveau, hauteurNiveau, vaisseau);
        // Ne subit pas de gravité
        this.gravite = 0;

        // Ca hauteur et largeur
        this.largeur = 42;
        this.hauteur = 40;

        // Pour l'aimant, l'objet 1 devient lui, donc recalculer la distance selon cela
        calculerDirectionVersVaisseau(posX,posY,calculerPositionCentre(vaisseauTarget.getPosX(), vaisseauTarget.getLargeur()), calculerPositionCentre( vaisseauTarget.getPosY(), vaisseauTarget.getHauteur()));


    }

    @Override
    public void update(double deltaTime) {

        // Loi de coulomb
        double forceElectrique = (CONSTANTE_COULOMB * CHARGE_AIMANT * this.vaisseauTarget.getCHARGE_VAISSEAU()) / (distance * distance);

        // On calcule la proportion de la force en X vs en Y
        double forceEnX = forceElectrique * proportionX;
        double forceEnY = forceElectrique * proportionY;

        // La force ici est égale à l'accélération directement
        this.aY = forceEnY;
        this.aX = forceEnX;


        // Metre a jour la physique/position
        updatePhysique(deltaTime);

        // Limiter la magnitude de la vitesse à 300
        limiterVitesse(300);
    }

}
