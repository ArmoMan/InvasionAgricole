module ca.qc.bdeb.sim.invasionagricole {
    requires javafx.controls;
    requires javafx.fxml;


    opens ca.qc.bdeb.sim.invasionagricole to javafx.fxml;
    exports ca.qc.bdeb.sim.invasionagricole;
}