/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.OracleCoroutinesSqlClient
import org.ufoss.kotysa.OracleReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectIntRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectIntTest

@Order(1)
class R2dbcSelectIntOracleTest : AbstractR2dbcOracleTest<ReactorSelectIntRepositoryOracleSelect>(),
    ReactorSelectIntTest<OracleInts, ReactorSelectIntRepositoryOracleSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: OracleReactorSqlClient, coSqlClient: OracleCoroutinesSqlClient) =
        ReactorSelectIntRepositoryOracleSelect(sqlClient)
}

class ReactorSelectIntRepositoryOracleSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectIntRepository<OracleInts>(sqlClient, OracleInts)
