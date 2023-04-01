/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import io.r2dbc.spi.R2dbcDataIntegrityViolationException
import org.junit.jupiter.api.Order
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInsertRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInsertTest

@Order(3)
class R2dbcInsertH2Test : AbstractR2dbcH2Test<RepositoryH2Insert>(),
    CoroutinesInsertTest<H2Ints, H2Longs, H2Customers, RepositoryH2Insert, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = RepositoryH2Insert(sqlClient)
    override val exceptionClass = R2dbcDataIntegrityViolationException::class.java
}

class RepositoryH2Insert(sqlClient: R2dbcSqlClient) :
    CoroutinesInsertRepository<H2Ints, H2Longs, H2Customers>(sqlClient, H2Ints, H2Longs, H2Customers)
