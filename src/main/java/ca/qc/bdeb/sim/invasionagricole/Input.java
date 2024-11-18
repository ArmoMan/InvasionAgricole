package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.input.KeyCode;

import java.util.HashMap;

public class Input {

    public static boolean modeDebogageActif;
    private static boolean toucheDebogageRelachee = true;

    // Mode Invincible
    public static boolean modeInvincible = false;
    private static boolean toucheInvincibleRelachee = true;

    // Touche ajouter vie
    public static boolean ajouterVie = false;
    private static boolean ajouterVieRelachee = true;

    private static HashMap<KeyCode, Boolean> touches = new HashMap<>();

    public static boolean isKeyPressed(KeyCode code) {
        return touches.getOrDefault(code, false);
    }

    public static boolean estToucheEncoreAppuyer(KeyCode code) {
        return touches.containsKey(code);
    }

    public static void setKeyPressed(KeyCode code, boolean isPressed) {
        touches.put(code, isPressed);
    }

    public static void gererToggleModeDebogage() {
        // Gestion du mode debugage
        if (isKeyPressed(KeyCode.D)) {
            if (toucheDebogageRelachee) {
                modeDebogageActif = !modeDebogageActif;
                toucheDebogageRelachee = false;
            }
        } else {
            toucheDebogageRelachee = true;
        }


        // Gestion de la touche Q pour le mode Invincible
        if (isKeyPressed(KeyCode.Q)) {
            if (toucheInvincibleRelachee) {
                modeInvincible = !modeInvincible;
                toucheInvincibleRelachee = false;
            }
        } else {
            toucheInvincibleRelachee = true;
        }

        // Mettre ajouterVie true seulement lorsque on appuie une seule fois.
        // Pour avoir encore true, il faut re-lacher et re-appuyer
        if (isKeyPressed(KeyCode.W)) {
            if (ajouterVieRelachee){
                ajouterVieRelachee = false;
                ajouterVie = true;
            }else {
                ajouterVie = false;
            }
        } else {
            ajouterVieRelachee = true;
        }

    }

}
