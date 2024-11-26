package ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique;

import ca.qc.bdeb.sim.invasionagricole.Camera;
import ca.qc.bdeb.sim.invasionagricole.Input;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public abstract class ObjetPhysique {

    protected double posX, posY;
    protected double vX = 0, vY =0;
    protected double aX = 0, aY = 0;
    protected Image image;
    protected double largeur, hauteur;
    protected int nombreDeVie;
    protected double largeurNiveau, hauteurNiveau;
    protected double gravite = 1000;
    // Boolean pour voir si l'objet est en collision entre les limites horizontales du niveau
    protected boolean estEnCollisionLimiteHorizontal = false;
    // Boolean pour voir si le physique du rayon doit s'appliquer
    protected boolean estMonter = false;
    // Variable pour garder en memoire une vitesse passé
    protected double ancienVitesse ;
    // La pourcentage de la limite verticale maximale que peut atteindre cette objet. Par defaut: 100%
    protected double pourcentageHauteurANePasDepasser = 100;

    public ObjetPhysique(double posX, double posY, String pathImage,double largeurNiveau, double hauteurNiveau, double largeurImage, double hauteurImage) {
        this.posY = posY;
        this.posX = posX;
        this.image = new Image(pathImage);
        this.largeurNiveau = largeurNiveau;
        this.hauteurNiveau = hauteurNiveau;
        this.largeur = largeurImage;
        this.hauteur = hauteurImage;
    }

    public abstract void update(double deltaTime);

    protected void updatePhysique(double deltaTemps) {
        vX += deltaTemps * aX;
        vY += deltaTemps * (aY+gravite);
        posX += deltaTemps * vX;
        posY += deltaTemps * vY;
    }

    protected void updateMonterPhysique() {
        if (estMonter){
            vY = -100;
            vX = 0;
            aY = 0;
        }else{
            aY = this.gravite;
            vX = this.ancienVitesse;
        }
    }

    public abstract void dessiner(GraphicsContext context, Camera camera);

    /**
     * Methode pour dessiner les lignes jaunes des ObjetPhysiques selon largeur et longeur
     * @param context
     * @param camera
     */
    public void dessinerDebug(GraphicsContext context, Camera camera) {

        // Si la touche de débogage a été appuyée, activer/désactiver le mode débogage
        if (Input.modeDebogageActif) {
            double xEcran = camera.calculerEcranX(posX);
            double yEcran = camera.calculerEcranY(posY);

            context.setStroke(Color.YELLOW);
            context.setLineWidth(2);
            context.strokeRect(xEcran, yEcran, largeur, hauteur);
        }
    }


    /**
     * Methode pour verifier si objet est dans les limites voulues ou non. Permet aussi de repositionner l'objet si voulu.
     * @param posXMax posX maximale atteignable par objet
     * @param posYMax posY maximale atteignable par objet
     * @param repositionnerObjet si vrai, alors il repositionne l'objet en cas de dépassement des limites
     * @return true si l'objet est hors limites et false si pas hors limites
     */
    public boolean estToucherLimites(double posXMax, double posYMax, boolean repositionnerObjet){
        // Cas horizontal
        this.estEnCollisionLimiteHorizontal = false;
        if (this.getDroite() > posXMax) {
            this.estEnCollisionLimiteHorizontal = true;
            if (repositionnerObjet){
                posX = posXMax - largeur;
                vX = 0;
            }
        }else if (this.getGauche() < 0) {
            this.estEnCollisionLimiteHorizontal = true;
            if (repositionnerObjet){
                posX = 0;
                vX = 0;
            }
        }

        // Cas vertical: verifier que l'objet ne depasse pas pourcentageHauteurANePasDepasser de l'hauteur donnée
        boolean estEnCollisionLimiteVerticale = false;
        double limiteDeHauteur = posYMax*(this.pourcentageHauteurANePasDepasser/100);
        if (this.getBas() > limiteDeHauteur) {
            estEnCollisionLimiteVerticale = true;
            if (repositionnerObjet){
                posY = limiteDeHauteur - hauteur;
                vY = 0;
            }
        }else if (this.getHaut() < 0) {
            estEnCollisionLimiteVerticale = true;
            if (repositionnerObjet){
                posY = 0;
                vY = 0;
            }
        }

        return estEnCollisionLimiteVerticale || this.estEnCollisionLimiteHorizontal;

    }

    /**
     * Methode pour generer une position x entre 0 et largeurNiveau - largeur (image)
     * @return position x entre 0 et largeurNiveau - largeur (image)
     */
    protected double genererPositionX(){
        return (Math.random() * (largeurNiveau- largeur));
    }

    public boolean estEnCollisionAvec(ObjetPhysique objetPhysique) {

        // Verifier si objet est entre les x de l'objet actuel
        boolean estCeQuePlusGaucheQuePartieDroiteActuel = this.getGauche()
                + this.largeur > objetPhysique.getGauche();
        boolean estCeQuePlusDroiteQuePartieGaucheActuel = this.getGauche() < objetPhysique.getDroite();

        // Verifier si objet est entre les y de l'objet actuel
        // Verifier si partie le plus bas de l'objet est plus bas que le point le plus haut de l'objet actuel
        boolean estCeQuePlusBasQuePartieHautActuel = this.getPosY() < objetPhysique.getPosY()
                + objetPhysique.getHauteur();

        // Verifier si partie le plus haut de l'objet est plus haut que le point le plus bas de l'objet actuel
        boolean estCeQuePlusHautQuePartieBasActuel = objetPhysique.getPosY() < this.getPosY() + this.hauteur;


        return estCeQuePlusBasQuePartieHautActuel && estCeQuePlusHautQuePartieBasActuel
                && estCeQuePlusDroiteQuePartieGaucheActuel && estCeQuePlusGaucheQuePartieDroiteActuel;

    }

    /**
     * Methode pour calculer le centre Vertical ou horizontal de l'objet
     * @param position est le centre horizontal ou vertical de l'ojbet qu'on veut calculer
     * @return la position du centre horizontal ou vertical
     */
    public double calculerPositionCentre(Position position){
        if (position == Position.HORIZONTAL){
            return posX + (this.largeur /2);
        }else {
            return posY + (this.hauteur /2);
        }
    }

    /**
     * Verifier si objet est hors camera
     * @param camera camera de l'objet
     * @return true si est hors au complet
     */
    public boolean estHorsCamera(Camera camera){
        return (this.getGauche() > camera.getDroite()) || (this.getDroite() < camera.getGauche()) ||
                (this.getHaut() > camera.getBas()) || (this.getBas() < camera.getHaut());
    }

    /**
     * Methode pour enlever une vie si il y a au moins 1 vie
     */
    public void enleverUneVie(){
        if (this.nombreDeVie > 0){
            this.nombreDeVie -= 1;
        }
    }


    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public int getNombreDeVie() {
        return nombreDeVie;
    }

    public void setNombreDeVie(int nombreDeVie) {
        this.nombreDeVie = nombreDeVie;
        if (this.nombreDeVie > 4) {
            this.nombreDeVie = 4;
        }
    }

    public double getHauteur() {
        return hauteur;
    }

    public double getLargeur() {
        return largeur;
    }

    public double getGauche(){
        return this.getPosX();
    }

    public double getDroite(){
        return this.getPosX() + this.largeur;
    }

    public double getBas(){
        return this.getPosY() + this.hauteur;
    }

    public double getHaut(){
        return posY;
    }

    public void setEstMonter(boolean estMonter) {
        this.estMonter = estMonter;
    }
}
