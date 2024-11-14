package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Map;

/**
 * Classe qui génère et contrôle les niveaux, contrôle la mini-carte, et génère l'alien
 */
public class ControleurJeu {

    private Vaisseau alien = new Vaisseau(0,0,"extraterrestre.png",InvasionAgricoleJavaFX.LARGEUR_FENETRE*4,InvasionAgricoleJavaFX.HAUTEUR_FENETRE);
    private RayonEnlevement rayonEnlevement = new RayonEnlevement(alien);
    private Camera camera = new Camera();
    Niveau n = new Niveau(InvasionAgricoleJavaFX.LARGEUR_FENETRE, InvasionAgricoleJavaFX.HAUTEUR_FENETRE, 1,1000, camera);
    ArrayList<ObjetPhysique> objets = new ArrayList<>();
    MiniCarte miniCarte = new MiniCarte(10,50,100,objets);
    ArrayList<Vache> vaches = n.vaches;

    // Ca cest un test !! À effacer plus tard !!!!!!!
    public void dessiner(GraphicsContext context){
        n.dessinerNiveau(context);
        camera.suivre(alien, n.getLargeurNiveau(), InvasionAgricoleJavaFX.LARGEUR_FENETRE);
        alien.dessiner(context,camera);
        rayonEnlevement.dessinerRayon(context, camera);
        rayonEnlevement.dessinerBatterie(context,100,30,10,10,"batterie.png");
        miniCarte.dessiner(context,n.getLargeurNiveau());
        alien.dessinerCompteurVieEtVacheCapturer(context, 130,10, "mini-vache.png",30, 5);
        for (Vache vache : vaches) {
            vache.dessiner(context, camera);
            vache.dessinerDebug(context, camera);
        }
    }
    public void update(double deltaTime){
        alien.update(deltaTime);
        rayonEnlevement.update(deltaTime);
        objets.clear();
        for (Vache vache : vaches) {
            vache.update(deltaTime);
            objets.add(vache);
        }        // pour map

        objets.add(alien);
    }
}
