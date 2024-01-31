module kotysa.ktor.jdbc {
    requires java.sql;
    requires com.h2database;
    requires io.ktor.http;
    requires io.ktor.serialization.kotlinx.json;
    requires io.ktor.server.content.negotiation;
    requires io.ktor.server.core;
    requires io.ktor.server.host.common;
    requires io.ktor.server.netty;
    requires kotlin.stdlib;
    requires kotlinx.datetime;
    requires kotlinx.serialization.core;
    requires kotysa.core;
    requires kotysa.jdbc;

    exports com.sample;
}