module kotysa.spring.r2dbc.reactor {
    requires kotlin.stdlib;
    requires kotysa.core;
    requires kotysa.spring.r2dbc;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.data.r2dbc;
    requires spring.r2dbc;
    requires spring.tx;
    requires spring.web;
    requires spring.webflux;
    requires r2dbc.spi;
    requires reactor.core;

    exports com.sample;
}