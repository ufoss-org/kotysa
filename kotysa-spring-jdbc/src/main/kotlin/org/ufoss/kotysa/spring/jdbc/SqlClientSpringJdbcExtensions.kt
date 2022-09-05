/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc

import org.springframework.jdbc.core.JdbcOperations
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.set
import org.ufoss.kotysa.*

/**
 * Create a [H2SqlClient] from a Spring [JdbcOperations] with [H2Tables] mapping
 *
 * @sample org.ufoss.kotysa.spring.jdbc.sample.UserRepositorySpringJdbc
 */
public fun JdbcOperations.sqlClient(tables: H2Tables): H2SqlClient = H2SqlClientSpringJdbc(this, tables)

/**
 * Create a [MysqlSqlClient] from a Spring [JdbcOperations] with [MysqlTables] mapping
 *
 * @sample org.ufoss.kotysa.spring.jdbc.sample.UserRepositorySpringJdbc
 */
public fun JdbcOperations.sqlClient(tables: MysqlTables): MysqlSqlClient = MysqlSqlClientSpringJdbc(this, tables)

/**
 * Create a [PostgresqlSqlClient] from a Spring [JdbcOperations] with [PostgresqlTables] mapping
 *
 * @sample org.ufoss.kotysa.spring.jdbc.sample.UserRepositorySpringJdbc
 */
public fun JdbcOperations.sqlClient(tables: PostgresqlTables): PostgresqlSqlClient = PostgresqlSqlClientSpringJdbc(this, tables)

/**
 * Create a [MssqlSqlClient] from a Spring [JdbcOperations] with [MssqlTables] mapping
 *
 * @sample org.ufoss.kotysa.spring.jdbc.sample.UserRepositorySpringJdbc
 */
public fun JdbcOperations.sqlClient(tables: MssqlTables): MssqlSqlClient = MssqlSqlClientSpringJdbc(this, tables)

/**
 * Create a [MariadbSqlClient] from a Spring [JdbcOperations] with [MariadbTables] mapping
 *
 * @sample org.ufoss.kotysa.spring.jdbc.sample.UserRepositorySpringJdbc
 */
public fun JdbcOperations.sqlClient(tables: MariadbTables): MariadbSqlClient = MariadbSqlClientSpringJdbc(this, tables)

internal fun DefaultSqlClientCommon.Properties.springJdbcBindParams(
    parameters: MapSqlParameterSource
) {
    dbValues()
        .forEach { dbValue -> parameters["k${index++}"] = dbValue }
}
