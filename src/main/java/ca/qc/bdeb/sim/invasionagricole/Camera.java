package ca.qc.bdeb.sim.invasionagricole;

public class Camera {
    private double xCamera, yCamera;
    private double largeurEcran;

    // Modifier cette classe !!! Ca c'est juste un essai !!!!
    public Camera(double largeurEcran) {
        // Camera commence a la position 0,0
        this.xCamera = 0;
        this.yCamera = 0;
    }

    public double calculerEcranX(double xObjet) {
        return xObjet - xCamera;
    }

    public double calculerEcranY(double yObjet) {
        return yObjet - yCamera;
    }

    // A editer cette partie !!!!!!!!!!!!!!!!!!!!!
    public void suivre(Vaisseau alien) {
        if(alien.getGauche() < this.xCamera) {
            this.xCamera = alien.getGauche();
        }

        if(alien.getDroite() > this.xCamera+largeurEcran) {
            this.xCamera = alien.getDroite()-largeurEcran;
        }
    }
}
