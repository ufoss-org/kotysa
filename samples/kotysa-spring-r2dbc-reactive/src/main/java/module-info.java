module kotysa.spring.r2dbc.reactive {
    requires kotlin.stdlib;
    requires kotysa.spring.r2dbc;
    requires spring.boot.autoconfigure;

    exports com.sample;
}