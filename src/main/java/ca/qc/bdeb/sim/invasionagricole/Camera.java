package ca.qc.bdeb.sim.invasionagricole;

import ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.vivant.Vaisseau;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Camera {

    private double xCamera, yCamera, largeurCamera, hauteurCamera;

    public Camera(double largeurCamera, double hauteurCamera) {
        // Camera commence a la position 0,0
        this.xCamera = 0;
        this.yCamera = 0;
        this.largeurCamera = largeurCamera;
        this.hauteurCamera = hauteurCamera;
    }

    /**
     * Permet de calculer la position en x à afficher d'un objet donné.
     * @param xObjet
     * @return xObjetAffichage
     */
    public double calculerEcranX(double xObjet) {
        return xObjet - xCamera;
    }

    /**
     * Permet de calculer la position en y à afficher d'un objet donné.
     * @param yObjet
     * @return yObjetAffichage
     */
    public double calculerEcranY(double yObjet) {
        return yObjet - yCamera;
    }

    /**
     * Permet à la camera de suivre le vaisseau pour l'affichage.
     * @param vaisseau
     * @param largeurNiveau
     */
    public void suivre(Vaisseau vaisseau, double largeurNiveau) {

        //Verifier que vaisseau n'est pas mort avant de suivre.
        if (vaisseau.getNombreDeVie() <= 0) return;

        double limiteGauche = xCamera + this.largeurCamera * 0.3;
        double limiteDroite = xCamera + this.largeurCamera * 0.7;
        double xCameraMax =  largeurNiveau - this.largeurCamera;

        // Si le vaisseau est à moins de 30% de l'écran et que la caméra n'est pas à son minimum, alors déplacer la caméra.
        if(vaisseau.getGauche() < limiteGauche && xCamera > 0) {
            this.xCamera = Math.max( vaisseau.getGauche() - largeurCamera *0.3,0);
        }

        // Si le vaisseau dépasse 70% de l'écran et que la caméra n'est pas à son maximum, alors déplacer la caméra.
        if(vaisseau.getDroite() > limiteDroite && xCamera < xCameraMax){
            this.xCamera = Math.min( vaisseau.getDroite()- largeurCamera *0.7, xCameraMax);
        }
    }

    /**
     * Methode pour dessiner les lignes verticales et horizontales de l'outil débogage.
     */
    public void dessinerDebug(GraphicsContext context){
        context.setStroke(Color.GRAY);
        context.setLineWidth(1);
        context.strokeLine(0, hauteurCamera * 0.6, largeurCamera, hauteurCamera * 0.6);
        context.strokeLine(largeurCamera * 0.3, 0, largeurCamera * 0.3, hauteurCamera);
        context.strokeLine(largeurCamera * 0.7, 0, largeurCamera * 0.7, hauteurCamera);
    }

    /**
     * Methode qui replace la camera a 0,0.
     */
    public void repositionnerPositionZero(){
        this.xCamera = 0;
        this.yCamera = 0;
    }

    public double getGauche() {
        return xCamera;
    }
    public double getDroite() {
        return xCamera + largeurCamera;
    }
    public double getHaut() {
        return yCamera;
    }
    public double getBas() {
        return yCamera + hauteurCamera;
    }



}
