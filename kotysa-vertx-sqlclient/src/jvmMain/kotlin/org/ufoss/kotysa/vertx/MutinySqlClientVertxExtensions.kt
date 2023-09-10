/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx

import io.vertx.mutiny.sqlclient.Pool
import org.ufoss.kotysa.*

/**
 * Create a [MysqlMutinyVertxSqlClient] from a Vertx [Pool] with [MysqlTables] mapping
 *
 * @sample org.ufoss.kotysa.vertx.samples.UserRepositoryVertxMutiny
 */
public fun Pool.sqlClient(tables: MysqlTables): MysqlMutinyVertxSqlClient = MysqlMutinySqlClientVertx(this, tables)

/**
 * Create a [PostgresqlMutinyVertxSqlClient] from a Vertx [Pool] with [PostgresqlTables] mapping
 *
 * @sample org.ufoss.kotysa.vertx.samples.UserRepositoryVertxMutiny
 */
public fun Pool.sqlClient(tables: PostgresqlTables): PostgresqlMutinyVertxSqlClient =
    PostgresqlMutinySqlClientVertx(this, tables)

/**
 * Create a [MssqlMutinyVertxSqlClient] from a Vertx [Pool] with [MssqlTables] mapping
 *
 * @sample org.ufoss.kotysa.vertx.samples.UserRepositoryVertxMutiny
 */
public fun Pool.sqlClient(tables: MssqlTables): MssqlMutinyVertxSqlClient = MssqlMutinySqlClientVertx(this, tables)

/**
 * Create a [MariadbMutinyVertxSqlClient] from a Vertx [Pool] with [MariadbTables] mapping
 *
 * @sample org.ufoss.kotysa.vertx.samples.UserRepositoryVertxMutiny
 */
public fun Pool.sqlClient(tables: MariadbTables): MariadbMutinyVertxSqlClient =
    MariadbMutinySqlClientVertx(this, tables)

/**
 * Create a [OracleMutinyVertxSqlClient] from a Vertx [Pool] with [OracleTables] mapping
 *
 * @sample org.ufoss.kotysa.vertx.samples.UserRepositoryVertxMutiny
 */
public fun Pool.sqlClient(tables: OracleTables): OracleMutinyVertxSqlClient = OracleMutinySqlClientVertx(this, tables)
