/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.H2Inheriteds
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInheritanceRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInheritanceTest

class R2dbcInheritanceH2Test : AbstractR2dbcH2Test<InheritanceH2Repository>(),
    CoroutinesInheritanceTest<H2Inheriteds, InheritanceH2Repository, R2dbcTransaction> {
    override val table = H2Inheriteds
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = InheritanceH2Repository(sqlClient)
}

class InheritanceH2Repository(sqlClient: R2dbcSqlClient) :
    CoroutinesInheritanceRepository<H2Inheriteds>(sqlClient, H2Inheriteds)
