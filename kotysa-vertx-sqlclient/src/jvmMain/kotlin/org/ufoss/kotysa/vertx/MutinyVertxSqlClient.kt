/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx

import org.ufoss.kotysa.vertx.transaction.MutinyVertxTransactionalOp

public sealed interface MutinyVertxSqlClient : MutinySqlClient, MutinyVertxTransactionalOp
public interface MysqlMutinyVertxSqlClient : MysqlMutinySqlClient, MutinyVertxSqlClient
public interface PostgresqlMutinyVertxSqlClient : PostgresqlMutinySqlClient, MutinyVertxSqlClient
public interface MssqlMutinyVertxSqlClient : MssqlMutinySqlClient, MutinyVertxSqlClient
public interface MariadbMutinyVertxSqlClient : MariadbMutinySqlClient, MutinyVertxSqlClient
public interface OracleMutinyVertxSqlClient : OracleMutinySqlClient, MutinyVertxSqlClient
