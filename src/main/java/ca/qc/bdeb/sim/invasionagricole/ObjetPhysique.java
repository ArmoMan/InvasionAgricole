package ca.qc.bdeb.sim.invasionagricole;

public abstract class ObjetPhysique {

    private int posX, posY;
    private int vX, vY;
    private int aX, aY;

    public ObjetPhysique(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }





    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
