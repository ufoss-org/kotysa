/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.junit.jupiter.api.Order
import org.springframework.dao.DataIntegrityViolationException
import org.ufoss.kotysa.H2CoroutinesSqlClient
import org.ufoss.kotysa.H2ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.H2Customers
import org.ufoss.kotysa.test.H2Ints
import org.ufoss.kotysa.test.H2Longs
import org.ufoss.kotysa.test.repositories.reactor.ReactorInsertRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorInsertTest

@Order(3)
class R2DbcInsertH2Test : AbstractR2dbcH2Test<H2InsertRepository>(),
    ReactorInsertTest<H2Ints, H2Longs, H2Customers, H2InsertRepository, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        H2InsertRepository(sqlClient)

    override val exceptionClass = DataIntegrityViolationException::class.java
}

class H2InsertRepository(sqlClient: H2ReactorSqlClient) :
    ReactorInsertRepository<H2Ints, H2Longs, H2Customers>(
        sqlClient,
        H2Ints,
        H2Longs,
        H2Customers
    )
