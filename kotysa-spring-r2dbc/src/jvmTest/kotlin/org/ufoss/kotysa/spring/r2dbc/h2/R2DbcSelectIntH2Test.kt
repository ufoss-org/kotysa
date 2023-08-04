/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.H2CoroutinesSqlClient
import org.ufoss.kotysa.H2ReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.H2Ints
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectIntRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectIntTest

@Order(1)
class R2dbcSelectIntH2Test : AbstractR2dbcH2Test<ReactorSelectIntRepositoryH2Select>(),
    ReactorSelectIntTest<H2Ints, ReactorSelectIntRepositoryH2Select, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        ReactorSelectIntRepositoryH2Select(sqlClient)
}

class ReactorSelectIntRepositoryH2Select(sqlClient: ReactorSqlClient) :
    ReactorSelectIntRepository<H2Ints>(sqlClient, H2Ints)
