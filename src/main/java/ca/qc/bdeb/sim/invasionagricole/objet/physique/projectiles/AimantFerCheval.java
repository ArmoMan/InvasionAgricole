
package ca.qc.bdeb.sim.invasionagricole.objet.physique.projectiles;

import ca.qc.bdeb.sim.invasionagricole.objet.physique.vivant.Vaisseau;

public class AimantFerCheval extends Projectile {
    private static final double CONSTANTE_COULOMB = 1500;
    private static final double CHARGE_AIMANT = 100;

    public AimantFerCheval(double posX, double posY, String pathImageProjectile, double largeurNiveau, double hauteurNiveau, Vaisseau vaisseau) {
        super(posX, posY, pathImageProjectile, largeurNiveau, hauteurNiveau, vaisseau, 42, 40);
        this.gravite = 0; // Pas de gravité pour l'aimant
    }

    @Override
    public void update(double deltaTime) {
        // Calculer les différences de position
        double deltaX = this.calculerPositionCentre(posX, largeurImage) -
                (vaisseauTarget.calculerPositionCentre(vaisseauTarget.getPosX(), vaisseauTarget.getLargeurImage()));
        double deltaY = this.calculerPositionCentre(posY, hauteurImage) -
                (vaisseauTarget.calculerPositionCentre(vaisseauTarget.getPosY(), vaisseauTarget.getHauteurImage()));
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        // Normaliser la direction
        double proportionX = deltaX / distance;
        double proportionY = deltaY / distance;

        // Loi de Coulomb
        double forceElectrique = (CONSTANTE_COULOMB * CHARGE_AIMANT * this.vaisseauTarget.getCHARGE_VAISSEAU()) / (distance * distance);

        this.aX = forceElectrique * proportionX;
        this.aY = forceElectrique * proportionY;

        updatePhysique(deltaTime);
        limiterVitesse(300);
    }

}
