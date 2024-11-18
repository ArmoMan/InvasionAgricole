package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Vaisseau extends ObjetPhysique {

    private Color couleurVitre =  Color.rgb(255, 255, 0, 0.6);
    private Image vaisseauLumiereOn = new Image("base-vaisseau-on.png");
    private Image vaisseauLumiereOff = new Image("base-vaisseau-off.png");
    private double limiteVitesse = 600;
    private boolean estLumiereAllumee = false;
    private int nbVachesCapturees = 0;
    private final double CHARGE_VAISSEAU = -900; // la charge en coulomb du vaisseau
    private RayonEnlevement rayonEnlevement;

    public Vaisseau(double posX, double posY, String pathImageAlien,double largeurNiveau, double hauteurNiveau) {
        super(posX, posY, pathImageAlien, largeurNiveau, hauteurNiveau, 100, 140);

        this.vX = 0;
        this.vY = 0;

        // L'alien commence avec 4 vies et pas de gravite
        this.nombreDeVie = 4;
        this.gravite = 0;

        this.pourcentageHauteurANePasDepasser = 60;

        // Creer le rayon
        this.rayonEnlevement = new RayonEnlevement(this);

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
            limiterVitesse();

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
     * Methode pour limiter la vitesse a 600px/s
     */
    private void limiterVitesse(){
        // Marge de vitesse pour arreter les oscillations
        double margeDeVitesse = 1.0;

        // Si plus que 600 ou moins que -600, alors remettre la vitesse a 600 avec la bonne signe
        if (Math.abs(this.vX) > limiteVitesse) vX = 600*Math.signum(this.vX);

        // Arreter les petits oscillations quand le vaisseau essaye d'arriver a un arret
        if (Math.abs(this.vX) < margeDeVitesse){
            vX = 0;
            aX = 0;
        }
        if (Math.abs(this.vY) < margeDeVitesse){
            vY = 0;
            aY = 0;
        }
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

}
