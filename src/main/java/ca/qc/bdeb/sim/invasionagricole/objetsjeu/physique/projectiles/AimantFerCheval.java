
package ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.projectiles;

import ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.Position;
import ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.vivant.Vaisseau;

public class AimantFerCheval extends Projectile {
    private static final double CONSTANTE_COULOMB = 1500;
    private static final double CHARGE_AIMANT = 100;

    public AimantFerCheval(double posX, double posY, String pathImageProjectile, double largeurNiveau, double hauteurNiveau, Vaisseau vaisseau) {
        super(posX, posY, pathImageProjectile, largeurNiveau, hauteurNiveau, vaisseau, 42, 40);
        this.gravite = 0; // Pas de gravité pour l'aimant
    }

    @Override
    public void update(double deltaTime) {
        // Calculer la direction
        calculerDirectionVersVaisseau(this.calculerPositionCentre(Position.HORIZONTAL), this.calculerPositionCentre(Position.VERTICAL),vaisseauTarget.calculerPositionCentre(Position.HORIZONTAL), vaisseauTarget.calculerPositionCentre(Position.VERTICAL));

        // Loi de coulomb
        appliquerLoiCoulomb();

        updatePhysique(deltaTime);

        limiterMagnitudeVitesse(300);

    }


    private void appliquerLoiCoulomb(){
        // Loi de Coulomb
        double forceElectrique = (CONSTANTE_COULOMB * CHARGE_AIMANT * this.vaisseauTarget.getCHARGE_VAISSEAU()) / (distance * distance);

        this.aX = forceElectrique * proportionX;
        this.aY = forceElectrique * proportionY;
    }

}
