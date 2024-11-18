package ca.qc.bdeb.sim.invasionagricole.objet.physique.projectiles;

import ca.qc.bdeb.sim.invasionagricole.objet.physique.Position;
import ca.qc.bdeb.sim.invasionagricole.objet.physique.vivant.Vaisseau;

public class Girouette extends Projectile {

    public Girouette(double posX, double posY, String pathImageProjectile, double largeurNiveau, double hauteurNiveau, Vaisseau vaisseau) {
        super(posX, posY, pathImageProjectile, largeurNiveau, hauteurNiveau, vaisseau, 53, 55);
        this.gravite = 0;

        // Calcul du direction du centre de girouette vers centre du vaisseau
        calculerDirectionVersVaisseau(vaisseauTarget.calculerPositionCentre(Position.HORIZONTAL), vaisseauTarget.calculerPositionCentre(Position.VERTICAL), this.calculerPositionCentre(Position.HORIZONTAL), this.calculerPositionCentre(Position.VERTICAL));

        // Normalisation et calcul des vitesses
        vX = this.proportionX * 500;
        vY = this.proportionY * 500;

    }

    @Override
    public void update(double deltaTime) {
        // Mise à jour de la position
        updatePhysique(deltaTime);
    }
}
