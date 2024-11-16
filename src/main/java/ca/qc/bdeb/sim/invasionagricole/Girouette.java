package ca.qc.bdeb.sim.invasionagricole;

public class Girouette extends Projectile {

    public Girouette(double posX, double posY, String pathImageProjectile, double largeurNiveau, double hauteurNiveau, Vaisseau vaisseau) {
        super(posX, posY, pathImageProjectile, largeurNiveau, hauteurNiveau, vaisseau);
        this.gravite = 0;

        // Calcul des composantes de vitesse
        double deltaX = vaisseau.getPosX() - posX;
        double deltaY = vaisseau.getPosY() - posY;

        // Longueur du vecteur directionnel
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        // Normalisation et calcul des vitesses
        vX = (deltaX / distance) * 500;
        vY = (deltaY / distance) * 500;
    }

    @Override
    public void update(double deltaTime) {
        // Mise à jour de la position
        this.posX += vX * deltaTime;
        this.posY += vY * deltaTime;

        // Vérification des limites (facultatif)
        estHorsLimites(largeurNiveau, hauteurNiveau, false);
    }
}
