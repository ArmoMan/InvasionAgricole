package ca.qc.bdeb.sim.invasionagricole.objet.background;

import ca.qc.bdeb.sim.invasionagricole.Camera;
import javafx.scene.canvas.GraphicsContext;

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
