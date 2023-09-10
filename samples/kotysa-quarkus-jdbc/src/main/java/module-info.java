module kotysa.quarkus.jdbc {
    requires java.sql;
    requires jakarta.cdi;
    requires jakarta.ws.rs;
    requires kotlin.stdlib;
    requires kotysa.core;
    requires kotysa.jdbc;

    exports com.sample;
}