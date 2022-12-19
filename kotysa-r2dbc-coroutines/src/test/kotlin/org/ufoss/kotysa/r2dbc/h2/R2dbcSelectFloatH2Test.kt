/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.H2Floats
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectFloatRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectFloatTest

class R2dbcSelectFloatH2Test : AbstractR2dbcH2Test<FloatRepositoryH2Select>(),
    CoroutinesSelectFloatTest<H2Floats, FloatRepositoryH2Select, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = FloatRepositoryH2Select(sqlClient)
}

class FloatRepositoryH2Select(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectFloatRepository<H2Floats>(sqlClient, H2Floats)
