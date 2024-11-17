package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Humain extends ObjetPhysique {

    private Camera camera;
    private double tempsDepuisDernierProjectile;
    private double delaiProjectile;
    private ArrayList<Projectile> projectiles;
    private Vaisseau vaisseau;

    public Humain(double posY, double largeurNiveau, double hauteurNiveau, String pathImageHumain, Camera camera, ArrayList<Projectile> projectiles, Vaisseau vaisseau) {
        super(0 ,posY, pathImageHumain,largeurNiveau,hauteurNiveau, 83, 185);
        this.nombreDeVie = 1;
        this.posX = genererPositionX();
        this.posY = posY - this.hauteurImage;
        this.camera = camera;
        this.tempsDepuisDernierProjectile = Math.random() * 2; // Entre 0 et 2 secondes
        this.delaiProjectile = 2.0;
        this.projectiles = projectiles;
        this.vaisseau = vaisseau;
    }

    @Override
    public void update(double deltaTime) {
        updateMonterPhysique();
        updatePhysique(deltaTime);
        estToucherLimites(this.largeurNiveau, this.hauteurNiveau,true);
        if (!estHorsCamera(camera)) {

            tempsDepuisDernierProjectile += deltaTime;
            if (tempsDepuisDernierProjectile >= delaiProjectile) {
                lancerProjectile(calculerPositionCentre(posX, largeurImage), calculerPositionCentre(posY, hauteurImage), projectiles);
                tempsDepuisDernierProjectile = 0;
            }
        }
    }


    @Override
    public void dessiner(GraphicsContext context, Camera camera) {
        double xEcran = camera.calculerEcranX(posX);
        double yEcran = camera.calculerEcranY(posY);  // le code ici se repete, il faudrait faire un super.dessiner()

        context.drawImage(this.image, xEcran, yEcran, this.largeurImage, this.hauteurImage);
    }


    public boolean estVisible(Camera camera) {
        if (posX > camera.getGauche() && posX < camera.getDroite()) {
            return true;
        } else {
            return false;
        }
    }

    private void lancerProjectile(double posX, double posY, ArrayList<Projectile> projectiles) {
        projectiles.add(creerProjectile(posX, posY));
    }

    private Projectile creerProjectile(double posX, double posY) {
        int choix = (1 + (int) (Math.random() * 3)); // genere un chiffre au hasard entre 1 et 3
        switch (choix) {
            case 1 -> {
                return new Girouette(posX,posY,"girouette.png", largeurNiveau, hauteurNiveau, vaisseau);
            }
            case 2 -> {
                return new AimantFerCheval(posX,posY,"aimant.png", largeurNiveau, hauteurNiveau, vaisseau);
            }
            case 3 -> {
                return new PotDeFleurs(posX,posY,"pot-de-fleurs.png", largeurNiveau, hauteurNiveau, vaisseau);
            }
        }
        return null;
    }


    public double getPosCentreX() {
        return posX + this.largeurImage / 2;
    }

    public double getPosCentreY() {
        return posY + this.hauteurImage / 2;
    }
}
