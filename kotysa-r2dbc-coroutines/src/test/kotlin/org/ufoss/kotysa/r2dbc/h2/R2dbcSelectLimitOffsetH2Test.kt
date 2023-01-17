/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.H2Customers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLimitOffsetRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLimitOffsetTest

class R2dbcSelectLimitOffsetH2Test : AbstractR2dbcH2Test<LimitOffsetRepositoryH2Select>(),
    CoroutinesSelectLimitOffsetTest<H2Customers, LimitOffsetRepositoryH2Select, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = LimitOffsetRepositoryH2Select(sqlClient)
}

class LimitOffsetRepositoryH2Select(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectLimitOffsetRepository<H2Customers>(sqlClient, H2Customers)
