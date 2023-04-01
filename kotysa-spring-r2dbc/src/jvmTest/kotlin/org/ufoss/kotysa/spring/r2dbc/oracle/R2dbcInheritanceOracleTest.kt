/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.ufoss.kotysa.OracleCoroutinesSqlClient
import org.ufoss.kotysa.OracleReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.OracleInheriteds
import org.ufoss.kotysa.test.repositories.reactor.ReactorInheritanceRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorInheritanceTest

class JdbcInheritanceOracleTest : AbstractR2dbcOracleTest<ReactorInheritanceOracleRepository>(),
    ReactorInheritanceTest<OracleInheriteds, ReactorInheritanceOracleRepository, ReactorTransaction> {
    override val table = OracleInheriteds

    override fun instantiateRepository(sqlClient: OracleReactorSqlClient, coSqlClient: OracleCoroutinesSqlClient) =
        ReactorInheritanceOracleRepository(sqlClient)
}

class ReactorInheritanceOracleRepository(sqlClient: ReactorSqlClient) :
    ReactorInheritanceRepository<OracleInheriteds>(sqlClient, OracleInheriteds)
