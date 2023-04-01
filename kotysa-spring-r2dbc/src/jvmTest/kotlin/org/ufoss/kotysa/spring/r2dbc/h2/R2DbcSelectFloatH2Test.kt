/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.ufoss.kotysa.H2CoroutinesSqlClient
import org.ufoss.kotysa.H2ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.H2Floats
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectFloatRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectFloatTest

class R2DbcSelectFloatH2Test : AbstractR2dbcH2Test<FloatH2Repository>(),
    ReactorSelectFloatTest<H2Floats, FloatH2Repository, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        FloatH2Repository(sqlClient)
}

class FloatH2Repository(sqlClient: H2ReactorSqlClient) :
    ReactorSelectFloatRepository<H2Floats>(sqlClient, H2Floats)
