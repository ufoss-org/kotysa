/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

module kotysa.spring.r2dbc {
    requires kotlin.stdlib;
    requires kotysa.core;
    requires spring.r2dbc;

    exports org.ufoss.kotysa.spring.r2dbc;
    exports org.ufoss.kotysa.spring.r2dbc.transaction;
}