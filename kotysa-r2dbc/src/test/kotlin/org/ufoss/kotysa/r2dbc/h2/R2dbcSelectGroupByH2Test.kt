/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.H2Customers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectGroupByRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectGroupByTest

class R2dbcSelectGroupByH2Test : AbstractR2dbcH2Test<GroupByRepositoryH2Select>(),
    CoroutinesSelectGroupByTest<H2Customers, GroupByRepositoryH2Select, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = GroupByRepositoryH2Select(sqlClient)
}

class GroupByRepositoryH2Select(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectGroupByRepository<H2Customers>(sqlClient, H2Customers)
