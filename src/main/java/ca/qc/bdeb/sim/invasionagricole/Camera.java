package ca.qc.bdeb.sim.invasionagricole;

public class Camera {
    private double xCamera, yCamera;
    private double largeurEcran;

    // Modifier cette classe !!! Ca c'est juste un essai !!!!
    public Camera(double largeurEcran) {
        // Camera commence a la position 0,0
        this.xCamera = 0;
        this.yCamera = 0;
        this.largeurEcran = largeurEcran;
    }

    public double calculerEcranX(double xObjet) {
        return xObjet - xCamera;
    }

    public double calculerEcranY(double yObjet) {
        return yObjet - yCamera;
    }

    public void suivre(Vaisseau vaisseau, double largeurNiveau) {
        double limiteGauche = xCamera + largeurEcran * 0.3;
        double limiteDroite = xCamera + largeurEcran * 0.7;
        double xCameraMax =  largeurNiveau - largeurEcran;

        // Si le vaisseau est moins 30% de l'ecran et que la camera n'est pas a son minimmum, alors deplacer la camera
        if(vaisseau.getGauche() < limiteGauche && xCamera > 0) {
            this.xCamera = Math.max( vaisseau.getGauche() - largeurEcran*0.3,0);
        }

        // Si le vaisseau depasse 70% de l'ecran et que la camera n'est pas a son maximum, alors deplacer la camera
        if(vaisseau.getDroite() > limiteDroite && xCamera < xCameraMax){
            this.xCamera = Math.min( vaisseau.getDroite()-largeurEcran*0.7, xCameraMax);
        }

    }
}
