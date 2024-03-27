module ro.ubbcluj.map.seminar7 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens ro.ubbcluj.map.seminar7 to javafx.fxml;
    exports ro.ubbcluj.map.seminar7;
    exports ro.ubbcluj.map.seminar7.GUI;
    opens ro.ubbcluj.map.seminar7.GUI to javafx.fxml;

    opens ro.ubbcluj.map.seminar7.domain to javafx.base;
    exports ro.ubbcluj.map.seminar7.domain;
}