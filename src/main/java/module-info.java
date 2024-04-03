module comprehensive.project.nasaapi {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens comprehensive.project.nasaapi to javafx.fxml;
    exports comprehensive.project.nasaapi;

    exports comprehensive.project.nasaapi.controllers;
    opens comprehensive.project.nasaapi.controllers to javafx.fxml;
}