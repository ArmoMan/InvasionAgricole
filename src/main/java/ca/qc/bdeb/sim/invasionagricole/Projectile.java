package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.canvas.GraphicsContext;

public abstract class Projectile extends ObjetPhysique {
    // Calcul des composantes de vitesse
    protected double deltaX;
    protected double deltaY;

    // Longueur du vecteur directionnel
    protected double distance;
    protected double proportionX;
    protected  double proportionY;
    protected Vaisseau vaisseauTarget;

    public Projectile(double posX, double posY, String pathImageProjectile, double largeurNiveau, double hauteurNiveau, Vaisseau vaisseau) {
        super(posX, posY, pathImageProjectile, largeurNiveau, hauteurNiveau);
        this.vaisseauTarget = vaisseau;
    }


    @Override
    public void dessiner(GraphicsContext context, Camera camera) {
        double xEcran = camera.calculerEcranX(posX);
        double yEcran = camera.calculerEcranY(posY);  // le code ici se repete, il faudrait faire un super.dessiner()

        context.drawImage(this.image, xEcran, yEcran, this.largeur, this.hauteur);
    }


    /**
     * Methode pour calculer detaX, detaY, distance et n vecteur unitaire d'un objet 1 vers un objet 2
     * @param posXObjetUn
     * @param posYObjetUn
     * @param posXObjetDeux
     * @param posYObjetDeux
     */
    protected void calculerDirectionVersVaisseau(double posXObjetUn, double posYObjetUn, double posXObjetDeux, double posYObjetDeux){
        // Calcul des composantes de vitesse
        deltaX = posXObjetUn - posXObjetDeux;
        deltaY = posYObjetUn - posYObjetDeux;

        // Longueur du vecteur directionnel
        distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        // Verifier que pas de division par 0
        if (this.distance < 1){
            distance = 1;
        }

        proportionX = deltaX / distance;
        proportionY = deltaY / distance;
    }

    /**
     * Methode pour limiter la magnitude de la vitesse
     * @param vitesseMax est la vitesse maximale que peut atteindre le projectile
     */
    protected void limiterVitesse(double vitesseMax){
        double magnitudeVitesse = Math.sqrt(vX * vX + vY * vY);

        // Si depasse vitesse maximale, alors diminuer la vY et vX proportionnelemnt pour arriver a la bonne magnitude
        if (vitesseMax < magnitudeVitesse){
            double proportionVitesseDiminuer = vitesseMax / magnitudeVitesse;
            vX *= proportionVitesseDiminuer;
            vY *= proportionVitesseDiminuer;
        }
    }


}
