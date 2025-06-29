module com.hairapp.game {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jdk.compiler;


    opens com.hairapp.game to javafx.fxml;
    exports com.hairapp.game;
}