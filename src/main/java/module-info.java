module comprehensive.project.nasaapi {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    requires com.google.gson;

    opens comprehensive.project.nasaapi to javafx.fxml;
    exports comprehensive.project.nasaapi;

    exports comprehensive.project.nasaapi.controllers;
    opens comprehensive.project.nasaapi.controllers to javafx.fxml;
    opens comprehensive.project.nasaapi.database.DAO to com.google.gson;


}