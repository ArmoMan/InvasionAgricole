package ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.projectiles;

import ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.Position;
import ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.vivant.Vaisseau;

public class Girouette extends Projectile {

    public Girouette(double posX, double posY, String pathImageProjectile, double largeurNiveau,
                     double hauteurNiveau, Vaisseau vaisseau) {
        super(posX, posY, pathImageProjectile, largeurNiveau, hauteurNiveau, vaisseau, 53, 55);
        this.gravite = 0;

        // Calcul du direction du centre de girouette vers centre du vaisseau
        calculerProprietesDirectionnelles(
                vaisseau.calculerPositionCentre(Position.HORIZONTAL),
                vaisseau.calculerPositionCentre(Position.VERTICAL),
                calculerPositionCentre(Position.HORIZONTAL),
                calculerPositionCentre(Position.VERTICAL));

        // Normalisation et calcul des vitesses
        vX = this.proportionX * 500;
        vY = this.proportionY * 500;

    }

    @Override
    public void update(double deltaTime) {
        updatePhysique(deltaTime);
    }
}
