/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import io.r2dbc.spi.R2dbcDataIntegrityViolationException
import org.junit.jupiter.api.Order
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInsertRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInsertTest

@Order(3)
class R2dbcInsertMssqlTest : AbstractR2dbcMssqlTest<RepositoryMssqlInsert>(),
    CoroutinesInsertTest<MssqlInts, MssqlLongs, MssqlCustomers, MssqlIntNonNullIds, MssqlLongNonNullIds,
            RepositoryMssqlInsert, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = RepositoryMssqlInsert(sqlClient)
    override val exceptionClass = R2dbcDataIntegrityViolationException::class.java
}

class RepositoryMssqlInsert(sqlClient: R2dbcSqlClient) :
    CoroutinesInsertRepository<MssqlInts, MssqlLongs, MssqlCustomers, MssqlIntNonNullIds, MssqlLongNonNullIds>(
        sqlClient,
        MssqlInts,
        MssqlLongs,
        MssqlCustomers,
        MssqlIntNonNullIds,
        MssqlLongNonNullIds
    )
