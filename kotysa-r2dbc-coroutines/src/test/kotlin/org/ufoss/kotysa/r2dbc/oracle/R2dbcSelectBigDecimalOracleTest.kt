/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.oracle

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.OracleBigDecimals
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalTest

class R2dbcSelectBigDecimalOracleTest : AbstractR2dbcOracleTest<SelectBigDecimalOracleRepository>(),
    CoroutinesSelectBigDecimalTest<OracleBigDecimals, SelectBigDecimalOracleRepository, R2dbcTransaction> {

    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = SelectBigDecimalOracleRepository(sqlClient)
}


class SelectBigDecimalOracleRepository(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectBigDecimalRepository<OracleBigDecimals>(sqlClient, OracleBigDecimals)
