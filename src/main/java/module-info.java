module comprehensive.project.nasaapi {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    requires javafx.web;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.media;

    requires com.google.gson;
    requires okhttp3;

    opens comprehensive.project.nasaapi.models;
    opens comprehensive.project.nasaapi.models.ivl;

    opens comprehensive.project.nasaapi to javafx.fxml;
    exports comprehensive.project.nasaapi;

    exports comprehensive.project.nasaapi.controllers;
    opens comprehensive.project.nasaapi.controllers to javafx.fxml;
    opens comprehensive.project.nasaapi.database.DAO to com.google.gson;


}