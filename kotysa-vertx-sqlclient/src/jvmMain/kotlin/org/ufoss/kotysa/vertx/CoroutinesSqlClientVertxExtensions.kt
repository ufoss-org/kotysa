/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx

import io.vertx.sqlclient.Pool
import org.ufoss.kotysa.*

/**
 * Create a [MysqlCoroutinesVertxSqlClient] from a Vertx [Pool] with [MysqlTables] mapping
 *
 * @sample org.ufoss.kotysa.vertx.samples.UserRepositoryVertxCoroutines
 */
public fun Pool.coSqlClient(tables: MysqlTables): MysqlCoroutinesVertxSqlClient = MysqlCoroutinesSqlClientVertx(this, tables)

/**
 * Create a [PostgresqlCoroutinesVertxSqlClient] from a Vertx [Pool] with [PostgresqlTables] mapping
 *
 * @sample org.ufoss.kotysa.vertx.samples.UserRepositoryVertxCoroutines
 */
public fun Pool.coSqlClient(tables: PostgresqlTables): PostgresqlCoroutinesVertxSqlClient =
    PostgresqlCoroutinesSqlClientVertx(this, tables)

/**
 * Create a [MssqlCoroutinesVertxSqlClient] from a Vertx [Pool] with [MssqlTables] mapping
 *
 * @sample org.ufoss.kotysa.vertx.samples.UserRepositoryVertxCoroutines
 */
public fun Pool.coSqlClient(tables: MssqlTables): MssqlCoroutinesVertxSqlClient = MssqlCoroutinesSqlClientVertx(this, tables)

/**
 * Create a [MariadbCoroutinesVertxSqlClient] from a Vertx [Pool] with [MariadbTables] mapping
 *
 * @sample org.ufoss.kotysa.vertx.samples.UserRepositoryVertxCoroutines
 */
public fun Pool.coSqlClient(tables: MariadbTables): MariadbCoroutinesVertxSqlClient =
    MariadbCoroutinesSqlClientVertx(this, tables)

/**
 * Create a [OracleCoroutinesVertxSqlClient] from a Vertx [Pool] with [OracleTables] mapping
 *
 * @sample org.ufoss.kotysa.vertx.samples.UserRepositoryVertxCoroutines
 */
public fun Pool.coSqlClient(tables: OracleTables): OracleCoroutinesVertxSqlClient = OracleCoroutinesSqlClientVertx(this, tables)
