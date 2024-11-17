package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

/**
 * Classe qui génère et contrôle les niveaux, contrôle la mini-carte, et génère l'alien
 */
public class ControleurJeu {

    private Vaisseau alien = new Vaisseau(0,0,"extraterrestre.png",InvasionAgricoleJavaFX.LARGEUR_FENETRE*4,InvasionAgricoleJavaFX.HAUTEUR_FENETRE);
    private RayonEnlevement rayonEnlevement = new RayonEnlevement(alien);
    private Camera camera = new Camera(InvasionAgricoleJavaFX.LARGEUR_FENETRE, InvasionAgricoleJavaFX.HAUTEUR_FENETRE);
    private double tempsEcouleEcranNoir = 0;
    Niveau niveau = new Niveau(InvasionAgricoleJavaFX.LARGEUR_FENETRE, InvasionAgricoleJavaFX.HAUTEUR_FENETRE, 1,1000, camera, alien);
    ArrayList<ObjetPhysique> objetsAffichageMap = new ArrayList<>();
    MiniCarte miniCarte = new MiniCarte(10,50,100, objetsAffichageMap);
    ArrayList<Vache> vaches = niveau.getVaches();
    ArrayList<Humain> humains = niveau.getHumains();

    // Ca cest un test !! À effacer plus tard !!!!!!!
    public void dessiner(GraphicsContext context){
        niveau.dessinerNiveau(context);


        camera.suivre(alien, niveau.getLargeurNiveau());
        alien.dessiner(context,camera);
        rayonEnlevement.dessinerRayon(context, camera);
        rayonEnlevement.dessinerBatterie(context,100,30,10,10,"batterie.png");
        miniCarte.dessiner(context, niveau.getLargeurNiveau());
        alien.dessinerCompteurVieEtVacheCapturer(context, 130,10, "mini-vache.png",30, 5);

        // Si niveau gagner, dessiner en noire l'ecran et afficher le texte
        if (niveau.isNiveauGagne()){
            niveau.dessinerEcranGagner(context, tempsEcouleEcranNoir/ 2.5);
        }
    }

    public void update(double deltaTime){

        // Si plus de vache et niveau non gagner, alors changer ca
        if (niveau.getVaches().isEmpty() && !niveau.isNiveauGagne()){
            niveau.setNiveauGagne(true);
            // remettre le temps de l'ecran noir a 0
            tempsEcouleEcranNoir = 0;
        }

        if (niveau.isNiveauGagne()){
            tempsEcouleEcranNoir += deltaTime;
            return;
        }

        // Update l'alien et rayon
        alien.update(deltaTime);
        rayonEnlevement.update(deltaTime);

        // A faire !!!!!!!!!!!! !!!!!!!!!!!!!!!!!!!!!!!!!   !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // Si vaisseau hors limites au complet du camera et que plus de vie, alors partie perdue
        if (alien.getNombreDeVie() <= 0){
            if (alien.estObjetHorsCamera(camera)){
                // Ici retourner ecran d'acceuil
            }
            return;
        };


        // Verifier si rayon touche un objet physique (vache et humain) et update le compteur de vache capturer
        for (ObjetPhysique personnage : niveau.getObjetPhysiques()){

            // Dessider si personnage doit "voler" vers le vaisseau ou pas
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

        // Update humains et projectiles
        for (Humain humain : humains) {
            humain.update(deltaTime);
        }

        // Liste de projectiles a effacer
        ArrayList<Projectile> projectilesEffacer = new ArrayList<>();
        // Faire le tour des projectiles et faire les verifications avec les projectiles
        for (Projectile projectile : niveau.getProjectiles()) {

            projectile.update(deltaTime);

            //Si hors limite, alors l'effacer
            if (projectile.estToucherLimites(niveau.getLargeurNiveau(), niveau.getPositionYSol(),false)){
                projectilesEffacer.add(projectile);
            }

            // Si projectile en collsion avec vaisseau, alors dminuer la vie du vaisseau et effacer projectile
            if (projectile.estEnCollisionAvec(alien)){
                projectilesEffacer.add(projectile);
                // Enlever des vie au vaisseau seulement si il a des vies
                if (alien.getNombreDeVie() > 0){
                    alien.setNombreDeVie(alien.getNombreDeVie() - 1);
                }
            }

        }
        niveau.getProjectiles().removeAll(projectilesEffacer);

        // deuxieme options pour effacer
//            Iterator<Projectile> iteratorProjectiles = niveau.getProjectiles().iterator();
//            while (iteratorProjectiles.hasNext()){
//                Projectile projectile = iteratorProjectiles.next();
//                projectile.update(deltaTime);
//                // Si projectile hors limites du niveau, alors l'effacer
//                if (projectile.estHorsLimites(niveau.getLargeurNiveau(), niveau.getPositionYSol(),false)){
//                    iteratorProjectiles.remove();
//                }
//            }

    }




}
