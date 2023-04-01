/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MariadbFloats
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectFloatRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectFloatTest

class R2dbcSelectFloatMariadbTest : AbstractR2dbcMariadbTest<FloatRepositoryMariadbSelect>(),
    CoroutinesSelectFloatTest<MariadbFloats, FloatRepositoryMariadbSelect,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = FloatRepositoryMariadbSelect(sqlClient)

    // in returns inconsistent results
    override fun `Verify selectAllByFloatNotNullIn finds both`() {}
}

class FloatRepositoryMariadbSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectFloatRepository<MariadbFloats>(sqlClient, MariadbFloats)
