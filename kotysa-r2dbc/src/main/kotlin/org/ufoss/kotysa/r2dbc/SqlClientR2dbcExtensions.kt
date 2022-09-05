/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import io.r2dbc.spi.ConnectionFactory
import org.ufoss.kotysa.*
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.reflect.KClass

/**
 * Create a [H2R2dbcSqlClient] from a R2DBC [ConnectionFactory] with [H2Tables] mapping
 *
 * @sample org.ufoss.kotysa.r2dbc.sample.UserRepositoryR2dbc
 */
public fun ConnectionFactory.sqlClient(tables: H2Tables): H2R2dbcSqlClient = H2SqlClientR2dbc(this, tables)

/**
 * Create a [MysqlR2dbcSqlClient] from a R2DBC [ConnectionFactory] with [MysqlTables] mapping
 *
 * @sample org.ufoss.kotysa.r2dbc.sample.UserRepositoryR2dbc
 */
public fun ConnectionFactory.sqlClient(tables: MysqlTables): MysqlR2dbcSqlClient = MysqlSqlClientR2dbc(this, tables)

/**
 * Create a [PostgresqlR2dbcSqlClient] from a R2DBC [ConnectionFactory] with [PostgresqlTables] mapping
 *
 * @sample org.ufoss.kotysa.r2dbc.sample.UserRepositoryR2dbc
 */
public fun ConnectionFactory.sqlClient(tables: PostgresqlTables): PostgresqlR2dbcSqlClient = PostgresqlSqlClientR2dbc(this, tables)

/**
 * Create a [MssqlR2dbcSqlClient] from a R2DBC [ConnectionFactory] with [MssqlTables] mapping
 *
 * @sample org.ufoss.kotysa.r2dbc.sample.UserRepositoryR2dbc
 */
public fun ConnectionFactory.sqlClient(tables: MssqlTables): MssqlR2dbcSqlClient = MssqlSqlClientR2dbc(this, tables)

/**
 * Create a [MariadbR2dbcSqlClient] from a R2DBC [ConnectionFactory] with [MariadbTables] mapping
 *
 * @sample org.ufoss.kotysa.r2dbc.sample.UserRepositoryR2dbc
 */
public fun ConnectionFactory.sqlClient(tables: MariadbTables): MariadbR2dbcSqlClient = MariadbSqlClientR2dbc(this, tables)

internal fun KClass<*>.toDbClass() =
    when (this.qualifiedName) {
        "kotlinx.datetime.LocalDate" -> LocalDate::class
        "kotlinx.datetime.LocalDateTime" -> LocalDateTime::class
        else -> this
    }
