package ca.qc.bdeb.sim.invasionagricole;

public abstract class Projectile extends ObjetPhysique {

    public Projectile(double posX, double posY, String pathImageProjectile, double largeurNiveau, double hauteurNiveau) {
        super(posX, posY, pathImageProjectile, largeurNiveau, hauteurNiveau);
    }
}
