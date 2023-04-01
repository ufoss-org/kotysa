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
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLongRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLongTest

@Order(2)
class JdbcSelectLongOracleTest : AbstractR2dbcOracleTest<ReactorLongRepositoryOracleSelect>(),
    ReactorSelectLongTest<OracleLongs, ReactorLongRepositoryOracleSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: OracleReactorSqlClient, coSqlClient: OracleCoroutinesSqlClient) =
        ReactorLongRepositoryOracleSelect(sqlClient)
}

class ReactorLongRepositoryOracleSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectLongRepository<OracleLongs>(sqlClient, OracleLongs)
