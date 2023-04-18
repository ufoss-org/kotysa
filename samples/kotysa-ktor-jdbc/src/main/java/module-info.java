module kotysa.ktor.jdbc {
    requires java.sql;
    requires com.h2database;
    requires io.ktor.server.core;
    requires io.ktor.server.host.common;
    requires io.ktor.server.netty;
    requires kotlin.stdlib;
    requires kotysa.jdbc;

    exports com.sample;
}