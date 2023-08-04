/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc

import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.*

/**
 * Create a [H2ReactorSqlClient] from a Spring [DatabaseClient] with [H2Tables] mapping
 *
 * @sample org.ufoss.kotysa.spring.r2dbc.sample.UserRepositorySpringR2dbc
 */
public fun DatabaseClient.sqlClient(tables: H2Tables): H2ReactorSqlClient = H2SqlClientSpringR2dbc(this, tables)

/**
 * Create a [MysqlReactorSqlClient] from a Spring [DatabaseClient] with [MysqlTables] mapping
 *
 * @sample org.ufoss.kotysa.spring.r2dbc.sample.UserRepositorySpringR2dbc
 */
public fun DatabaseClient.sqlClient(tables: MysqlTables): MysqlReactorSqlClient =
    MysqlSqlClientSpringR2dbc(this, tables)

/**
 * Create a [PostgresqlReactorSqlClient] from a Spring [DatabaseClient] with [PostgresqlTables] mapping
 *
 * @sample org.ufoss.kotysa.spring.r2dbc.sample.UserRepositorySpringR2dbc
 */
public fun DatabaseClient.sqlClient(tables: PostgresqlTables): PostgresqlReactorSqlClient =
    PostgresqlSqlClientSpringR2dbc(this, tables)

/**
 * Create a [MssqlReactorSqlClient] from a Spring [DatabaseClient] with [MssqlTables] mapping
 *
 * @sample org.ufoss.kotysa.spring.r2dbc.sample.UserRepositorySpringR2dbc
 */
public fun DatabaseClient.sqlClient(tables: MssqlTables): MssqlReactorSqlClient =
    MssqlSqlClientSpringR2dbc(this, tables)

/**
 * Create a [MariadbReactorSqlClient] from a Spring [DatabaseClient] with [MariadbTables] mapping
 *
 * @sample org.ufoss.kotysa.spring.r2dbc.sample.UserRepositorySpringR2dbc
 */
public fun DatabaseClient.sqlClient(tables: MariadbTables): MariadbReactorSqlClient =
    MariadbSqlClientSpringR2dbc(this, tables)

/**
 * Create a [OracleReactorSqlClient] from a Spring [DatabaseClient] with [OracleTables] mapping
 *
 * @sample org.ufoss.kotysa.spring.r2dbc.sample.UserRepositorySpringR2dbc
 */
public fun DatabaseClient.sqlClient(tables: OracleTables): OracleReactorSqlClient =
    OracleSqlClientSpringR2dbc(this, tables)
