/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient

import io.vertx.mutiny.sqlclient.Pool
import org.ufoss.kotysa.*

/**
 * Create a [MysqlVertxSqlClient] from a Vertx [Pool] with [MysqlTables] mapping
 *
 * @sample org.ufoss.kotysa.jdbc.sample.UserRepositoryJdbc
 */
public fun Pool.sqlClient(tables: MysqlTables): MysqlVertxSqlClient = MysqlSqlClientVertx(this, tables)

/**
 * Create a [PostgresqlJdbcSqlClient] from a Vertx [Pool] with [PostgresqlTables] mapping
 *
 * @sample org.ufoss.kotysa.jdbc.sample.UserRepositoryJdbc
 */
public fun Pool.sqlClient(tables: PostgresqlTables): PostgresqlVertxSqlClient = PostgresqlSqlClientVertx(this, tables)

///**
// * Create a [MssqlJdbcSqlClient] from a Vertx [Pool] with [MssqlTables] mapping
// *
// * @sample org.ufoss.kotysa.jdbc.sample.UserRepositoryJdbc
// */
//public fun Pool.sqlClient(tables: MssqlTables): MssqlJdbcSqlClient = MssqlSqlClientJdbc(this, tables)

/**
 * Create a [MariadbVertxSqlClient] from a Vertx [Pool] with [MariadbTables] mapping
 *
 * @sample org.ufoss.kotysa.jdbc.sample.UserRepositoryJdbc
 */
public fun Pool.sqlClient(tables: MariadbTables): MariadbVertxSqlClient = MariadbSqlClientVertx(this, tables)
