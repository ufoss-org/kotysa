/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.ufoss.kotysa.OracleCoroutinesSqlClient
import org.ufoss.kotysa.OracleReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.OracleBigDecimals
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalTest

class JdbcSelectBigDecimalOracleTest : AbstractR2dbcOracleTest<ReactorSelectBigDecimalRepositoryOracleSelect>(),
    ReactorSelectBigDecimalTest<OracleBigDecimals, ReactorSelectBigDecimalRepositoryOracleSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: OracleReactorSqlClient, coSqlClient: OracleCoroutinesSqlClient) =
        ReactorSelectBigDecimalRepositoryOracleSelect(sqlClient)
}

class ReactorSelectBigDecimalRepositoryOracleSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectBigDecimalRepository<OracleBigDecimals>(sqlClient, OracleBigDecimals)
