/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mssql

import io.vertx.mssqlclient.MSSQLException
import org.junit.jupiter.api.Order
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInsertRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInsertTest

@Order(3)
class VertxCoroutinesInsertMssqlTest : AbstractVertxCoroutinesMssqlTest<RepositoryMssqlInsert>(),
    CoroutinesInsertTest<MssqlInts, MssqlLongs, MssqlCustomers, MssqlIntNonNullIds, MssqlLongNonNullIds,
            RepositoryMssqlInsert, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = RepositoryMssqlInsert(sqlClient)
    override val exceptionClass = MSSQLException::class.java
}

class RepositoryMssqlInsert(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesInsertRepository<MssqlInts, MssqlLongs, MssqlCustomers, MssqlIntNonNullIds, MssqlLongNonNullIds>(
        sqlClient,
        MssqlInts,
        MssqlLongs,
        MssqlCustomers,
        MssqlIntNonNullIds,
        MssqlLongNonNullIds
    )
