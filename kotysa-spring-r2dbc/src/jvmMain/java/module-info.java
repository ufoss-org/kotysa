/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

module kotysa.spring.r2dbc {
    requires kotlin.stdlib;
    requires kotysa.core;
    requires r2dbc.spi;
    requires spring.r2dbc;
    
    requires static kotlinx.coroutines.core;

    exports org.ufoss.kotysa.spring.r2dbc;
    exports org.ufoss.kotysa.spring.r2dbc.transaction;
}