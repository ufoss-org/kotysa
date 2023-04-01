/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.ufoss.kotysa.OracleCoroutinesSqlClient
import org.ufoss.kotysa.OracleReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.OracleDoubles
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectDoubleRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectDoubleTest

class JdbcSelectDoubleOracleTest : AbstractR2dbcOracleTest<ReactorSelectDoubleRepositoryOracleSelect>(),
    ReactorSelectDoubleTest<OracleDoubles, ReactorSelectDoubleRepositoryOracleSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: OracleReactorSqlClient, coSqlClient: OracleCoroutinesSqlClient) =
        ReactorSelectDoubleRepositoryOracleSelect(sqlClient)
}

class ReactorSelectDoubleRepositoryOracleSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectDoubleRepository<OracleDoubles>(sqlClient, OracleDoubles)
