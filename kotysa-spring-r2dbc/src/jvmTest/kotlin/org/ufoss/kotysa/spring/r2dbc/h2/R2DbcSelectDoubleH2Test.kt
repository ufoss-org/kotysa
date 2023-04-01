/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.ufoss.kotysa.H2CoroutinesSqlClient
import org.ufoss.kotysa.H2ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.H2Doubles
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectDoubleRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectDoubleTest

class R2DbcSelectDoubleH2Test : AbstractR2dbcH2Test<DoubleH2Repository>(),
    ReactorSelectDoubleTest<H2Doubles, DoubleH2Repository, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        DoubleH2Repository(sqlClient)
}

class DoubleH2Repository(sqlClient: H2ReactorSqlClient) :
    ReactorSelectDoubleRepository<H2Doubles>(sqlClient, H2Doubles)
