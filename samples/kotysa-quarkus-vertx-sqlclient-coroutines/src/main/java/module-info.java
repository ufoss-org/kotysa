module kotysa.quarkus.vertx.sqlclient.coroutines {
    requires io.vertx.client.sql.pg;
    requires jakarta.cdi;
    requires jakarta.ws.rs;
    requires kotlin.stdlib;
    requires kotlinx.coroutines.core;
    requires kotysa.core;
    requires kotysa.vertx.sqlclient;

    exports com.sample;
}