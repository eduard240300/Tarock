module TarockClient {
    requires java.sql;
    requires java.desktop;

    exports GUI.ConnectionForm;
    opens GUI;
}