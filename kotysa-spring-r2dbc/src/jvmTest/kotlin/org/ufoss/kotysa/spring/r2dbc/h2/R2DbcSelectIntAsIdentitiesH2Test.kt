/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.H2CoroutinesSqlClient
import org.ufoss.kotysa.H2ReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectIntAsIdentitiesRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectIntAsIdentitiesTest

@Order(1)
class R2DbcSelectIntAsIdentitiesH2Test : AbstractR2dbcH2Test<ReactorSelectIntRepositoryH2Select>(),
    ReactorSelectIntAsIdentitiesTest<H2IntAsIdentities, ReactorSelectIntRepositoryH2Select, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        ReactorSelectIntRepositoryH2Select(sqlClient)
}

class ReactorSelectIntRepositoryH2Select(sqlClient: ReactorSqlClient) :
    ReactorSelectIntAsIdentitiesRepository<H2IntAsIdentities>(sqlClient, H2IntAsIdentities)
