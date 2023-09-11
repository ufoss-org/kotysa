/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

module kotysa.spring.r2dbc {
    requires kotlin.stdlib;
    requires kotysa.core;
    requires r2dbc.spi;
    requires spring.r2dbc;

    // todo uncomment with kotlinx.coroutines 1.7.x
//    requires static kotlinx.coroutines.core;

    exports org.ufoss.kotysa.spring.r2dbc;
    exports org.ufoss.kotysa.spring.r2dbc.transaction;
}