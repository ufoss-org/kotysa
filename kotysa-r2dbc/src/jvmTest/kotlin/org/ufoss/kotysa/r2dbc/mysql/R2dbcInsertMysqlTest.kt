/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import io.r2dbc.spi.R2dbcDataIntegrityViolationException
import org.junit.jupiter.api.Order
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInsertRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInsertTest

@Order(3)
class R2dbcInsertMysqlTest : AbstractR2dbcMysqlTest<RepositoryMysqlInsert>(),
    CoroutinesInsertTest<MysqlInts, MysqlLongs, MysqlCustomers, MysqlIntNonNullIds, MysqlLongNonNullIds,
            RepositoryMysqlInsert, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = RepositoryMysqlInsert(sqlClient)
    override val exceptionClass = R2dbcDataIntegrityViolationException::class.java
}

class RepositoryMysqlInsert(sqlClient: R2dbcSqlClient) :
    CoroutinesInsertRepository<MysqlInts, MysqlLongs, MysqlCustomers, MysqlIntNonNullIds, MysqlLongNonNullIds>(
        sqlClient,
        MysqlInts,
        MysqlLongs,
        MysqlCustomers,
        MysqlIntNonNullIds,
        MysqlLongNonNullIds
    )
