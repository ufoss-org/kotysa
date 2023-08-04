/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.ufoss.kotysa.H2CoroutinesSqlClient
import org.ufoss.kotysa.H2ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.H2Inheriteds
import org.ufoss.kotysa.test.repositories.reactor.ReactorInheritanceRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorInheritanceTest

class R2DbcInheritanceH2Test : AbstractR2dbcH2Test<ReactorInheritanceH2Repository>(),
    ReactorInheritanceTest<H2Inheriteds, ReactorInheritanceH2Repository, ReactorTransaction> {
    override val table = H2Inheriteds
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        ReactorInheritanceH2Repository(sqlClient)
}

class ReactorInheritanceH2Repository(sqlClient: H2ReactorSqlClient) :
    ReactorInheritanceRepository<H2Inheriteds>(sqlClient, H2Inheriteds)
