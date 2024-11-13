package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Vaisseau extends ObjetPhysique {

    private Color couleurVitre =  Color.rgb(255, 255, 0, 0.6);
    private Image vaisseauLumiereOn = new Image("base-vaisseau-on.png");
    private Image vaisseauLumiereOff = new Image("base-vaisseau-off.png");
    private double limiteVitesse = 600;
    private boolean estLumierAllumer = false;
    private double largeurNiveau, hauteurNiveau;
    private int nombreDeVacheCapturer = 0;

    public Vaisseau(int posX, int posY, String pathImageAlien, double largeurNiveau, double hauteurNiveau) {
        super(posX, posY, pathImageAlien);
        this.largeur = 100;
        this.hauteur = 140;
        this.vX = 0;
        this.vY = 0;
        this.largeurNiveau = largeurNiveau;
        this.hauteurNiveau = hauteurNiveau;

        // L'alien commence avec 4 vies
        this.nombreDeVie = 4;

    }

    @Override
    public void update(double deltaTime) {
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

        limiterVitesse();

        // Verifier que ca ne depasse pas les limites du niveau
        verifierQuePasHorsNiveau(largeurNiveau, hauteurNiveau);

        // Si il y a une des 4 touches appuyer alors mettre estLumierAllumer True sinon False
        estLumierAllumer = estCeQueButtonMouvementAppuyer();
    }


    /**
     * Methode pour verifier que le vaisseau n'est pas hors ecran et qu'il respecte la limite de hauteur
     * @param largeurMax du niveau
     * @param hauteurMax du niveau
     */
    private void verifierQuePasHorsNiveau(double largeurMax, double hauteurMax){

        // verifier la limite horizontale
        if (posX + largeur > largeurMax) {
            posX = largeurMax - largeur;
            vX = 0;
        }else if (posX < 0) {
            posX = 0;
            vX = 0;
        }

        // verifier que le vaisseau ne depasse pas 60% de l'hauteur du niveau
        double limiteDeHauteur = hauteurMax * 0.6;
        if (posY + hauteur > limiteDeHauteur) {
            posY = limiteDeHauteur - hauteur;
            vY = 0;
        }else if (posY < 0) {
            posY = 0;
            vY = 0;
        }

    }

    private boolean estCeQueButtonMouvementAppuyer(){
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


        if (estLumierAllumer){
            context.drawImage(vaisseauLumiereOn,  (xEcran-( vaisseauLumiereOn.getWidth()- this.largeur)/2), yEcran + (this.hauteur-vaisseauLumiereOn.getHeight()));
        }else {
            context.drawImage(vaisseauLumiereOff,  (xEcran-( vaisseauLumiereOff.getWidth()- this.largeur)/2), yEcran + (this.hauteur-vaisseauLumiereOff.getHeight()));
        }

        context.drawImage(this.imagePersonnage, (xEcran+( this.largeur - this.imagePersonnage.getWidth())/2),yEcran + this.hauteur-vaisseauLumiereOff.getHeight()-this.imagePersonnage.getHeight() );

        context.setFill(couleurVitre);
        context.fillOval(xEcran, yEcran,  this.largeur,  this.hauteur);



        dessinerDebug(context, camera);
    }

    public void dessinerCompteurVieEtVacheCapturer(GraphicsContext context,double posXdessin, double posYdessin, String pathImageVache, double hauteurImages, double espacementDesElements) {
        Image imageVache = new Image(pathImageVache);

        // Hauteur des images selon le largeur voulu
        double largeurImageVache = calculerNouveauLargeurImage(imageVache, hauteurImages);
        double largeurImageAlien = calculerNouveauLargeurImage(this.imagePersonnage, hauteurImages);

        // Image de vache
        context.drawImage(imageVache, posXdessin, posYdessin,largeurImageVache , hauteurImages);

        // Mettre le font des textes
        context.setFont(new Font(hauteurImages));

        // Mettre la couleur des textes
        context.setFill(Color.WHITE);

        // A chauque fois le texte est plus bas que voulu, donc le baisser moin
        double valeurASoustraire = hauteurImages*0.15;
        double  posYTextes = posYdessin+hauteurImages-valeurASoustraire;
        // Texte de vache
        double posXCompteurVache = posXdessin + largeurImageVache + espacementDesElements;
        context.fillText(String.valueOf(this.nombreDeVacheCapturer), posXCompteurVache, posYTextes);

        // Image d'Alien
        double posXAlien = posXCompteurVache + espacementDesElements + context.getFont().getSize();
        context.drawImage(this.imagePersonnage, posXAlien, posYdessin,largeurImageAlien ,hauteurImages );

        // Texte de nombre de vie Alien
        double posXCompteurVie = posXAlien + largeurImageAlien + espacementDesElements;
        context.fillText(String.valueOf(this.nombreDeVie), posXCompteurVie, posYTextes);

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

    public int getNombreDeVacheCapturer() {
        return nombreDeVacheCapturer;
    }

    public void setNombreDeVacheCapturer(int nombreDeVacheCapturer) {
        this.nombreDeVacheCapturer = nombreDeVacheCapturer;
    }
}
