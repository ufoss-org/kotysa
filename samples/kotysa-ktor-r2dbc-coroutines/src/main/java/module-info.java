module kotysa.ktor.r2dbc.coroutines {
    requires io.ktor.http;
    requires io.ktor.serialization.kotlinx.json;
    requires io.ktor.server.content.negotiation;
    requires io.ktor.server.core;
    requires io.ktor.server.host.common;
    requires io.ktor.server.netty;
    requires kotlin.stdlib;
    requires kotlinx.coroutines.core;
    requires kotlinx.datetime;
    requires kotlinx.serialization.json;
    requires kotysa.core;
    requires kotysa.r2dbc;
    requires r2dbc.spi;

    exports com.sample;
}