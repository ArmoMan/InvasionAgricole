package ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.projectiles;

import ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.Position;
import ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.vivant.Vaisseau;

public class PotDeFleurs extends Projectile {

    public PotDeFleurs(double posX, double posY, String pathImageProjectile, double largeurNiveau,
                       double hauteurNiveau, Vaisseau vaisseau) {

        super(posX, posY, pathImageProjectile, largeurNiveau, hauteurNiveau, vaisseau, 31, 61);

        // Les humains visent a 400 px plus haut que le vaisseau, donc modifier la direction de deplacement
        double cibleX = vaisseauTarget.calculerPositionCentre(Position.HORIZONTAL);
        double cibleY = vaisseauTarget.calculerPositionCentre(Position.VERTICAL)-400;

        calculerDirectionVersVaisseau(cibleX, cibleY,posX, posY);

        // Vitesse initiale de magnitude 1000
        vX = this.proportionX * 1000;
        vY = this.proportionY * 1000;

    }

    @Override
    public void update(double deltaTime) {
        // Mettre a jour la physique / position
        updatePhysique(deltaTime);
    }

}
