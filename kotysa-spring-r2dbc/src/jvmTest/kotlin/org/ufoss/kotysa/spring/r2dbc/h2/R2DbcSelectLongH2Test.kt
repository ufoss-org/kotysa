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
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLongRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLongTest

@Order(2)
class R2dbcSelectLongH2Test : AbstractR2dbcH2Test<ReactorLongRepositoryH2Select>(),
    ReactorSelectLongTest<H2Longs, ReactorLongRepositoryH2Select, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        ReactorLongRepositoryH2Select(sqlClient)
}

class ReactorLongRepositoryH2Select(sqlClient: ReactorSqlClient) :
    ReactorSelectLongRepository<H2Longs>(sqlClient, H2Longs)
