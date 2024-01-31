/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.junit.jupiter.api.Order
import org.springframework.dao.DataIntegrityViolationException
import org.ufoss.kotysa.MysqlCoroutinesSqlClient
import org.ufoss.kotysa.MysqlReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.reactor.ReactorInsertRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorInsertTest

@Order(3)
class R2DbcInsertMysqlTest : AbstractR2dbcMysqlTest<MysqlInsertRepository>(),
    ReactorInsertTest<MysqlInts, MysqlLongs, MysqlCustomers, MysqlIntNonNullIds, MysqlLongNonNullIds, MysqlInsertRepository,
            ReactorTransaction> {

    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        MysqlInsertRepository(sqlClient)

    override val exceptionClass = DataIntegrityViolationException::class.java
}

class MysqlInsertRepository(sqlClient: MysqlReactorSqlClient) :
    ReactorInsertRepository<MysqlInts, MysqlLongs, MysqlCustomers, MysqlIntNonNullIds, MysqlLongNonNullIds>(
        sqlClient,
        MysqlInts,
        MysqlLongs,
        MysqlCustomers,
        MysqlIntNonNullIds,
        MysqlLongNonNullIds
    )
