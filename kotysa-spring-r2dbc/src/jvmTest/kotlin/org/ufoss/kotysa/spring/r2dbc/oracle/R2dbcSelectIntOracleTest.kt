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
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectIntAsIdentitiesRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectIntAsIdentitiesTest

@Order(1)
class R2dbcSelectIntOracleTest : AbstractR2dbcOracleTest<ReactorSelectIntRepositoryOracleSelect>(),
    ReactorSelectIntAsIdentitiesTest<OracleIntAsIdentities, ReactorSelectIntRepositoryOracleSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: OracleReactorSqlClient, coSqlClient: OracleCoroutinesSqlClient) =
        ReactorSelectIntRepositoryOracleSelect(sqlClient)
}

class ReactorSelectIntRepositoryOracleSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectIntAsIdentitiesRepository<OracleIntAsIdentities>(sqlClient, OracleIntAsIdentities)
