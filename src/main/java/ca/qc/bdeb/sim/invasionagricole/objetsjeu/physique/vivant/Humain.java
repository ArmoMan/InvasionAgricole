package ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.vivant;

import ca.qc.bdeb.sim.invasionagricole.*;
import ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.ObjetPhysique;
import ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.Position;
import ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.projectiles.AimantFerCheval;
import ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.projectiles.Girouette;
import ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.projectiles.PotDeFleurs;
import ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.projectiles.Projectile;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Humain extends ObjetPhysique {

    private Camera camera;
    private double tempsDepuisDernierProjectile;
    private double delaiProjectile;
    private ArrayList<Projectile> projectiles;
    private Vaisseau vaisseau;

    public Humain(double largeurNiveau, double hauteurNiveau, String pathImageHumain, Camera camera,
                  ArrayList<Projectile> projectiles, Vaisseau vaisseau) {

        super(0 , hauteurNiveau, pathImageHumain,largeurNiveau,hauteurNiveau, 83, 185);
        this.nombreDeVie = 1;
        this.posX = genererPositionX();
        this.posY = posY - this.hauteur;
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
                lancerProjectile(this.calculerPositionCentre(Position.HORIZONTAL),
                        this.calculerPositionCentre(Position.VERTICAL), projectiles);
                tempsDepuisDernierProjectile = 0;
            }
        }
    }

    private void lancerProjectile(double posX, double posY, ArrayList<Projectile> projectiles) {
        projectiles.add(creerProjectile(posX, posY));
    }

    /**
     * Génère un projectile au hasard : soit Girouette, soit AimantFerCheval, soit PotDeFleurs.
     * @param posX Position en X du projectile.
     * @param posY Position en Y du projectile.
     * @return Le projectile généré.
     */
    private Projectile creerProjectile(double posX, double posY) {
        int choix = (1 + (int) (Math.random() * 3)); // génère un chiffre au hasard entre 1 et 3.
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
}
