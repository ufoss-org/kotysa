/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MariadbDoubles
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectDoubleRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectDoubleTest

class R2dbcSelectDoubleMariadbTest : AbstractR2dbcMariadbTest<DoubleRepositoryMariadbSelect>(),
    CoroutinesSelectDoubleTest<MariadbDoubles, DoubleRepositoryMariadbSelect,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = DoubleRepositoryMariadbSelect(sqlClient)
}

class DoubleRepositoryMariadbSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectDoubleRepository<MariadbDoubles>(sqlClient, MariadbDoubles)
