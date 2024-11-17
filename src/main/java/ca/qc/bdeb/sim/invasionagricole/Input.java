package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.input.KeyCode;

import java.util.HashMap;

public class Input {

    public static boolean modeDebogageActif;
    private static boolean toucheDebogageRelachee = true;


    private static HashMap<KeyCode, Boolean> touches = new HashMap<>();

    public static boolean isKeyPressed(KeyCode code) {
        return touches.getOrDefault(code, false);
    }

    public static void setKeyPressed(KeyCode code, boolean isPressed) {
        touches.put(code, isPressed);
    }

    public static void gererToggleModeDebogage() {
        if (isKeyPressed(KeyCode.D)) {
            if (toucheDebogageRelachee) {
                modeDebogageActif = !modeDebogageActif;
                toucheDebogageRelachee = false;
            }
        } else {
            toucheDebogageRelachee = true;
        }
    }
}
