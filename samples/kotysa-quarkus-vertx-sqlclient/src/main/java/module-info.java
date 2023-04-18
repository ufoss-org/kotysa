module kotysa.quarkus.vertx.sqlclient {
    requires io.smallrye.mutiny.vertx.pg.client;
    requires kotlin.stdlib;
    requires kotysa.vertx.sqlclient;

    exports com.sample;
}