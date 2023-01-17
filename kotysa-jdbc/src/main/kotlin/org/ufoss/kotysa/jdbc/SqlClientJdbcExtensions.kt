/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc

import org.ufoss.kotysa.*
import javax.sql.DataSource

/**
 * Create a [H2JdbcSqlClient] from a JDBC [DataSource] with [H2Tables] mapping
 *
 * @sample org.ufoss.kotysa.jdbc.sample.UserRepositoryJdbc
 */
public fun DataSource.sqlClient(tables: H2Tables): H2JdbcSqlClient = H2SqlClientJdbc(this, tables)

/**
 * Create a [MysqlJdbcSqlClient] from a JDBC [DataSource] with [MysqlTables] mapping
 *
 * @sample org.ufoss.kotysa.jdbc.sample.UserRepositoryJdbc
 */
public fun DataSource.sqlClient(tables: MysqlTables): MysqlJdbcSqlClient = MysqlSqlClientJdbc(this, tables)

/**
 * Create a [PostgresqlJdbcSqlClient] from a JDBC [DataSource] with [PostgresqlTables] mapping
 *
 * @sample org.ufoss.kotysa.jdbc.sample.UserRepositoryJdbc
 */
public fun DataSource.sqlClient(tables: PostgresqlTables): PostgresqlJdbcSqlClient = PostgresqlSqlClientJdbc(this, tables)

/**
 * Create a [MssqlJdbcSqlClient] from a JDBC [DataSource] with [MssqlTables] mapping
 *
 * @sample org.ufoss.kotysa.jdbc.sample.UserRepositoryJdbc
 */
public fun DataSource.sqlClient(tables: MssqlTables): MssqlJdbcSqlClient = MssqlSqlClientJdbc(this, tables)

/**
 * Create a [MariadbJdbcSqlClient] from a JDBC [DataSource] with [MariadbTables] mapping
 *
 * @sample org.ufoss.kotysa.jdbc.sample.UserRepositoryJdbc
 */
public fun DataSource.sqlClient(tables: MariadbTables): MariadbJdbcSqlClient = MariadbSqlClientJdbc(this, tables)

/**
 * Create a [OracleJdbcSqlClient] from a JDBC [DataSource] with [OracleTables] mapping
 *
 * @sample org.ufoss.kotysa.jdbc.sample.UserRepositoryJdbc
 */
public fun DataSource.sqlClient(tables: OracleTables): OracleJdbcSqlClient = OracleSqlClientJdbc(this, tables)
