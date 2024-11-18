module ca.qc.bdeb.sim.invasionagricole {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens ca.qc.bdeb.sim.invasionagricole to javafx.fxml;
    exports ca.qc.bdeb.sim.invasionagricole;
    exports ca.qc.bdeb.sim.invasionagricole.objet.physique;
    opens ca.qc.bdeb.sim.invasionagricole.objet.physique to javafx.fxml;
    exports ca.qc.bdeb.sim.invasionagricole.objet.physique.vivant;
    opens ca.qc.bdeb.sim.invasionagricole.objet.physique.vivant to javafx.fxml;
    exports ca.qc.bdeb.sim.invasionagricole.objet.physique.projectiles;
    opens ca.qc.bdeb.sim.invasionagricole.objet.physique.projectiles to javafx.fxml;
    exports ca.qc.bdeb.sim.invasionagricole.objet.background;
    opens ca.qc.bdeb.sim.invasionagricole.objet.background to javafx.fxml;
}