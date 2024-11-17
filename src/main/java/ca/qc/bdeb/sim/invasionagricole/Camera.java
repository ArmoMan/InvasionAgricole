package ca.qc.bdeb.sim.invasionagricole;

public class Camera {
    private double xCamera, yCamera, largeurCamera,hauteurCamera;
    private boolean arreterDeSuivre  = false;
    // Modifier cette classe !!! Ca c'est juste un essai !!!!
    public Camera(double largeurCamera, double hauteurCamera) {
        // Camera commence a la position 0,0
        this.xCamera = 0;
        this.yCamera = 0;
        this.largeurCamera = largeurCamera;
        this.hauteurCamera = hauteurCamera;
    }

    public double calculerEcranX(double xObjet) {
        return xObjet - xCamera;
    }

    public double calculerEcranY(double yObjet) {
        return yObjet - yCamera;
    }

    public void suivre(Vaisseau vaisseau, double largeurNiveau) {

        //Verifier que vaisseau pas mort avant de suivre
        if (vaisseau.getNombreDeVie() <= 0) return;

        double limiteGauche = xCamera + this.largeurCamera * 0.3;
        double limiteDroite = xCamera + this.largeurCamera * 0.7;
        double xCameraMax =  largeurNiveau - this.largeurCamera;

        // Si le vaisseau est moins 30% de l'ecran et que la camera n'est pas a son minimmum, alors deplacer la camera
        if(vaisseau.getGauche() < limiteGauche && xCamera > 0) {
            this.xCamera = Math.max( vaisseau.getGauche() - largeurCamera *0.3,0);
        }

        // Si le vaisseau depasse 70% de l'ecran et que la camera n'est pas a son maximum, alors deplacer la camera
        if(vaisseau.getDroite() > limiteDroite && xCamera < xCameraMax){
            this.xCamera = Math.min( vaisseau.getDroite()- largeurCamera *0.7, xCameraMax);
        }

    }

    public double getGauche() {
        return xCamera;
    }
    public double getDroite() {
        return xCamera+ largeurCamera;
    }
    public double getHaut() {
        return yCamera;
    }
    public double getBas() {
        return yCamera+hauteurCamera;
    }
    public double getLargeurCamera() {
        return largeurCamera;
    }
    public double getHauteurCamera() {
        return hauteurCamera;
    }


}
