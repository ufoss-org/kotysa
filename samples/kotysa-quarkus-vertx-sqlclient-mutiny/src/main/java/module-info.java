module kotysa.quarkus.vertx.sqlclient.mutiny {
    requires io.smallrye.mutiny;
    requires io.smallrye.mutiny.vertx.pg.client;
    requires jakarta.cdi;
    requires jakarta.ws.rs;
    requires kotlin.stdlib;
    requires kotysa.core;
    requires kotysa.vertx.sqlclient;

    exports com.sample;
}