package ca.qc.bdeb.sim.invasionagricole;

import ca.qc.bdeb.sim.invasionagricole.objet.physique.ObjetPhysique;
import ca.qc.bdeb.sim.invasionagricole.objet.physique.vivant.Vache;
import ca.qc.bdeb.sim.invasionagricole.objet.physique.vivant.Vaisseau;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

/**
 * Classe qui génère et contrôle les niveaux, contrôle la mini-carte, et génère l'alien
 */
public class ControleurJeu {

    // Information du niveau
    private int numeroNiveauActuel = 0;
    private Niveau niveauActuel;
    private final double largeurNiveau, hauteurNiveau;

    // Objets etc
    private final Camera camera;
    private ArrayList<ObjetPhysique> objetsAffichageMap = new ArrayList<>();
    private MiniCarte miniCarte;
    private final Vaisseau vaisseau;

    // Partie animation ecran noir
    private double tempsAnimation = 0;
    private double opaciteCouleurTransition = 0;

    // Partie de jeu
    private boolean estJeuFini = false;

    public ControleurJeu() {
        this.largeurNiveau = 4 * InvasionAgricoleJavaFX.LARGEUR_FENETRE;
        this.hauteurNiveau = InvasionAgricoleJavaFX.HAUTEUR_FENETRE;

        this.miniCarte = new MiniCarte(10,50,100, objetsAffichageMap);
        // Commencer vaissau positionné à 100px du haut de l’écran et centré au milieu de l’écran horizontalement.
        this.vaisseau = new Vaisseau(InvasionAgricoleJavaFX.LARGEUR_FENETRE,100,"extraterrestre.png",InvasionAgricoleJavaFX.LARGEUR_FENETRE*4,InvasionAgricoleJavaFX.HAUTEUR_FENETRE);
        this.camera = new Camera(InvasionAgricoleJavaFX.LARGEUR_FENETRE, InvasionAgricoleJavaFX.HAUTEUR_FENETRE);

        chargerNouveauNiveau();
    }

    private void chargerNouveauNiveau(){
        // Creer niveau
        numeroNiveauActuel++;
        niveauActuel = new Niveau(InvasionAgricoleJavaFX.LARGEUR_FENETRE, InvasionAgricoleJavaFX.HAUTEUR_FENETRE, numeroNiveauActuel, camera, vaisseau);

        // Repositionner le vaisseau au centre et met vY et vX a 0
        vaisseau.repositionnerAuPosInitiale();
        // Repositionner le camera au debut
        camera.repositionnerPositionZero();
    }

    public void update(double deltaTime){
        // Si niveau perdu, alors jeu fini et que hors ecran
        if (niveauActuel.estPerdue() && vaisseau.estHorsCamera(camera)){
            this.estJeuFini = true;
        }

        // Si niveau terminer (gagner), commencer l'animation (le timer et opacite)
        if (niveauActuel.estTermine()){
            tempsAnimation += deltaTime;
            opaciteCouleurTransition+=0.007;

            // Si animation deja 2.5 secondes et que tout ecran fonce, alors changer de niveau
            if (tempsAnimation >= 2.5 && opaciteCouleurTransition >= 1){
                opaciteCouleurTransition = 0;
                tempsAnimation = 0;
                chargerNouveauNiveau();
            }

            // Return pour empecher les objets de bouger
            return;
        }

        // Update le niveau
        niveauActuel.update(deltaTime);

        // Update la map
        objetsAffichageMap.clear();

        // Ajouter vaches au map
        for (Vache vache : niveauActuel.getVaches()) {
            objetsAffichageMap.add(vache);
        }

        // Ajouter vaisseau au map
        objetsAffichageMap.add(vaisseau);

    }

    public void dessiner(GraphicsContext context){
        // Dessiner niveau
        niveauActuel.dessinerNiveau(context);

        // dessiner la mini carte
        miniCarte.dessiner(context, niveauActuel.getLargeurNiveau());

        // Si niveau termine, dessiner message affichage
        if (niveauActuel.estTermine()){
            dessinerMessageGagner(context);
        }
    }

    private void dessinerMessageGagner(GraphicsContext context){
        String messageFin = "Niveau " + niveauActuel.getNumeroDuNiveau() + " complété";
        // Pour empecher d'avoir une erreur, verifier que l'opacite macimale est de 1
        opaciteCouleurTransition = Math.min(1,opaciteCouleurTransition);

        context.setFill(Color.WHITE);
        context.setFont(Font.font("Arial", 36));
        context.setTextAlign(TextAlignment.CENTER);
        context.fillText(messageFin,  (double) InvasionAgricoleJavaFX.LARGEUR_FENETRE / 2, hauteurNiveau / 2);

        context.setFill(Color.color(0, 0, 0, opaciteCouleurTransition));
        context.fillRect(0, 0, largeurNiveau, hauteurNiveau);
    }

    public boolean estJeuFini(){
        return estJeuFini;
    }

}
