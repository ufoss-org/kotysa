module kotysa.ktor.r2dbc.coroutines {
    requires io.ktor.server.core;
    requires io.ktor.server.host.common;
    requires io.ktor.server.netty;
    requires kotlin.stdlib;
    requires kotysa.core;
    requires kotysa.r2dbc;

    exports com.sample;
}