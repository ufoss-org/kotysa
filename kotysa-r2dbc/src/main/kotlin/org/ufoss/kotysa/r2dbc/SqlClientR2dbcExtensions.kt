/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import io.r2dbc.spi.ConnectionFactory
import kotlinx.coroutines.reactive.awaitSingle
import org.ufoss.kotysa.*
import org.ufoss.kotysa.r2dbc.transaction.R2dbcTransactionImpl
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.coroutines.coroutineContext
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
        "kotlinx.datetime.LocalTime" -> LocalTime::class
        else -> this
    }

internal suspend fun ConnectionFactory.getR2dbcConnection(): R2dbcConnection {
    // reuse currentTransaction's connection if any, else establish a new connection
    val transaction = coroutineContext[R2dbcTransactionImpl]
    val connection = if (transaction != null && !transaction.isCompleted()) {
        transaction.connection
    } else {
        create().awaitSingle()
    }

    return R2dbcConnection(connection, transaction != null)
}
