/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.ufoss.kotysa.MariadbCoroutinesSqlClient
import org.ufoss.kotysa.MariadbReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MariadbInheriteds
import org.ufoss.kotysa.test.repositories.reactor.ReactorInheritanceRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorInheritanceTest

class R2DbcInheritanceMariadbTest : AbstractR2dbcMariadbTest<ReactorInheritanceMariadbRepository>(),
    ReactorInheritanceTest<MariadbInheriteds, ReactorInheritanceMariadbRepository, ReactorTransaction> {
    override val table = MariadbInheriteds

    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        ReactorInheritanceMariadbRepository(sqlClient)
}

class ReactorInheritanceMariadbRepository(sqlClient: MariadbReactorSqlClient) :
    ReactorInheritanceRepository<MariadbInheriteds>(sqlClient, MariadbInheriteds)
