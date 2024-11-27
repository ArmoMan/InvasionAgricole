package ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.projectiles;

import ca.qc.bdeb.sim.invasionagricole.Camera;
import ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.ObjetPhysique;
import ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.vivant.Vaisseau;
import javafx.scene.canvas.GraphicsContext;

public abstract class Projectile extends ObjetPhysique {
    // Calcul des composantes de vitesse
    protected double deltaX;
    protected double deltaY;

    // Longueur du vecteur directionnel
    protected double distance;
    protected double proportionX;
    protected  double proportionY;
    protected Vaisseau vaisseau;

    public Projectile(double posX, double posY, String pathImageProjectile, double largeurNiveau, double hauteurNiveau, Vaisseau vaisseau, double largeur, double hauteur) {
        super(posX, posY, pathImageProjectile, largeurNiveau, hauteurNiveau, largeur, hauteur);
        this.posX = this.posX - largeur /2;
        this.posY = this.posY - hauteur /2;
        this.vaisseau = vaisseau;
    }

    /**
     * Permet de calculer le deltaX, le deltaY, la distance et le vecteur unitaire d'un objet 1 vers un objet 2.
     * @param posXObjetUn position x de l'objet 1
     * @param posYObjetUn position y de l'objet 1
     * @param posXObjetDeux position x de l'objet 2
     * @param posYObjetDeux position t de l'objet 2
     */
    protected void calculerProprietesDirectionnelles(double posXObjetUn, double posYObjetUn, double posXObjetDeux, double posYObjetDeux) {
        // Calcul des composantes de vecteur direction
        deltaX = posXObjetUn - posXObjetDeux;
        deltaY = posYObjetUn - posYObjetDeux;

        // Longueur du vecteur directionnel
        distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        // Verifier que pas de division par 0
        if (this.distance < 1){
            distance = 1;
        }

        // Normaliser la direction
        proportionX = deltaX / distance;
        proportionY = deltaY / distance;
    }

    /**
     * Methode pour limiter la magnitude de la vitesse
     * @param vitesseMax est la vitesse maximale que peut atteindre le projectile
     */
    protected void limiterMagnitudeVitesse(double vitesseMax){
        double magnitudeVitesse =  Math.sqrt(vX * vX + vY * vY);

        // Si depasse vitesse maximale, alors diminuer la vY et vX proportionnelemnt pour arriver a la bonne magnitude
        if (vitesseMax < magnitudeVitesse){
            double proportionVitesseX = vX / magnitudeVitesse;
            double proportionVitesseY = vY / magnitudeVitesse;

            vX = proportionVitesseX * vitesseMax;
            vY = proportionVitesseY * vitesseMax;

        }

    }




}
