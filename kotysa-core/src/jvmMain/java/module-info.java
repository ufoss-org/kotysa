/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

module kotysa.core {
    requires org.slf4j;
    requires kotlin.reflect;
    requires kotlin.stdlib;
    requires kolog;

    requires static java.sql;
    requires static kotlinx.datetime;
    requires static r2dbc.spi;
    // todo uncomment with kotlinx.coroutines 1.7.x
//    requires static kotlinx.coroutines.core;

    exports org.ufoss.kotysa;
    exports org.ufoss.kotysa.columns;
    exports org.ufoss.kotysa.core.jdbc;
    exports org.ufoss.kotysa.core.jdbc.transaction;
    exports org.ufoss.kotysa.core.r2dbc;
    exports org.ufoss.kotysa.core.r2dbc.transaction;
    exports org.ufoss.kotysa.h2;
    exports org.ufoss.kotysa.mariadb;
    exports org.ufoss.kotysa.mssql;
    exports org.ufoss.kotysa.mysql;
    exports org.ufoss.kotysa.oracle;
    exports org.ufoss.kotysa.postgresql;
    exports org.ufoss.kotysa.transaction;
}