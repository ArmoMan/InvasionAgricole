package ca.qc.bdeb.sim.invasionagricole.objet.physique.vivant;

import ca.qc.bdeb.sim.invasionagricole.Camera;
import ca.qc.bdeb.sim.invasionagricole.Input;
import ca.qc.bdeb.sim.invasionagricole.RayonEnlevement;
import ca.qc.bdeb.sim.invasionagricole.objet.physique.ObjetPhysique;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Vaisseau extends ObjetPhysique {

    // Image et couleurs | description physique
    private Color couleurVitre =  Color.rgb(255, 255, 0, 0.6);
    private Image vaisseauLumiereOn = new Image("base-vaisseau-on.png");
    private Image vaisseauLumiereOff = new Image("base-vaisseau-off.png");

    // Fonctionnement du vaisseau et autres elements
    private boolean estLumiereAllumee = false;
    private int nbVachesCapturees = 0;
    private RayonEnlevement rayonEnlevement;

    // Constants
    private final double NOMBRE_DE_VIE_MAX;
    private final double POSITION_Y_INITIALE;
    private final double POSITION_X_INITIALE;
    private final double CHARGE_VAISSEAU = -900; // la charge en coulomb du vaisseau
    private final double LIMITE_VITESSE = 600;



    /**
     * Methode pour representer un vaisseau.
     * @param largeurEcran largeur d'ecran. Ensuite ca va le faire commencer le vaisseau au centre de cette position
     * @param posY position y initiale
     * @param pathImageAlien image du conducteur de vaisseau
     * @param largeurNiveau est la largeur du niveau dans laquelle va "jouer" le vaisseau
     * @param hauteurNiveau est la largeur du niveau dans laquelle va "jouer" le vaisseau
     */
    public Vaisseau(double largeurEcran, double posY, String pathImageAlien,double largeurNiveau, double hauteurNiveau) {
        super(0, posY, pathImageAlien, largeurNiveau, hauteurNiveau, 100, 140);

        // Mettre en memoire les position pour commencer toujours de la meme position
        this.POSITION_Y_INITIALE = posY;
        this.POSITION_X_INITIALE = (largeurEcran/2) - this.largeurImage/2;

        // Positionner le vaissau au centre du position donne
        repositionnerAuPosInitiale();

        this.vX = 0;
        this.vY = 0;

        // L'alien commence avec 4 vies et pas de gravite
        this.nombreDeVie = 4;
        this.gravite = 0;
        this.NOMBRE_DE_VIE_MAX = this.nombreDeVie;

        this.pourcentageHauteurANePasDepasser = 60;

        // Creer le rayon
        this.rayonEnlevement = new RayonEnlevement(this);

        this.nbVachesCapturees = 208;

    }

    @Override
    public void update(double deltaTime) {
        if (nombreDeVie > 0){
            boolean left = Input.isKeyPressed(KeyCode.LEFT);
            boolean right = Input.isKeyPressed(KeyCode.RIGHT);
            boolean up = Input.isKeyPressed(KeyCode.UP);
            boolean down = Input.isKeyPressed(KeyCode.DOWN);

            // Acceleration horizontale
            if (left) {
                aX = -2000;
            } else if (right) {
                aX = 2000;
            } else{
                if (vX > 0) {
                    aX = -500;
                } else if (vX < 0) {
                    aX = 500;
                } else {
                    aX = 0;
                }
            }

            // Acceleration verticale
            if (up) {
                aY = -2000;
            } else if (down) {
                aY = 2000;
            } else{
                if (vY > 0) {
                    aY = -500;
                } else if (vY < 0) {
                    aY = 500;
                } else {
                    aY = 0;
                }
            }

            updatePhysique(deltaTime);

            // limiter vitesse à 600
            this.vX = limiterVitesse(vX);
            this.vY = limiterVitesse(vY);

            // Verifier que ca ne depasse pas les limites du niveau
            estToucherLimites(largeurNiveau, hauteurNiveau, true);

            // Si il y a une des 4 touches appuyer alors mettre estLumierAllumer True sinon False
            estLumiereAllumee = isBoutonMouvementActif();

            // Update le rayon d'enlevement
            this.rayonEnlevement.update(deltaTime);
        }else {
            vX = 100;
            vY = 100;
            updatePhysique(deltaTime);
        }

        // Update le rayon
        rayonEnlevement.update(deltaTime);


    }



    private boolean isBoutonMouvementActif(){
        boolean left = Input.isKeyPressed(KeyCode.LEFT);
        boolean right = Input.isKeyPressed(KeyCode.RIGHT);
        boolean up = Input.isKeyPressed(KeyCode.UP);
        boolean down = Input.isKeyPressed(KeyCode.DOWN);
        return (left||right||up||down);
    }

    /**
     * Methode pour limiter la vitesse a LIMITE_VITESSE
     * @param vitesse la vitesse qu'on veut verifier et limiter
     * @return  vitessee la vitesse recalculée
     */
    private double limiterVitesse(double vitesse){
        // Marge de vitesse pour arreter les oscillations
        double margeDeVitesse = 1.0;

        /* Si plus que LIMITE_VITESSE ou moins que -LIMITE_VITESSE, alors remettre la vitesse a LIMITES_VITESSE
         avec la bonne signe*/
        if (Math.abs(vitesse) > LIMITE_VITESSE) return  LIMITE_VITESSE * Math.signum(vitesse);

        // Arreter les petits oscillations quand le vaisseau essaye d'arriver a un arret
        if (Math.abs(vitesse) < margeDeVitesse){
            return 0;
        }

        return vitesse;
    }

    @Override
    public void dessiner(GraphicsContext context, Camera camera) {
        double xEcran = camera.calculerEcranX(posX);
        double yEcran = camera.calculerEcranY(posY);

        // Dessiner le vitre
        context.setFill(couleurVitre);
        context.fillOval(xEcran, yEcran,  this.largeurImage,  this.hauteurImage);

        // Dessiner et dessider quelle base de vaisseau utiliser
        if (estLumiereAllumee){
            context.drawImage(vaisseauLumiereOn,  (xEcran-( vaisseauLumiereOn.getWidth()- this.largeurImage)/2), yEcran + (this.hauteurImage -vaisseauLumiereOn.getHeight()));
        }else {
            context.drawImage(vaisseauLumiereOff,  (xEcran-( vaisseauLumiereOff.getWidth()- this.largeurImage)/2), yEcran + (this.hauteurImage -vaisseauLumiereOff.getHeight()));
        }

        // Dessiner l'alien
        context.drawImage(this.image, (xEcran+( this.largeurImage - this.image.getWidth())/2),yEcran + this.hauteurImage -vaisseauLumiereOff.getHeight()-this.image.getHeight() );

    }


    public void dessinerCompteurVieEtVacheCapturer(GraphicsContext context,double posXdessin, double posYdessin, String pathImageVache,String pathImageAlienIcon, double hauteurImages, double espacementDesElements) {
        Image imageVache = new Image(pathImageVache);
        Image imageAlienIcone = new Image(pathImageAlienIcon);

        // Hauteur des images selon le largeur voulu
        double largeurImageVache = calculerNouveauLargeurImage(imageVache, hauteurImages);
        double largeurImageAlien = calculerNouveauLargeurImage(imageAlienIcone, hauteurImages);

        // Image de vache
        context.drawImage(imageVache, posXdessin, posYdessin,largeurImageVache , hauteurImages);

        // Mettre le font des textes
        context.setFont(new Font(hauteurImages));

        // Mettre la couleur des textes
        context.setFill(Color.WHITE);

        // A chaque fois le texte est plus bas que voulu, donc le baisser moin
        double valeurASoustraire = hauteurImages*0.15;
        double  posYTextes = posYdessin+hauteurImages-valeurASoustraire;
        // Texte de vache
        double posXCompteurVache = posXdessin + largeurImageVache + espacementDesElements;
        context.fillText(String.valueOf(this.nbVachesCapturees), posXCompteurVache, posYTextes);

        // Image d'Alien
        double posXAlien = posXCompteurVache + espacementDesElements + context.getFont().getSize();
        context.drawImage(imageAlienIcone, posXAlien, posYdessin,largeurImageAlien ,hauteurImages );

        // Texte de nombre de vie Alien
        double posXCompteurVie = posXAlien + largeurImageAlien + espacementDesElements;
        String texteNombreDeVie;

        // Si vaisseau invincible, alors afficher :)
        if (Input.modeInvincible){
            texteNombreDeVie = ":)";
        }else {
            texteNombreDeVie = String.valueOf(this.nombreDeVie);
        }
        context.fillText(texteNombreDeVie, posXCompteurVie, posYTextes);

    }

    /**
     * Methode pour ajouter une vie. Limite a NOMBRE_DE_VIE_MAX
     */
    public void ajouterUneVie(){
        if (this.nombreDeVie < NOMBRE_DE_VIE_MAX){
            this.nombreDeVie += 1;
        }
    }

    /**
     * Methode pour recevoir la taille (hauteur) voulu d'une image et de calculer sa nouvelle largeur sans
     * l'ecraser.
     * @param image est l'image que l'on veut calculer la nouvelle largeur
     * @param nouveauHauteur est l'hauteur qui va être donné à l'image
     * @return la nouvelle largeur en respectant la taille de l'image de base
     */
    private double calculerNouveauLargeurImage(Image image, double nouveauHauteur ){
        return image.getWidth() * nouveauHauteur / image.getHeight();
    }

    public void augmenterCompteurVache(int nombreAjoute){
        this.nbVachesCapturees += nombreAjoute;
    }
    public int getNbVachesCapturees() {
        return nbVachesCapturees;
    }

    public void setNbVachesCapturees(int nbVachesCapturees) {
        this.nbVachesCapturees = nbVachesCapturees;
    }

    public double getCHARGE_VAISSEAU() {
        return CHARGE_VAISSEAU;
    }

    public RayonEnlevement getRayonEnlevement() {
        return rayonEnlevement;
    }

    public void setRayonEnlevement(RayonEnlevement rayonEnlevement) {
        this.rayonEnlevement = rayonEnlevement;
    }

    public void repositionnerAuPosInitiale() {
        this.posX = POSITION_X_INITIALE;
        this.posY = POSITION_Y_INITIALE;
    }

}
