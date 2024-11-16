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
        calculerDirectionVersVaisseau();
        System.out.println("un projectile a ete creer");
    }

    protected void calculerDirectionVersVaisseau(){
        // Calcul des composantes de vitesse
        deltaX = vaisseauTarget.getPosX() - posX;
        deltaY = vaisseauTarget.getPosY() - posY;

        // Longueur du vecteur directionnel
        distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        proportionX = deltaX / distance;
        proportionY = deltaY / distance;
    }

    @Override
    public void dessiner(GraphicsContext context, Camera camera) {
        double xEcran = camera.calculerEcranX(posX);
        double yEcran = camera.calculerEcranY(posY);  // le code ici se repete, il faudrait faire un super.dessiner()

        context.drawImage(this.image, xEcran, yEcran);
        System.out.println("Un projectile a ete dessine");
    }
}
