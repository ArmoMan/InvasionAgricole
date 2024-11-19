module ca.qc.bdeb.sim.invasionagricole {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens ca.qc.bdeb.sim.invasionagricole to javafx.fxml;
    exports ca.qc.bdeb.sim.invasionagricole;
    exports ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique;
    opens ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique to javafx.fxml;
    exports ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.vivant;
    opens ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.vivant to javafx.fxml;
    exports ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.projectiles;
    opens ca.qc.bdeb.sim.invasionagricole.objetsjeu.physique.projectiles to javafx.fxml;
    exports ca.qc.bdeb.sim.invasionagricole.objetsjeu.background;
    opens ca.qc.bdeb.sim.invasionagricole.objetsjeu.background to javafx.fxml;
}