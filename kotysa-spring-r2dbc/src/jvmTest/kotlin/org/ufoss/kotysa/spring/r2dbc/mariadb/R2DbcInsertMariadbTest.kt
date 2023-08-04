/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.junit.jupiter.api.Order
import org.springframework.dao.DataIntegrityViolationException
import org.ufoss.kotysa.MariadbCoroutinesSqlClient
import org.ufoss.kotysa.MariadbReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MariadbCustomers
import org.ufoss.kotysa.test.MariadbInts
import org.ufoss.kotysa.test.MariadbLongs
import org.ufoss.kotysa.test.repositories.reactor.ReactorInsertRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorInsertTest

@Order(3)
class R2DbcInsertMariadbTest : AbstractR2dbcMariadbTest<MariadbInsertRepository>(),
    ReactorInsertTest<MariadbInts, MariadbLongs, MariadbCustomers, MariadbInsertRepository, ReactorTransaction> {

    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        MariadbInsertRepository(sqlClient)

    override val exceptionClass = DataIntegrityViolationException::class.java
}

class MariadbInsertRepository(sqlClient: MariadbReactorSqlClient) :
    ReactorInsertRepository<MariadbInts, MariadbLongs, MariadbCustomers>(
        sqlClient,
        MariadbInts,
        MariadbLongs,
        MariadbCustomers
    )
