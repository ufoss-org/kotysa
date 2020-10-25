/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kotysa.h2.H2TablesDsl
import org.ufoss.kotysa.mysql.MysqlTablesDsl
import org.ufoss.kotysa.postgresql.PostgresqlTablesDsl
import org.ufoss.kotysa.sqlite.SqLiteTablesDsl

/**
 * Supported Database Choice
 */
public object DbTypeChoice {

    /**
     * Configure Functional Table Mapping support for H2
     * @sample org.ufoss.kotysa.sample.h2Tables
     * @see H2TablesDsl
     */
    public fun h2(dsl: H2TablesDsl.() -> Unit): Tables {
        val tablesDsl = H2TablesDsl(dsl, DbType.H2)
        return tablesDsl.initialize(tablesDsl)
    }

    /**
     * Configure Functional Table Mapping support for SqLite
     * @sample org.ufoss.kotysa.sample.sqLiteTables
     * @see SqLiteTablesDsl
     */
    public fun sqlite(dsl: SqLiteTablesDsl.() -> Unit): Tables {
        val tablesDsl = SqLiteTablesDsl(dsl, DbType.SQLITE)
        return tablesDsl.initialize(tablesDsl)
    }

    /**
     * Configure Functional Table Mapping support for PostgreSQL
     * @sample org.ufoss.kotysa.sample.postgresqlTables
     * @see PostgresqlTablesDsl
     */
    public fun postgresql(dsl: PostgresqlTablesDsl.() -> Unit): Tables {
        val tablesDsl = PostgresqlTablesDsl(dsl, DbType.POSTGRESQL)
        return tablesDsl.initialize(tablesDsl)
    }

    /**
     * Configure Functional Table Mapping support for MySQL
     * @sample org.ufoss.kotysa.sample.mysqlTables
     * @see MysqlTablesDsl
     */
    public fun mysql(dsl: MysqlTablesDsl.() -> Unit): Tables {
        val tablesDsl = MysqlTablesDsl(dsl, DbType.MYSQL)
        return tablesDsl.initialize(tablesDsl)
    }
}

/**
 * Choose the database's Type
 *
 * @see TablesDsl
 * @see DbTypeChoice
 */
public fun tables(): DbTypeChoice = DbTypeChoice
