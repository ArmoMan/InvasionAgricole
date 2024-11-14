package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

/**
 * La classe niveau permet de genrere les decors, les vaches, les humains et contient une methode qui peut les dessiner
 */
public class Niveau {
    private double largeurNiveau, hauteurNiveau;
    private int numeroDuNiveau;
    private double graviteDuNiveau;
    private final int HAUTEUR_SOL_VERT = 20;
    // Largeur de l'ecran / fenetre
    private double largeurFenetre;
    private ArrayList<ObjetArrierePlan> objetsArrierePlan = new ArrayList<ObjetArrierePlan>();
    private Camera camera;
    private  double positionYSol; // code se repete
    private ArrayList<ObjetPhysique> objetPhysiquesNiveau = new ArrayList<>();

    private ArrayList<Vache> vaches = new ArrayList<>();
    private ArrayList<Humain> humains = new ArrayList<>();

    public Niveau(double largeurFenetre,double hauteurFenetre, int numeroDuNiveau, double graviteDuNiveau, Camera camera) {
        this.numeroDuNiveau = numeroDuNiveau;
        this.graviteDuNiveau = graviteDuNiveau;
        this.hauteurNiveau = hauteurFenetre;
        // taille du niveau 4 fois la taille de la fenetre
        this.largeurNiveau = largeurFenetre*4;
        this.largeurFenetre = largeurFenetre;
        this.camera = camera;
        this.positionYSol = hauteurNiveau - HAUTEUR_SOL_VERT;
        // Generer le contenu du niveau
        genererEtoiles(100);
        genererGrangesEtTracteurs();
        genererVaches();
        genererHumains();
        creerTableauObjetsPhysiquesNiveau();
    }

    public ArrayList<ObjetPhysique> getObjetPhysiquesNiveau() {
        return objetPhysiquesNiveau;
    }

    public ArrayList<Vache> getVaches() {
        return vaches;
    }

    public ArrayList<Humain> getHumains() {
        return humains;
    }

    public double getLargeurNiveau() {
        return largeurNiveau;
    }

    public double getHauteurNiveau() {
        return hauteurNiveau;
    }

    public int getNumeroDuNiveau() {
        return numeroDuNiveau;
    }

    public void creerTableauObjetsPhysiquesNiveau(){
        objetPhysiquesNiveau.addAll(vaches);
        objetPhysiquesNiveau.addAll(humains);
    }

    public void dessinerNiveau(GraphicsContext context){
        afficherArrierePlan(context);
        for (Vache vache : vaches) {
            vache.dessiner(context, camera);
            vache.dessinerDebug(context, camera);
        }

        for (Humain humain : humains) {
            humain.dessiner(context, camera);
            humain.dessinerDebug(context, camera);
        }
    }

    private void afficherArrierePlan(GraphicsContext context){
        // dessiner le sol vert et le ciel en noir
        dessinerEnvironnementPourBackground(context);

        // Dessiner les objets
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
        context.fillRect(0,hauteurNiveau-HAUTEUR_SOL_VERT,largeurNiveau,HAUTEUR_SOL_VERT);
    }

    private void genererEtoiles(int nombreDeEtoileVoulu){
        for (int i = 0; i <nombreDeEtoileVoulu; i++) {
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
        int positionX = 0;
        Random rand = new Random();

        while (positionX < largeurNiveau) {
            int prochaineDistanceAjouter = rand.nextInt(500,800);
            if (estCeTourTracteur){
                int hauteurObjet = 55;
                int positionY = (int) (hauteurNiveau-hauteurObjet-HAUTEUR_SOL_VERT);
                ObjetImage tracteur = new ObjetImage(positionX, positionY,"tracteur.png", 89,hauteurObjet);
                objetsArrierePlan.add(tracteur);
                estCeTourTracteur = false;
            }else{
                int hauteurObjet = 147;
                int positionY = (int) (hauteurNiveau-hauteurObjet-HAUTEUR_SOL_VERT);
                ObjetImage grange = new ObjetImage(positionX,positionY,"grange.png", 113,hauteurObjet);
                objetsArrierePlan.add(grange);
                estCeTourTracteur = true;
            }
            positionX += prochaineDistanceAjouter;
        }
    }

    private void genererVaches() {
        vaches.clear();
        for (int i = 0; i < calculerNombreDeVache(); i++) {
            Vache vache = new Vache(positionYSol, largeurNiveau, positionYSol);
            vaches.add(vache);
        }
    }

    private void genererHumains() {
        humains.clear();
        for (int i = 0; i < calculerNombreDeHumains() ; i++) {
            Humain humain = new Humain(positionYSol, largeurNiveau, positionYSol, determinerImageHumain());
            humains.add(humain);
        }
    }

    private String determinerImageHumain() {
        if (Math.random() < 0.5) {
            return "fermier.png";
        } else {
            return "fermiere.png";
        }
    }

    private int calculerNombreDeVache(){
        return 5+2*numeroDuNiveau;
    }

    private int calculerNombreDeHumains(){
        return 3 + 3*(numeroDuNiveau - 1);
    }
}
