/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

module kotysa.spring.jdbc {
    requires java.sql;
    requires kotlin.stdlib;
    requires kotysa.core;
    requires spring.jdbc;

    exports org.ufoss.kotysa.spring.jdbc;
    exports org.ufoss.kotysa.spring.jdbc.transaction;
}