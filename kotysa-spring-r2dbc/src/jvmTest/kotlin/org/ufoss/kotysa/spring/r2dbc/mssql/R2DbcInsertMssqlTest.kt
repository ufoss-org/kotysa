/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.junit.jupiter.api.Order
import org.springframework.dao.DataIntegrityViolationException
import org.ufoss.kotysa.MssqlCoroutinesSqlClient
import org.ufoss.kotysa.MssqlReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MssqlCustomers
import org.ufoss.kotysa.test.MssqlInts
import org.ufoss.kotysa.test.MssqlLongs
import org.ufoss.kotysa.test.repositories.reactor.ReactorInsertRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorInsertTest

@Order(3)
class R2DbcInsertMssqlTest : AbstractR2dbcMssqlTest<MssqlInsertRepository>(),
    ReactorInsertTest<MssqlInts, MssqlLongs, MssqlCustomers, MssqlInsertRepository, ReactorTransaction> {

    override fun instantiateRepository(sqlClient: MssqlReactorSqlClient, coSqlClient: MssqlCoroutinesSqlClient) =
        MssqlInsertRepository(sqlClient)

    override val exceptionClass = DataIntegrityViolationException::class.java
}

class MssqlInsertRepository(sqlClient: MssqlReactorSqlClient) :
    ReactorInsertRepository<MssqlInts, MssqlLongs, MssqlCustomers>(
        sqlClient,
        MssqlInts,
        MssqlLongs,
        MssqlCustomers
    )
