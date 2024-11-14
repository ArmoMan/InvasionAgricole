package ca.qc.bdeb.sim.invasionagricole;

public abstract class Projectile extends ObjetPhysique {

    public Projectile(double posY, String pathImageProjectile, double largeurNiveau, double hauteurNiveau) {
        super(posY, pathImageProjectile, largeurNiveau, hauteurNiveau);
    }
}
