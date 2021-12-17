module myBomberMan {
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.desktop;

    opens com.ninh236.mybomberman to javafx.fxml;
}