module kotysa.quarkus.vertx.sqlclient {
    requires io.smallrye.mutiny.vertx.pg.client;
    requires jakarta.cdi;
    requires jakarta.ws.rs;
    requires kotlin.stdlib;
    requires kotysa.vertx.sqlclient;

    exports com.sample;
}