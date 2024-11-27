package ca.qc.bdeb.sim.invasionagricole;

import ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.ObjetPhysique;
import ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.vivant.Vaisseau;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class RayonEnlevement {
    // Les constants
    private final double VITESSE_EXTENSION_RAYON = 150;
    private final double VITESSSE_RECHARGE_BATTERIE = 0.2;
    private final double VITESSE_DECHARGE_BATTERIE = 0.25;

    private double largeurRayon;
    private double hauteurRayon;
    private boolean estRayonActiver = false;
    // Charge entre 0 et 1
    private double chargePresentBatterie;
    private Color couleurRayon = Color.rgb(255, 255, 0, 0.5);
    private Vaisseau vaisseau;
    private boolean estCeQueBatteriePeutEtreUtilisee = true;

    public RayonEnlevement(Vaisseau vaisseau) {
        this.largeurRayon = vaisseau.getLargeur();
        this.chargePresentBatterie = 1;
        this.hauteurRayon = 0;
        this.vaisseau = vaisseau;
    }

    public void update(double deltaTime) {
        boolean espaceAppuyer = Input.isKeyPressed(KeyCode.SPACE);

        // Si appuyer espace et que il y a de la charge et que utilisateur peut utiliser,
        // alors augmenter la hauteur du rayon et baisser le niveau de la batterie
        if (espaceAppuyer && chargePresentBatterie > 0 && estCeQueBatteriePeutEtreUtilisee) {
            hauteurRayon += VITESSE_EXTENSION_RAYON * deltaTime;
            chargePresentBatterie -= VITESSE_DECHARGE_BATTERIE * deltaTime;
            estRayonActiver = true;
        } else {
            // Si pas de charge ou pas de button appuyer, alors charger la batterie
            hauteurRayon = 0;

            // Si completement decharger, alors empecher utilisateur de l'utiliser
            if (chargePresentBatterie <= 0) {
                estCeQueBatteriePeutEtreUtilisee = false;
            } else if (chargePresentBatterie >= 1) {
                estCeQueBatteriePeutEtreUtilisee = true;
            }

            // Charger la batterie si necessaire
            if (chargePresentBatterie < 1) {
                chargePresentBatterie += VITESSSE_RECHARGE_BATTERIE * deltaTime;
                // Verifier que la charge ne depasse jamais 1
                if (chargePresentBatterie > 1) {
                    chargePresentBatterie = 1;
                }
            }
            estRayonActiver = false;
        }
    }

    /**
     * Methode pour verifier si la rayon est en collision avec un objet
     * @param elementPhysique
     * @return true si en collision et false si pas en collision
     */
    public boolean estRayonEnColisionAvec(ObjetPhysique elementPhysique) {
        // Si rayon est inactif, alors ignorer
        if (!estRayonActiver) {
            return false;
        }


        // Verifier si objet est entre les x du rayon
        boolean estCeQuePlusGaucheQuePartieDroiteRayon = vaisseau.getGauche()
                + largeurRayon > elementPhysique.getGauche();
        boolean estCeQuePlusDroiteQuePartieGaucheRayon = vaisseau.getGauche() < elementPhysique.getDroite();

        // Verifier si objet est entre les y du rayon
        // Verifier si partie le plus bas de l'objet est plus bas que le point le plus haut du rayon
        boolean estCeQuePlusBasQuePartieHautRayon = vaisseau.getPosY() < elementPhysique.getPosY()
                + elementPhysique.getHauteur();
        // Verifier si partie le plus haut de l'objet est plus haut que le point le plus bas du rayon
        boolean estCeQuePlusHautQuePartieBasRayon = elementPhysique.getPosY() < vaisseau.getPosY() +vaisseau.getHauteur()+  hauteurRayon;

        boolean objetPlusBasQueBasDuVaisseau = vaisseau.getPosY() + vaisseau.getHauteur() < elementPhysique.getPosY();

        return  estCeQuePlusHautQuePartieBasRayon && estCeQuePlusBasQuePartieHautRayon
                && estCeQuePlusDroiteQuePartieGaucheRayon && estCeQuePlusGaucheQuePartieDroiteRayon && objetPlusBasQueBasDuVaisseau;

    }

    public void dessinerRayon(GraphicsContext context, Camera camera) {
        if (estRayonActiver && chargePresentBatterie > 0) {
            double xEcran = camera.calculerEcranX(vaisseau.getPosX());
            double yEcran = camera.calculerEcranY(vaisseau.getPosY() + vaisseau.getHauteur());
            context.setFill(couleurRayon);
            context.fillRect(xEcran, yEcran, largeurRayon, hauteurRayon);
        }
    }

    /**
     * Methode pour dessiner la batterie du Rayon
     * @param context est le GraphicsContext
     * @param largeurBatterie est la largeur de la batterie à dessiner
     * @param hauteurBatterie est la hauteur de la batterie à dessiner
     * @param posX position x de la batterie
     * @param posY position y de la batterie
     * @param pathImageBatterie image de la batterie qui doit être affiché dans la barre de recharge
     */
    public void dessinerBatterie(GraphicsContext context, double largeurBatterie,
                                 double hauteurBatterie, double posX, double posY, String pathImageBatterie) {
        Color couleurBatterie;

        if (estCeQueBatteriePeutEtreUtilisee) {
            if (chargePresentBatterie <= 0.2) {
                couleurBatterie = Color.ORANGE;
            } else {
                couleurBatterie = Color.YELLOW;
            }
        } else {
            couleurBatterie = Color.RED;
        }


        // La charge interrieur
        context.setFill(couleurBatterie);
        context.fillRect(posX, posY, largeurBatterie * chargePresentBatterie, hauteurBatterie);

        // Le contour de la batterie
        context.setStroke(couleurBatterie);
        context.setLineWidth(2);
        context.strokeRect(posX, posY, largeurBatterie, hauteurBatterie);

        // Image de la betterie
        Image imageBatterie = new Image(pathImageBatterie);
        double largeurImage = hauteurBatterie * imageBatterie.getWidth() / imageBatterie.getHeight();
        context.drawImage(imageBatterie, posX + 5, posY, largeurImage, hauteurBatterie);

    }

    /**
     * Methode pour charger au complet la batterie
     */
    public void rechargerBatterie(){
        this.chargePresentBatterie = 1;
        estCeQueBatteriePeutEtreUtilisee = true;
    }
}
