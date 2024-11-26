package ca.qc.bdeb.sim.invasionagricole;

import ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.ObjetPhysique;
import ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.vivant.Vaisseau;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Classe pour dessiner les objetPhysique en appelant dessiner().
 * Le constructeur prend les objetPhysique à afficher
 */
public class MiniCarte {
    private double posX, posY;
    private ArrayList<Double> positionsObjetsAfficher = new ArrayList<>();
    private double largeurMap;
    private ArrayList<ObjetPhysique> objetPhysiques;
    private double diametreDesObjetsDessiner = 6;

    /**
     * Constructeur du Minicarte
     * @param posX position x sur l'ecran
     * @param posY position y sur l'ecran
     * @param largeurMap largeur de la carte voulu (taille)
     * @param objetPhysiques les objets à afficher
     */
    public MiniCarte(double posX, double posY, double largeurMap, ArrayList<ObjetPhysique> objetPhysiques) {
        this.posX = posX;
        this.posY = posY;
        this.largeurMap = largeurMap;
        this.objetPhysiques = objetPhysiques;
    }


    public void dessiner(GraphicsContext context, double largeurNiveau ) {
        // Dessiner la ligne de la map
        context.setStroke(Color.WHITE);
        context.setLineWidth(2);
        context.strokeLine(posX, posY, posX+this.largeurMap, posY);

        // Ajouter les positions sur la ligne
        for (ObjetPhysique obj : objetPhysiques) {
            if (obj.getClass() == Vaisseau.class){
                context.setFill(Color.LIGHTGREEN);
            }else{
                context.setFill(Color.WHITE);
            }
            // Adapter la position de l'objet pour la carte
            double posXObj = obj.getPosX()/largeurNiveau * this.largeurMap;
            context.fillOval(posX + posXObj, posY-diametreDesObjetsDessiner/2, diametreDesObjetsDessiner, diametreDesObjetsDessiner);
        }
    }
}
