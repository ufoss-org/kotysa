/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

/* Must be the same package as sealed interface CoroutinesSqlClient */
package org.ufoss.kotysa

import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.transaction.CoroutinesTransactionalOp

public sealed interface R2dbcSqlClient : CoroutinesSqlClient, CoroutinesTransactionalOp<R2dbcTransaction>
public interface H2R2dbcSqlClient : H2CoroutinesSqlClient, R2dbcSqlClient
public interface MysqlR2dbcSqlClient : MysqlCoroutinesSqlClient, R2dbcSqlClient
public interface PostgresqlR2dbcSqlClient : PostgresqlCoroutinesSqlClient, R2dbcSqlClient
public interface MssqlR2dbcSqlClient : MssqlCoroutinesSqlClient, R2dbcSqlClient
public interface MariadbR2dbcSqlClient : MariadbCoroutinesSqlClient, R2dbcSqlClient
