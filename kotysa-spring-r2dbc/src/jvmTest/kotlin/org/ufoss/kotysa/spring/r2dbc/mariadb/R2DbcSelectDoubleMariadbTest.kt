/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.ufoss.kotysa.MariadbCoroutinesSqlClient
import org.ufoss.kotysa.MariadbReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MariadbDoubles
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectDoubleRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectDoubleTest

class R2DbcSelectDoubleMariadbTest : AbstractR2dbcMariadbTest<DoubleMariadbRepository>(),
    ReactorSelectDoubleTest<MariadbDoubles, DoubleMariadbRepository, ReactorTransaction> {

    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        DoubleMariadbRepository(sqlClient)
}

class DoubleMariadbRepository(sqlClient: MariadbReactorSqlClient) :
    ReactorSelectDoubleRepository<MariadbDoubles>(sqlClient, MariadbDoubles)
