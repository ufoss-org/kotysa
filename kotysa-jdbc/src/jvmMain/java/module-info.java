/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

module kotysa.jdbc {
    requires java.sql;
    requires kotlin.stdlib;
    requires kotysa.core;

    exports org.ufoss.kotysa.jdbc;
    exports org.ufoss.kotysa.jdbc.transaction;
}