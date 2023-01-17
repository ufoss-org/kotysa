/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient

import org.ufoss.kotysa.vertx.mutiny.sqlclient.transaction.VertxTransactionalOp

public sealed interface VertxSqlClient : MutinySqlClient, VertxTransactionalOp
public interface MysqlVertxSqlClient : MysqlMutinySqlClient, VertxSqlClient
public interface PostgresqlVertxSqlClient : PostgresqlMutinySqlClient, VertxSqlClient
public interface MssqlVertxSqlClient : MssqlMutinySqlClient, VertxSqlClient
public interface MariadbVertxSqlClient : MariadbMutinySqlClient, VertxSqlClient
public interface OracleVertxSqlClient : OracleMutinySqlClient, VertxSqlClient
