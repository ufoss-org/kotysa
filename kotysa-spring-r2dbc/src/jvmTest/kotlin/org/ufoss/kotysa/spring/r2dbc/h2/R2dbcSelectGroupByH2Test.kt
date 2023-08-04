/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.ufoss.kotysa.H2CoroutinesSqlClient
import org.ufoss.kotysa.H2ReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.H2Customers
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectGroupByRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectGroupByTest

class R2dbcSelectGroupByH2Test : AbstractR2dbcH2Test<GroupByRepositoryH2Select>(),
    ReactorSelectGroupByTest<H2Customers, GroupByRepositoryH2Select, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        GroupByRepositoryH2Select(sqlClient)
}

class GroupByRepositoryH2Select(sqlClient: ReactorSqlClient) :
    ReactorSelectGroupByRepository<H2Customers>(sqlClient, H2Customers)
