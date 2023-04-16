/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

module kotysa.vertx.sqlclient {
    requires io.smallrye.mutiny;
    requires io.smallrye.mutiny.vertx.sql.client;
    requires io.vertx.client.sql;
    requires io.vertx.core;
    requires kotlin.stdlib;
    requires kotysa.core;

    exports org.ufoss.kotysa.vertx.mutiny.sqlclient;
    exports org.ufoss.kotysa.vertx.mutiny.sqlclient.transaction;
}