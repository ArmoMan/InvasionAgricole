package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Classe qui génère et contrôle les niveaux, contrôle la mini-carte, et génère l'alien
 */
public class ControleurJeu {

    private Vaisseau vaisseau = new Vaisseau(0,0,"extraterrestre.png",InvasionAgricoleJavaFX.LARGEUR_FENETRE*4,InvasionAgricoleJavaFX.HAUTEUR_FENETRE);
    private RayonEnlevement rayonEnlevement = new RayonEnlevement(vaisseau);
    private Camera camera = new Camera(InvasionAgricoleJavaFX.LARGEUR_FENETRE, InvasionAgricoleJavaFX.HAUTEUR_FENETRE);
    private double tempsEcouleEcranNoir = 0;
    private Niveau niveau = new Niveau(InvasionAgricoleJavaFX.LARGEUR_FENETRE, InvasionAgricoleJavaFX.HAUTEUR_FENETRE, 1, camera, vaisseau);
    private ArrayList<ObjetPhysique> objetsAffichageMap = new ArrayList<>();
    private MiniCarte miniCarte = new MiniCarte(10,50,100, objetsAffichageMap);
    private ArrayList<Vache> vaches = niveau.getVaches();
    private ArrayList<Humain> humains = niveau.getHumains();

    // Ca cest un test !! À effacer plus tard !!!!!!!
    public void dessiner(GraphicsContext context){
        niveau.dessinerNiveau(context);


        camera.suivre(vaisseau, niveau.getLargeurNiveau());
        vaisseau.dessiner(context,camera);
        rayonEnlevement.dessinerRayon(context, camera);
        rayonEnlevement.dessinerBatterie(context,100,30,10,10,"batterie.png");
        miniCarte.dessiner(context, niveau.getLargeurNiveau());
        vaisseau.dessinerCompteurVieEtVacheCapturer(context, 130,10, "mini-vache.png",30, 5);

        // Si niveau gagner, dessiner en noire l'ecran et afficher le texte
        if (niveau.estNiveauGagne()){
            niveau.dessinerEcranGagner(context, tempsEcouleEcranNoir/ 2.5);
        }
    }

    public void update(double deltaTime){

        // Si plus de vache et niveau non gagner, alors changer ca
        if (niveau.getVaches().isEmpty() && !niveau.estNiveauGagne()){
            niveau.setNiveauGagne(true);
            // remettre le temps de l'ecran noir a 0
            tempsEcouleEcranNoir = 0;
        }

        if (niveau.estNiveauGagne()){
            tempsEcouleEcranNoir += deltaTime;
            return;
        }

        // Update l'alien et rayon
        vaisseau.update(deltaTime);
        rayonEnlevement.update(deltaTime);

        // A faire !!!!!!!!!!!! !!!!!!!!!!!!!!!!!!!!!!!!!   !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // Si vaisseau hors limites au complet du camera et que plus de vie, alors partie perdue
        if (vaisseau.getNombreDeVie() <= 0){
            if (vaisseau.estHorsCamera(camera)){
                // Ici retourner ecran d'acceuil
            }
            return;
        };


        // Verifier si rayon touche un objet physique (vache et humain) et update le compteur de vache capturer
        for (ObjetPhysique personnage : niveau.getObjetPhysiques()){

            // Dessider si personnage doit "voler" vers le vaisseau ou pas
            personnage.setEstMonter(rayonEnlevement.estRayonEnColisionAvec(personnage));

            // Si en collision et en vie alors augmenter compteur, enlever vie au vache et enlever la vache
            if (vaisseau.estEnCollisionAvec(personnage) && personnage.getNombreDeVie() > 0){
                personnage.setNombreDeVie(0);

                // Enlever la vache ou l'humain et augmenter compteur de capture
                if (personnage.getClass() == Humain.class){
                    humains.remove(personnage);
                    vaisseau.setNombreDeVie(vaisseau.getNombreDeVie() + 1);
                }else  if (personnage.getClass() == Vache.class){
                    vaches.remove(personnage);
                    vaisseau.setNbVachesCapturees(vaisseau.getNbVachesCapturees() + 1);

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
        objetsAffichageMap.add(vaisseau);

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
            if (projectile.estEnCollisionAvec(vaisseau)){
                projectilesEffacer.add(projectile);
                // Enlever des vie au vaisseau seulement si il a des vies
                if (vaisseau.getNombreDeVie() > 0){
                    vaisseau.setNombreDeVie(vaisseau.getNombreDeVie() - 1);
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
