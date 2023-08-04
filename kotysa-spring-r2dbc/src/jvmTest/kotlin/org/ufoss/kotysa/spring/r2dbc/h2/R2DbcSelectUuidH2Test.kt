/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.ufoss.kotysa.H2CoroutinesSqlClient
import org.ufoss.kotysa.H2ReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.H2Uuids
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectUuidRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectUuidTest

class R2DbcSelectUuidH2Test : AbstractR2dbcH2Test<UuidRepositoryH2Select>(),
    ReactorSelectUuidTest<H2Uuids, UuidRepositoryH2Select, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        UuidRepositoryH2Select(sqlClient)
}

class UuidRepositoryH2Select(sqlClient: ReactorSqlClient) :
    ReactorSelectUuidRepository<H2Uuids>(sqlClient, H2Uuids)
