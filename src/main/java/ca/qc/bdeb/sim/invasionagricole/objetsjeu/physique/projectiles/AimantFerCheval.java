
package ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.projectiles;

import ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.Position;
import ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.vivant.Vaisseau;

public class AimantFerCheval extends Projectile {
    // Constante de Coulomb modifiée à fin de rendre l'aimant plus facile à éviter.
    private static final double CONSTANTE_COULOMB = 400;
    private static final double CHARGE_AIMANT = 100;

    public AimantFerCheval(double posX, double posY, String pathImageProjectile, double largeurNiveau,
                           double hauteurNiveau, Vaisseau vaisseau) {
        super(posX, posY, pathImageProjectile, largeurNiveau, hauteurNiveau, vaisseau, 42, 40);
        gravite = 0; // Pas de gravité pour l'aimant
    }

    @Override
    public void update(double deltaTime) {
        calculerProprietesDirectionnelles(
                calculerPositionCentre(Position.HORIZONTAL),
                calculerPositionCentre(Position.VERTICAL),
                vaisseau.calculerPositionCentre(Position.HORIZONTAL),
                vaisseau.calculerPositionCentre(Position.VERTICAL));
        appliquerLoiCoulomb();
        updatePhysique(deltaTime);
        limiterMagnitudeVitesse(300);
    }

    /**
     * Change l'accélération de l'aimant à fin qu'il suive le vaisseau selon la loi de Coulomb.
     */
    private void appliquerLoiCoulomb(){
        // Loi de Coulomb
        double forceElectrique = (CONSTANTE_COULOMB * CHARGE_AIMANT * this.vaisseau.getCHARGE_VAISSEAU()) /
                (distance * distance);

        aX = forceElectrique * proportionX;
        aY = forceElectrique * proportionY;
    }
}
