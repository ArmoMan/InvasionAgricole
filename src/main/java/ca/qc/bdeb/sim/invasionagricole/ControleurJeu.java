package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

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

        // Verifier si rayon touche un objet physique (vache et humain) et update le compteur de vache capturer
        for (ObjetPhysique personnage : niveau.getObjetPhysiques()){

            // Dessider si personnage doit "voler" vers le vasiseau ou pas
            personnage.setEstMonter(rayonEnlevement.estRayonEnColisionAvec(personnage));

            // Si en collision et en vie alors augmenter compteur, enlever vie au vache et enlever la vache
            if (alien.estEnCollisionAvec(personnage) && personnage.getNombreDeVie() > 0){
                personnage.setNombreDeVie(0);

                // Enlever la vache ou l'humain et augmenter compteur de capture
                if (personnage.getClass() == Humain.class){
                    humains.remove(personnage);
                }else  if (personnage.getClass() == Vache.class){
                    vaches.remove(personnage);
                    alien.setNombreDeVacheCapturer(alien.getNombreDeVacheCapturer() + 1);
                }
            }
        }

        // Update la map
        objetsAffichageMap.clear();


        // Update vaches et ajouter au map
        for (Vache vache : vaches) {
            vache.update(deltaTime);
            objetsAffichageMap.add(vache);
        }

        // Ajouter vaisseau au map
        objetsAffichageMap.add(alien);

        for (Humain humain : humains) {
            humain.update(deltaTime);
        }

        // Update humains et projectiles
        for (Humain humain : humains) {
            if (humain.estVisible(camera)) {
                //lancerProjectile(humain.getPosCentreX(), humain.getPosCentreY()); attention, cette ligne va manger toute ta ram
            }
        }

        for (Projectile projectile : projectiles) {
            System.out.println(projectile.getPosX());
            projectile.update(deltaTime);
        }

    }

    private void lancerProjectile(double posX, double posY) {
        projectiles.add(creerProjectile((1 + (int) (Math.random() * 3)), posX, posY));
    }

    private Projectile creerProjectile(int choix, double posX, double posY) {
        switch (choix) {
            case 1 -> {
                return new Girouette(posX,posY,"girouette.png", niveau.getLargeurNiveau(), niveau.getHauteurNiveau());
            }
            case 2 -> {
                return new AimantFerCheval(posX,posY,"aimant.png", niveau.getLargeurNiveau(), niveau.getHauteurNiveau());
            }
            case 3 -> {
                return new PotDeFleurs(posX,posY,"pot-de-fleurs.png", niveau.getLargeurNiveau(), niveau.getHauteurNiveau());
            }
        }
        return null;
    }
}
