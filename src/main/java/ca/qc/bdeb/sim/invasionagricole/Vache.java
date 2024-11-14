package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Vache extends ObjetPhysique {
    private double vitesseInitiale = 0;
    public Vache(double posX, double posY,double largeurNiveau, double hauteurNiveau) {

        super(posX, posY, "vache.png", largeurNiveau, hauteurNiveau);
        this.nombreDeVie = 1;
        this.vY = 0;

        // Commencer sur terre
        this.largeur = 136;
        this.hauteur = 134;
        this.posY = posY - this.hauteur;
        this.vX = determinerVitesse();
    }

    @Override
    public void update(double deltaTime) {
        updatePhysique(deltaTime);
        verifierQuePasHorsNiveau(this.largeurNiveau, this.hauteurNiveau, 100);

        // Si objet en collision, alors changer de direction de vitesse et changer image
        if (this.estEnCollisionHorizontal){
            // Quand l'objet est en collision, ca vX devient 0, donc il faut reinitialiser la vitesse
            vX = -1 * vitesseInitiale;
            changerImageSelonVitesse();
        }

    }

    private void changerImageSelonVitesse(){
        if (vX < 0){
            this.imagePersonnage = new Image("vache.png");
        }else{
            System.out.println(vX);
            this.imagePersonnage = new Image("vache-droite.png");
        }
    }

    // determine une vitesse de depart entre 10 et 50 px/s vers la droite ou la gauche
    private double determinerVitesse() {
        double vitesse = 10 + (Math.random() * (50 - 10 + 1));
        vitesse *= determinerDirection();
        vitesseInitiale = vitesse;
        changerImageSelonVitesse();
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
        context.drawImage(this.imagePersonnage, xEcran, yEcran);
    }
}
