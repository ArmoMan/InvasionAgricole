package ca.qc.bdeb.sim.invasionagricole;

public class Girouette extends Projectile {

    public Girouette(double posX, double posY, String pathImageProjectile, double largeurNiveau, double hauteurNiveau, Vaisseau vaisseau) {
        super(posX, posY, pathImageProjectile, largeurNiveau, hauteurNiveau, vaisseau);
        this.gravite = 0;
        this.largeur = 53;
        this.hauteur = 55;

        // Calcul du direction vers centre du vaisseau
        calculerDirectionVersVaisseau(calculerPositionCentre(vaisseauTarget.getPosX(), vaisseauTarget.getLargeur()), calculerPositionCentre( vaisseauTarget.getPosY(), vaisseauTarget.getHauteur()), posX, posY);

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
