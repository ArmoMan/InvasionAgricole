package ca.qc.bdeb.sim.invasionagricole;

import ca.qc.bdeb.sim.invasionagricole.objetsjeu.background.Etoile;
import ca.qc.bdeb.sim.invasionagricole.objetsjeu.background.ObjetArrierePlan;
import ca.qc.bdeb.sim.invasionagricole.objetsjeu.background.ObjetImage;
import ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.ObjetPhysique;
import ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.projectiles.Projectile;
import ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.vivant.Humain;
import ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.vivant.Vache;
import ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.vivant.Vaisseau;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

/**
 * La classe niveau permet de genrere les decors, les vaches, les humains, contient une methode qui peut les dessiner et
 * une qui peut les updates.
 */
public class Niveau {

    // Information sur le niveau
    private int numeroNiveau;
    private boolean estTermine = false;
    private boolean estPerdue = false;

    // Les dimensions
    private double largeurNiveau, hauteurNiveau;
    private final int HAUTEUR_SOL_VERT = 60;
    // Largeur de l'ecran / fenetre
    private double largeurFenetre,hauteurFenetre;
    private  double positionYSol;

    // Pour l'arriere plan
    private ArrayList<ObjetArrierePlan> objetsArrierePlan = new ArrayList<ObjetArrierePlan>();

    // Les objets physiques du niveau
    private Vaisseau vaisseau;
    private ArrayList<Vache> vaches = new ArrayList<>();
    private ArrayList<Humain> humains = new ArrayList<>();
    private ArrayList<Projectile> projectiles = new ArrayList<>();

    // Camera
    private Camera camera;



    public Niveau(double largeurFenetre,double hauteurFenetre, int numeroNiveau, Camera camera, Vaisseau vaisseau) {
        // Initialiser informations importants
        this.numeroNiveau = numeroNiveau;

        // Initialiser les dimensions: largeur du niveau est 4 fois celle de la fenetre
        this.hauteurNiveau = hauteurFenetre;
        this.largeurNiveau = largeurFenetre*4;
        this.largeurFenetre = largeurFenetre;
        this.hauteurFenetre = hauteurFenetre;
        //this.positionYSol = hauteurNiveau - HAUTEUR_SOL_VERT;

        this.camera = camera;
        this.vaisseau = vaisseau;

        // Generer le contenu du niveau
        genererEtoiles(100);
        genererGrangesEtTracteurs();
        genererObjetsPhysique();

    }


    /*  *****************************************************************************
     ***************   Partie pour dessiner les objets etc            ***************
     ********************************************************************************
     */

    public void dessinerNiveau(GraphicsContext context){
        afficherArrierePlan(context);

        // Dessiner objets
        for (Vache vache : vaches) {
            vache.dessiner(context, camera);
            if (Input.modeDebogageActif) vache.dessinerDebug(context, camera);
        }

        for (Humain humain : humains) {
            humain.dessiner(context, camera);
            if (Input.modeDebogageActif) humain.dessinerDebug(context, camera);
        }

        for (Projectile projectile : projectiles) {
            projectile.dessiner(context, camera);
            if (Input.modeDebogageActif) projectile.dessinerDebug(context, camera);
        }

        // Dessiner le vaisseau et ses elements
        vaisseau.dessiner(context, camera);
        vaisseau.dessinerCompteurVieEtVacheCapturer(context, 130,10, "mini-vache.png","icone.png",30, 5);
        vaisseau.getRayonEnlevement().dessinerBatterie(context,100,30,10,10,"batterie.png");
        vaisseau.getRayonEnlevement().dessinerRayon(context, camera);
        if (Input.modeDebogageActif){
            vaisseau.dessinerDebug(context, camera);
        }

        // Dessiner les lignes verticales sur l'ecran
        if (Input.modeDebogageActif){
            camera.dessinerDebug(context);
        }
    }


    private void afficherArrierePlan(GraphicsContext context){
        // dessiner le sol vert et le ciel en noir
        dessinerEnvironnementPourBackground(context);

        // Dessiner les objets de l'arriere plan
        for (ObjetArrierePlan obj : objetsArrierePlan){
            obj.dessiner(context,this.camera);
        }

    }

    private void dessinerEnvironnementPourBackground(GraphicsContext context){
        // le ciel
        context.setFill(Color.web("#1a1a1a"));
        context.fillRect(0,0,largeurNiveau,hauteurNiveau);

        // le sol
        context.setFill(Color.web("#225500"));
        context.fillRect(0, hauteurNiveau - HAUTEUR_SOL_VERT, largeurNiveau, HAUTEUR_SOL_VERT);
    }


    /*  *****************************************************************************
     ***************   Partie pour updates/interagir avec les objets  ***************
     ********************************************************************************
     */

    public void update(double deltaTime){

        // Mettre en application les touches du debogage
        gererTouchesDebogages();
        // Update le vaisseau et son rayon
        vaisseau.update(deltaTime);

        // Faire suivre la camera
        camera.suivre(vaisseau, largeurNiveau);

        // Verfifier si le niveau est perdu
        verifierNiveauPerdue();

        // Update les objets physiques du niveau (position, vitesse etc)
        for (ObjetPhysique objetPhysique : this.getObjetPhysiques()) {
            objetPhysique.update(deltaTime);

            // Dessider si objet doit "voler" vers le vaisseau ou pas
            objetPhysique.setEstMonter(vaisseau.getRayonEnlevement().estRayonEnColisionAvec(objetPhysique));
        }

        for (Projectile projectile : projectiles) {
            projectile.update(deltaTime);
        }

        // Verifier si apres les updates il y a eu des collision entre les objets et le gerer
        verifierCollisions();

        // Verifier si le niveau est fini
        if (vaches.size() <= 0) estTermine = true;
    }

    /**
     * Gerer les touches de debogages pour le update
     */
    private void gererTouchesDebogages(){
        // Si W appuye, alors augmenter la vie de 1
        if (Input.ajouterVie ){
            vaisseau.ajouterUneVie();
        }

        // Si E appuye, recharger la batterie au complet
        if (Input.isKeyPressed(KeyCode.E)){
            vaisseau.getRayonEnlevement().rechargerBatterie();
        }

        // Si touche I alors niveau gagne
        if (Input.isKeyPressed(KeyCode.I)){
            this.estTermine = true;
        }

        // Si touche F, alors niveau perdu et vaisseau 0 vie (comme ca elle tombe)
        if (Input.isKeyPressed(KeyCode.F)){
            this.estPerdue = true;
            vaisseau.setNombreDeVie(0);
        }
    }

    private void verifierNiveauPerdue(){
        // Si vaisseau hors limites au complet du camera et que plus de vie, alors partie perdue
        if ((vaisseau.getNombreDeVie() <= 0) && vaisseau.estHorsCamera(camera)){
            this.estPerdue = true;
        };
    }

    private void verifierCollisions(){

        // Verifier les collsions entre vaisseau et projectiles: enlever vie et projectile + si hors ecran alors enlever
        projectiles.removeIf(projectile -> {
            if (projectile.estEnCollisionAvec(vaisseau)){
                // Verifier que pas en mode invincible
                if (!Input.modeInvincible){
                    vaisseau.enleverUneVie();
                }
                return true;
            }

            // Si hors camera alors effacer
            if (projectile.estHorsCamera(camera)){
                return true;
            }

            return false;
        });

        // Si le vaisseau est mort, alors il ne doit pas  affecter les vaches ou humains (en tombant sur eux)
        if (vaisseau.getNombreDeVie() <= 0) return;

        // Verifier collision entre vaisseau et vache, si il y a collision => augmenter vaches capturer et effacer vache
        vaches.removeIf(vache ->{

            if (vaisseau.estEnCollisionAvec(vache)){
                // Augmenter compteur de vache du vaisseau et effacer vache (true)
                vaisseau.augmenterCompteurVache(1);
                return true;
            }
            return false;
        });

        //Verifier collision entre vaisseau et humain, si il y a collision =>  effacer humain et ajouter vie au vaisseau
        humains.removeIf(humain ->{
            if (vaisseau.estEnCollisionAvec(humain)){
                // Ca ajoute une vie seulement si la vie est plus bas que sa vie maximale
                vaisseau.ajouterUneVie();
                return true;
            }
            return false;
        });

    }


    /*  *****************************************************************
        ***************   Partie pour generer les objets  ***************
        *****************************************************************
     */

    private void genererEtoiles(int nbEtoilesVoulues){
        for (int i = 0; i < nbEtoilesVoulues; i++) {
            int positionX = (int) (Math.random() * largeurNiveau);

            //Position y est choisie au hasard de façon à ne pas dépasser 50% de la hauteur de la fenêtre.
            int positionY = (int) (Math.random() * (hauteurNiveau/2));

            Etoile etoile = new Etoile(positionX,positionY);

            objetsArrierePlan.add(etoile);
        }
    }

    /*Ajouter autant de tracteurs/granges qu’on peut avec distances tirées au
     hasard, jusqu’à ce qu’on couvre toute la largeur du niveau.*/
    private void genererGrangesEtTracteurs() {
        boolean estCeTourTracteur = false;
        int posX = 0;
        Random rand = new Random();

        while (posX < largeurNiveau) {
            int prochaineDistanceAjouter = rand.nextInt(500,800);
            if (estCeTourTracteur){
                objetsArrierePlan.add(genererObjetImage(posX, "tracteur.png", 89, 55));
                estCeTourTracteur = false;
            }else{
                objetsArrierePlan.add(genererObjetImage(posX, "grange.png", 113, 147));
                estCeTourTracteur = true;
            }
            posX += prochaineDistanceAjouter;
        }
    }

    private ObjetImage genererObjetImage(int posX, String imagePath, int largeur, int hauteur){
        int posY = (int) (hauteurNiveau-hauteur-HAUTEUR_SOL_VERT);
        return new ObjetImage(posX, posY,imagePath, largeur, hauteur);
    }

    private void genererObjetsPhysique(){
        // Les vaches
        for (int i = 0; i < calculerNombreDeVache(); i++) {
            Vache vache = new Vache(largeurNiveau, hauteurNiveau);
            vaches.add(vache);
        }

        // Les humains
        for (int i = 0; i < calculerNombreDeHumains() ; i++) {
            Humain humain = new Humain(largeurNiveau, hauteurNiveau, determinerImageHumain(), camera, projectiles, vaisseau);
            humains.add(humain);
        }
    }

    /*  *****************************************************************************
     ***************   Partie pour calculs et autre                   ***************
     ********************************************************************************
     */

    private String determinerImageHumain() {
        if (Math.random() < 0.5) {
            return "fermier.png";
        } else {
            return "fermiere.png";
        }
    }

    private int calculerNombreDeVache(){
        return 5+2* numeroNiveau;
    }

    private int calculerNombreDeHumains(){
        return 3 + 3*(numeroNiveau - 1);
    }

    public boolean estTermine() {
        return estTermine;
    }

    /**
     * Methode pour former un arraylist des vaches et humains
     * @return un arraylist des humains et vaches du niveay
     */
    public ArrayList<ObjetPhysique> getObjetPhysiques() {
        ArrayList<ObjetPhysique> objetPhysiquesNiveau = new ArrayList<>();
        objetPhysiquesNiveau.addAll(vaches);
        objetPhysiquesNiveau.addAll(humains);
        return objetPhysiquesNiveau;
    }

    public ArrayList<Vache> getVaches() {
        return vaches;
    }

    public double getLargeurNiveau() {
        return largeurNiveau;
    }

    public int getNumeroNiveau() {
        return numeroNiveau;
    }

    public boolean estPerdue() {
        return estPerdue;
    }
}
