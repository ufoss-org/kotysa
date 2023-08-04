/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.MariadbCoroutinesSqlClient
import org.ufoss.kotysa.MariadbReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLongRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLongTest

@Order(2)
class R2dbcSelectLongMariadbTest : AbstractR2dbcMariadbTest<ReactorLongRepositoryMariadbSelect>(),
    ReactorSelectLongTest<MariadbLongs, ReactorLongRepositoryMariadbSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        ReactorLongRepositoryMariadbSelect(sqlClient)
}

class ReactorLongRepositoryMariadbSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectLongRepository<MariadbLongs>(sqlClient, MariadbLongs)
