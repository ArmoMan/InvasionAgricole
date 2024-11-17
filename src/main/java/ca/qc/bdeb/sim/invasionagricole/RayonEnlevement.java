package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class RayonEnlevement {
    private double vitessseExtensionRayon = 150;
    private double vitesseDeRechargeBatterie = 0.2;
    private double vitesseDeDechargeBatterie = 0.25;
    private double largeurRayon;
    private double hauteurRayon;
    private boolean estRayonActiver = false;
    // Charge entre 0 et 1
    private double chargePresentBatterie;
    private Color couleurRayon = Color.rgb(255, 255, 0, 0.5);
    private Vaisseau vaisseau;
    private boolean estCeQueBatteriePeutEtreUtiliser = true;

    public RayonEnlevement(Vaisseau vaisseau) {
        this.largeurRayon = vaisseau.getLargeurImage();
        this.chargePresentBatterie = 1;
        this.hauteurRayon = 0;
        this.vaisseau = vaisseau;
    }

    public void update(double deltaTime) {
        boolean espaceAppuyer = Input.isKeyPressed(KeyCode.SPACE);

        // Si appuyer espace et que il y a de la charge et que utilisateur peut utiliser,
        // alors augmenter la hauteur du rayon et baisser le niveau de la batterie
        if (espaceAppuyer && chargePresentBatterie > 0 && estCeQueBatteriePeutEtreUtiliser) {
            hauteurRayon += vitessseExtensionRayon * deltaTime;
            chargePresentBatterie -= vitesseDeDechargeBatterie * deltaTime;
            estRayonActiver = true;
        } else {
            // Si pas de charge ou pas de button appuyer, alors charger la batterie
            hauteurRayon = 0;

            // Si completement decharger, alors empecher utilisateur de l'utiliser
            if (chargePresentBatterie <= 0) {
                estCeQueBatteriePeutEtreUtiliser = false;
            } else if (chargePresentBatterie >= 1) {
                estCeQueBatteriePeutEtreUtiliser = true;
            }

            // Charger la batterie si necessaire
            if (chargePresentBatterie < 1) {
                chargePresentBatterie += vitesseDeRechargeBatterie * deltaTime;
                // Verifier que la charge ne depasse jamais 1
                if (chargePresentBatterie > 1) {
                    chargePresentBatterie = 1;
                }
            }
            estRayonActiver = false;
        }
    }

    public boolean estRayonEnColisionAvec(ObjetPhysique objetPhysique) {
        // Si rayon est inactif, alors ignorer
        if (!estRayonActiver) {
            return false;
        }


        // Verifier si objet est entre les x du rayon
        boolean estCeQuePlusGaucheQuePartieDroiteRayon = vaisseau.getGauche()
                + largeurRayon > objetPhysique.getGauche();
        boolean estCeQuePlusDroiteQuePartieGaucheRayon = vaisseau.getGauche() < objetPhysique.getDroite();

        // Verifier si objet est entre les y du rayon
        // Verifier si partie le plus bas de l'objet est plus bas que le point le plus haut du rayon
        boolean estCeQuePlusBasQuePartieHautRayon = vaisseau.getPosY() < objetPhysique.getPosY()
                + objetPhysique.getHauteurImage();
        // Verifier si partie le plus haut de l'objet est plus haut que le point le plus bas du rayon
        boolean estCeQuePlusHautQuePartieBasRayon = objetPhysique.getPosY() < vaisseau.getPosY() +vaisseau.getHauteurImage()+  hauteurRayon;

        boolean objetPlusBasQueBasDuVaisseau = vaisseau.getPosY() + vaisseau.getHauteurImage() < objetPhysique.getPosY();

        return  estCeQuePlusHautQuePartieBasRayon && estCeQuePlusBasQuePartieHautRayon
                && estCeQuePlusDroiteQuePartieGaucheRayon && estCeQuePlusGaucheQuePartieDroiteRayon && objetPlusBasQueBasDuVaisseau;

    }

    public void dessinerRayon(GraphicsContext context, Camera camera) {
        if (estRayonActiver && chargePresentBatterie > 0) {
            double xEcran = camera.calculerEcranX(vaisseau.getPosX());
            double yEcran = camera.calculerEcranY(vaisseau.getPosY() + vaisseau.getHauteurImage());
            context.setFill(couleurRayon);
            context.fillRect(xEcran, yEcran, largeurRayon, hauteurRayon);
        }
    }

    public void dessinerBatterie(GraphicsContext context, double largeurBatterie,
                                 double hauteurBatterie, double posX, double posY, String pathImageBatterie) {
        Color couleurBatterie;

        if (estCeQueBatteriePeutEtreUtiliser) {
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


}
