/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.ufoss.kotysa.MariadbCoroutinesSqlClient
import org.ufoss.kotysa.MariadbReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MariadbCustomers
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectGroupByRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectGroupByTest

class R2dbcSelectGroupByMariadbTest : AbstractR2dbcMariadbTest<GroupByRepositoryMariadbSelect>(),
    ReactorSelectGroupByTest<MariadbCustomers, GroupByRepositoryMariadbSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        GroupByRepositoryMariadbSelect(sqlClient)
}

class GroupByRepositoryMariadbSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectGroupByRepository<MariadbCustomers>(sqlClient, MariadbCustomers)
