package ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.vivant;

import ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.ObjetPhysique;
import javafx.scene.image.Image;

public class Vache extends ObjetPhysique {
    private double vitessePrecedenteCollision = 0;

    public Vache(double largeurNiveau, double hauteurNiveau) {

        super(0, hauteurNiveau, "vache.png", largeurNiveau, hauteurNiveau, 136, 134);
        this.nombreDeVie = 1;
        this.vY = 0;

        this.posY = posY - this.hauteur; // Ce code ne marche pas, elle ne change pas le posY

        this.posX = genererPositionX();
        this.vX = determinerVitesse();
        this.ancienneVitesse = this.vX;
        // Changer image selon vitesse
        changerImageSelonVitesse();
    }

    @Override
    public void update(double deltaTime) {

        // Si objet en collision, alors changer de direction de vitesse et changer image
        if (this.estEnCollisionLimiteHorizontal){
            // Quand l'objet est en collision, son vX devient 0, donc il faut reinitialiser la vitesse
            vX = -1 * vitessePrecedenteCollision;
            vitessePrecedenteCollision = vX;
            changerImageSelonVitesse();

            // Update la vitesse pour le rayon enlevement
            this.ancienneVitesse = this.vX;
        }
        updateMonterPhysique();
        updatePhysique(deltaTime);

        estToucherLimites(this.largeurNiveau, this.hauteurNiveau, true);
    }

    /**
     * Methode pour changer l'image de vache si elle va à gauche ou à droite
     */
    private void changerImageSelonVitesse(){
        if (vX < 0){
            this.image = new Image("vache.png");
        }else{
            this.image = new Image("vache-droite.png");
        }
    }

    // determine une vitesse de depart entre 10 et 50 px/s vers la droite ou la gauche
    private double determinerVitesse() {
        double vitesse = 10 + (Math.random() * (50 - 10 + 1));
        vitesse *= determinerDirection();
        vitessePrecedenteCollision = vitesse;
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
}
