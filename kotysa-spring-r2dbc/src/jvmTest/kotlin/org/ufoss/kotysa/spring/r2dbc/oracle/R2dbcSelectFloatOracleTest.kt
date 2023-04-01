/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.ufoss.kotysa.OracleCoroutinesSqlClient
import org.ufoss.kotysa.OracleReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.OracleFloats
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectFloatRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectFloatTest

class JdbcSelectFloatOracleTest : AbstractR2dbcOracleTest<ReactorSelectFloatRepositoryOracleSelect>(),
    ReactorSelectFloatTest<OracleFloats, ReactorSelectFloatRepositoryOracleSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: OracleReactorSqlClient, coSqlClient: OracleCoroutinesSqlClient) =
        ReactorSelectFloatRepositoryOracleSelect(sqlClient)
}

class ReactorSelectFloatRepositoryOracleSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectFloatRepository<OracleFloats>(sqlClient, OracleFloats)
