package ca.qc.bdeb.sim.invasionagricole;

public class Camera {
    private double xCamera, yCamera;

    // Modifier cette classe !!! Ca c'est juste un essai !!!!
    public Camera() {
        this.xCamera = 100;
        this.yCamera = 100;
    }

    // ajouter une methode pour suivre le personnage principal qu’on contrôle au clavier
    // voir JavaFX – Animations V , diapo 33 (29 / 34 pour le prof
    // ...
    // ...
    // ...

    public double calculerEcranX(double xObjet) {
        return xObjet - xCamera;
    }
    public double calculerEcranY(double yObjet) {
        return yObjet - yCamera;
    }
}
