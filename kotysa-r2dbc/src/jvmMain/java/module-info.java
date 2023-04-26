/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

module kotysa.r2dbc {
    requires java.sql;
    requires kotlin.stdlib;
    requires kotysa.core;
    requires r2dbc.spi;

    // todo uncomment with kotlinx.coroutines 1.7.x
    //    requires static kotlinx.coroutines.core;

    exports org.ufoss.kotysa.r2dbc;
    exports org.ufoss.kotysa.r2dbc.transaction;
}