/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MariadbCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLimitOffsetRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLimitOffsetTest

class R2dbcSelectLimitOffsetMariadbTest : AbstractR2dbcMariadbTest<LimitOffsetRepositoryMariadbSelect>(),
    CoroutinesSelectLimitOffsetTest<MariadbCustomers, LimitOffsetRepositoryMariadbSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = LimitOffsetRepositoryMariadbSelect(sqlClient)
}

class LimitOffsetRepositoryMariadbSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectLimitOffsetRepository<MariadbCustomers>(sqlClient, MariadbCustomers)
