module kotysa.quarkus.jdbc {
    requires java.sql;
    requires java.ws.rs;
    requires kotlin.stdlib;
    requires kotysa.jdbc;

    exports com.sample;
}