package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Vaisseau extends ObjetPhysique {
    private Color couleurVitre =  Color.rgb(255, 255, 0, 0.6);
    private Image vaisseauLumiereOn = new Image("base-vaisseau-on.png");
    private Image vaisseauLumiereOff = new Image("base-vaisseau-off.png");
    private double limiteVitesse = 600;
    private boolean estLumierAllumer = false;

    public Vaisseau(int posX, int posY, String pathImageAlien) {
        super(posX, posY, pathImageAlien);
        this.largeur = 100;
        this.hauteur = 140;
        this.vX = 0;
        this.vY = 0;
    }

    public double getGauche(){
        return this.getPosX();
    }

    public double getDroite(){
        return this.getPosX() + this.largeur;
    }

    @Override
    protected void updatePhysique(double deltaTemps) {
        boolean left = Input.isKeyPressed(KeyCode.LEFT);
        boolean right = Input.isKeyPressed(KeyCode.RIGHT);
        boolean up = Input.isKeyPressed(KeyCode.UP);
        boolean down = Input.isKeyPressed(KeyCode.DOWN);

        if (left)
            aX = -2000;
        else if (right)
            aX = 2000;
        else
            if (aX < 0)
                aX = -500;
            else if (aX > 0 )
                aX= 500;
            else
                aX = 0;

        super.updatePhysique(deltaTemps);

        limiterVitesse();

        // Si il y a une des 4 touches appuyer alors mettre estLumierAllumer True sinon False
        estLumierAllumer = (left||right||up||down);

    }

    /**
     * Methode pour limiter la vitesse a 600px/s
     */
    private void limiterVitesse(){
        // Si plus que 600 ou moins que -600, alors remettre la vitesse a 600 avec la bonne signe
        if (Math.abs(this.vX) > limiteVitesse) vX = 600*Math.signum(this.vX);
    }

    @Override
    protected void dessiner(GraphicsContext context, Camera camera) {

    }
}
