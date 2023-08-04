/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.MariadbCoroutinesSqlClient
import org.ufoss.kotysa.MariadbReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MariadbInts
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectIntRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectIntTest

@Order(1)
class R2dbcSelectIntMariadbTest : AbstractR2dbcMariadbTest<ReactorSelectIntRepositoryMariadbSelect>(),
    ReactorSelectIntTest<MariadbInts, ReactorSelectIntRepositoryMariadbSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        ReactorSelectIntRepositoryMariadbSelect(sqlClient)
}

class ReactorSelectIntRepositoryMariadbSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectIntRepository<MariadbInts>(sqlClient, MariadbInts)
