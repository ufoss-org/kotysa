/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.H2Doubles
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectDoubleRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectDoubleTest

class R2dbcSelectDoubleH2Test : AbstractR2dbcH2Test<DoubleRepositoryH2Select>(),
    CoroutinesSelectDoubleTest<H2Doubles, DoubleRepositoryH2Select, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = DoubleRepositoryH2Select(sqlClient)
}

class DoubleRepositoryH2Select(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectDoubleRepository<H2Doubles>(sqlClient, H2Doubles)
