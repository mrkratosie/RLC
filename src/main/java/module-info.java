module com.example.rlcsimulator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;

    opens com.example.rlcsimulator to javafx.fxml;
    exports com.example.rlcsimulator;
}