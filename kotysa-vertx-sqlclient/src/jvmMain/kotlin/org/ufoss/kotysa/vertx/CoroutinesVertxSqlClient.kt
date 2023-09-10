/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx

import org.ufoss.kotysa.*
import org.ufoss.kotysa.transaction.CoroutinesTransactionalOp
import org.ufoss.kotysa.transaction.Transaction

public sealed interface CoroutinesVertxSqlClient : CoroutinesSqlClient,
    CoroutinesTransactionalOp<Transaction>
public interface MysqlCoroutinesVertxSqlClient : MysqlCoroutinesSqlClient, CoroutinesVertxSqlClient
public interface PostgresqlCoroutinesVertxSqlClient : PostgresqlCoroutinesSqlClient, CoroutinesVertxSqlClient
public interface MssqlCoroutinesVertxSqlClient : MssqlCoroutinesSqlClient, CoroutinesVertxSqlClient
public interface MariadbCoroutinesVertxSqlClient : MariadbCoroutinesSqlClient, CoroutinesVertxSqlClient
public interface OracleCoroutinesVertxSqlClient : OracleCoroutinesSqlClient, CoroutinesVertxSqlClient
