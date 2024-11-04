package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.canvas.GraphicsContext;

public abstract class ObjetArrierePlan {
    protected int posX, posY;

    public ObjetArrierePlan(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    protected abstract void dessiner(GraphicsContext context);

}
