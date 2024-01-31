module kotysa.spring.r2dbc.coroutines {
    requires kotlin.stdlib;
    requires kotysa.core;
    requires kotysa.spring.r2dbc;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;

    exports com.sample;
}