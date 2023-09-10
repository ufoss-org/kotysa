/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

module kotysa.vertx.sqlclient {
    requires io.vertx.client.sql;
    requires io.vertx.core;
    requires kotlin.stdlib;
    requires kotysa.core;

    requires static io.smallrye.mutiny;
    requires static io.smallrye.mutiny.vertx.sql.client;
    requires static io.vertx.kotlin.coroutines;
    requires static kotlinx.coroutines.core;

    exports org.ufoss.kotysa.vertx;
    exports org.ufoss.kotysa.vertx.transaction;
}