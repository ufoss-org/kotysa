/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.ufoss.kotysa.MariadbCoroutinesSqlClient
import org.ufoss.kotysa.MariadbReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MariadbCustomers
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLimitOffsetRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLimitOffsetTest

class R2dbcSelectLimitOffsetMariadbTest : AbstractR2dbcMariadbTest<LimitOffsetRepositoryMariadbSelect>(),
    ReactorSelectLimitOffsetTest<MariadbCustomers, LimitOffsetRepositoryMariadbSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        LimitOffsetRepositoryMariadbSelect(sqlClient)
}

class LimitOffsetRepositoryMariadbSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectLimitOffsetRepository<MariadbCustomers>(sqlClient, MariadbCustomers)
