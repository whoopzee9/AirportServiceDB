module SMO {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires java.base;
    requires javax.xml.bind;

    opens sample;
    opens sample.tables;
    opens sample.UI;
}