package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.canvas.GraphicsContext;

public class Vache extends ObjetPhysique {

    public Vache(int posX, int posY) {
        super(posX, posY, "vache.png");
        this.nombreDeVie = 1;
        this.vY = 0;
        this.vX = determinerVitesse();
    }

    @Override
    public void update(double deltaTime) {
        updatePhysique(deltaTime);
    }

    // determine une vitesse de depart entre 10 et 50 px/s vers la droite ou la gauche
    private double determinerVitesse() {
        double vitesse = 10 + (Math.random() * (50 - 10 + 1));
        vitesse *= determinerDirection();
        return vitesse;
    }

    // determine si la vache avance vers la droite ou la gauche
    private int determinerDirection() {
        if (Math.random() < 0.5) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public void dessiner(GraphicsContext context, Camera camera) {
        double xEcran = camera.calculerEcranX(posX);
        double yEcran = camera.calculerEcranY(posY);  // le code ici se repete, il faudrait faire un super.dessiner()

        // l'affichage n'est pas bon ici
        context.drawImage(this.imagePersonnage, (xEcran+(this.largeur - this.imagePersonnage.getWidth())/2),yEcran + this.imagePersonnage.getHeight() );
    }
}
