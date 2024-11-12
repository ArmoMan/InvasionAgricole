package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.canvas.GraphicsContext;

public class ControleurJeu {

    private Vaisseau alien = new Vaisseau(0,0,"icone.png",InvasionAgricoleJavaFX.LARGEUR_FENETRE*4,InvasionAgricoleJavaFX.HAUTEUR_FENETRE);

    private Camera camera = new Camera(InvasionAgricoleJavaFX.LARGEUR_FENETRE);
    Niveau n = new Niveau(InvasionAgricoleJavaFX.LARGEUR_FENETRE, InvasionAgricoleJavaFX.HAUTEUR_FENETRE, 1,1000, camera);

    // Ca cest un test !! À effacer plus tard !!!!!!!
    public void dessiner(GraphicsContext context){
        n.dessinerNiveau(context);
        camera.suivre(alien, n.getLargeurNiveau());
        alien.dessiner(context,camera);
    }
    public void update(double deltaTime){
        alien.update(deltaTime);
    }
}
