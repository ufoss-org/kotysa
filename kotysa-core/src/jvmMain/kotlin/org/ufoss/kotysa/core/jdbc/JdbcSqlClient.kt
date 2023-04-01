/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

/* Must be the same package as sealed interface SqlClient */
package org.ufoss.kotysa

import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.transaction.TransactionalOp

public sealed interface JdbcSqlClient : SqlClient, TransactionalOp<JdbcTransaction>
public interface H2JdbcSqlClient : H2SqlClient, JdbcSqlClient
public interface MysqlJdbcSqlClient : MysqlSqlClient, JdbcSqlClient
public interface PostgresqlJdbcSqlClient : PostgresqlSqlClient, JdbcSqlClient
public interface MssqlJdbcSqlClient : MssqlSqlClient, JdbcSqlClient
public interface MariadbJdbcSqlClient : MariadbSqlClient, JdbcSqlClient
public interface OracleJdbcSqlClient : MariadbSqlClient, JdbcSqlClient
