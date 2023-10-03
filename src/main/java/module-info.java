module com.valeriygulin.ssespringbootjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires retrofit2;
    requires static lombok;
    requires retrofit2.converter.jackson;
    requires okhttp.eventsource;
    requires okhttp3;


    opens com.valeriygulin.ssespringbootjavafx to javafx.fxml;
    exports com.valeriygulin.ssespringbootjavafx;
    exports com.valeriygulin.ssespringbootjavafx.controllers;
    opens com.valeriygulin.ssespringbootjavafx.controllers to javafx.fxml;
    exports com.valeriygulin.ssespringbootjavafx.dto;
    exports com.valeriygulin.ssespringbootjavafx.model;
    opens com.valeriygulin.ssespringbootjavafx.model to javafx.fxml;
}