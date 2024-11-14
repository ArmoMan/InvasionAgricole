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
    private Camera camera = new Camera(InvasionAgricoleJavaFX.LARGEUR_FENETRE);
    Niveau niveau = new Niveau(InvasionAgricoleJavaFX.LARGEUR_FENETRE, InvasionAgricoleJavaFX.HAUTEUR_FENETRE, 1,1000, camera);
    ArrayList<ObjetPhysique> objetsAffichageMap = new ArrayList<>();
    MiniCarte miniCarte = new MiniCarte(10,50,100, objetsAffichageMap);
    ArrayList<Vache> vaches = niveau.getVaches();
    ArrayList<Humain> humains = niveau.getHumains();
    ArrayList<Projectile> projectiles = new ArrayList<>();

    // Ca cest un test !! À effacer plus tard !!!!!!!
    public void dessiner(GraphicsContext context){
        niveau.dessinerNiveau(context);
        camera.suivre(alien, niveau.getLargeurNiveau());
        alien.dessiner(context,camera);
        rayonEnlevement.dessinerRayon(context, camera);
        rayonEnlevement.dessinerBatterie(context,100,30,10,10,"batterie.png");
        miniCarte.dessiner(context, niveau.getLargeurNiveau());
        alien.dessinerCompteurVieEtVacheCapturer(context, 130,10, "mini-vache.png",30, 5);

    }
    public void update(double deltaTime){

        // Update l'alien et rayon
        alien.update(deltaTime);
        rayonEnlevement.update(deltaTime);

        // Verifier si rayon touche un objet physique (vache et humain)

        for (ObjetPhysique obj : niveau.getObjetPhysiquesNiveau()){
            if (rayonEnlevement.estRayonEnColisionAvec(obj)){
                System.out.println("En col");
                obj.setaY(-10000);
                obj.setvY(100);
            }
        }
        // Update la map
        objetsAffichageMap.clear();
        for (Vache vache : vaches) {
            vache.update(deltaTime);
            objetsAffichageMap.add(vache);
        }
        objetsAffichageMap.add(alien);
    }


    public void update2(double deltaTime){
       // Update humains et projectiles
        for (Humain humain : humains) {
            if (humain.estVisible(camera)) {
                lancerProjectile(humain.getPosCentreX(), humain.getPosCentreY());
            }
        }
    }

    private void lancerProjectile(double posX, double posY) {
        projectiles.add(determinerProjectile(1 + (int) (Math.random() * 3)));
    }

    private Projectile determinerProjectile(int choix) {
        switch (choix) {
            case 1 -> {
                return new Girouette();
            }
            case 2 -> {
                return new AimantFerCheval();
            }
            case 3 -> {
                return new PotDeFleurs();
            }
        }
        return null;
    }
}
