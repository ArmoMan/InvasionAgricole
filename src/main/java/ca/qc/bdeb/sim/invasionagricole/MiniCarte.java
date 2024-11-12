package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;

public class MiniCarte {
    private double posX, posY;
    private ArrayList<Double> positionsObjetsAfficher = new ArrayList<>();
    private double largeurMap;
    private ArrayList<ObjetPhysique> objetPhysiques;
    private double diametreDesObjetsDessiner = 6;

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
            double posXObj = obj.getPosX()/largeurNiveau * this.largeurMap;
            context.fillOval(posX + posXObj, posY-diametreDesObjetsDessiner/2, diametreDesObjetsDessiner, diametreDesObjetsDessiner);
        }
    }
}
