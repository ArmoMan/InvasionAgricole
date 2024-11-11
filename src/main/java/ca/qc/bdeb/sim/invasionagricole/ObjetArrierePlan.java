package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.canvas.GraphicsContext;

public abstract class ObjetArrierePlan {
    protected int posX, posY;
    protected double xEcran, yEcran;

    public ObjetArrierePlan(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    protected void dessiner(GraphicsContext context,Camera camera){
        this.xEcran = camera.calculerEcranX(posX);
        this.yEcran = camera.calculerEcranY(posY);
    }

}
