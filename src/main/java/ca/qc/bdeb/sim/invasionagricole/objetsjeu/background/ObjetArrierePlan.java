package ca.qc.bdeb.sim.invasionagricole.objetsjeu.background;

import ca.qc.bdeb.sim.invasionagricole.Camera;
import javafx.scene.canvas.GraphicsContext;

/**
 * Classe parent pour les objets statiques qui vont etre affichés en arriere plan. Contient seulement posX,posY,
 * xEcran, yEcran et une methode dessiner.
 */
public abstract class ObjetArrierePlan {
    protected int posX, posY;
    protected double xEcran, yEcran;

    public ObjetArrierePlan(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void dessiner(GraphicsContext context, Camera camera){
        this.xEcran = camera.calculerEcranX(posX);
        this.yEcran = camera.calculerEcranY(posY);
    }

}
