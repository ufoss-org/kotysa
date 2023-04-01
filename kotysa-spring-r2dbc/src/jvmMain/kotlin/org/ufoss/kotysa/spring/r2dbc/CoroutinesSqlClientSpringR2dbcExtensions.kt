/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc

import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.*

/**
 * Create a [H2CoroutinesSqlClient] from a Spring [DatabaseClient] with [H2Tables] mapping
 *
 * @sample org.ufoss.kotysa.spring.r2dbc.sample.UserRepositorySpringR2dbcCoroutines
 */
public fun DatabaseClient.coSqlClient(tables: H2Tables): H2CoroutinesSqlClient =
    H2CoroutinesSqlClientSpringR2dbc(this, tables)

/**
 * Create a [MysqlCoroutinesSqlClient] from a Spring [DatabaseClient] with [MysqlTables] mapping
 *
 * @sample org.ufoss.kotysa.spring.r2dbc.sample.UserRepositorySpringR2dbcCoroutines
 */
public fun DatabaseClient.coSqlClient(tables: MysqlTables): MysqlCoroutinesSqlClient =
    MysqlCoroutinesSqlClientSpringR2dbc(this, tables)

/**
 * Create a [PostgresqlCoroutinesSqlClient] from a Spring [DatabaseClient] with [PostgresqlTables] mapping
 *
 * @sample org.ufoss.kotysa.spring.r2dbc.sample.UserRepositorySpringR2dbcCoroutines
 */
public fun DatabaseClient.coSqlClient(tables: PostgresqlTables): PostgresqlCoroutinesSqlClient =
    PostgresqlCoroutinesSqlClientSpringR2dbc(this, tables)

/**
 * Create a [MssqlCoroutinesSqlClient] from a Spring [DatabaseClient] with [MssqlTables] mapping
 *
 * @sample org.ufoss.kotysa.spring.r2dbc.sample.UserRepositorySpringR2dbcCoroutines
 */
public fun DatabaseClient.coSqlClient(tables: MssqlTables): MssqlCoroutinesSqlClient =
    MssqlCoroutinesSqlClientSpringR2dbc(this, tables)

/**
 * Create a [MariadbCoroutinesSqlClient] from a Spring [DatabaseClient] with [MariadbTables] mapping
 *
 * @sample org.ufoss.kotysa.spring.r2dbc.sample.UserRepositorySpringR2dbcCoroutines
 */
public fun DatabaseClient.coSqlClient(tables: MariadbTables): MariadbCoroutinesSqlClient =
    MariadbCoroutinesSqlClientSpringR2dbc(this, tables)

/**
 * Create a [OracleCoroutinesSqlClient] from a Spring [DatabaseClient] with [OracleTables] mapping
 *
 * @sample org.ufoss.kotysa.spring.r2dbc.sample.UserRepositorySpringR2dbcCoroutines
 */
public fun DatabaseClient.coSqlClient(tables: OracleTables): OracleCoroutinesSqlClient =
    OracleCoroutinesSqlClientSpringR2dbc(this, tables)
