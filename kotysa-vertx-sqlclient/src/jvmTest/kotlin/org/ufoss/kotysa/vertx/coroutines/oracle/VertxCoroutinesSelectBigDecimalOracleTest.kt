/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.oracle

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.OracleBigDecimals
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalTest

class VertxCoroutinesSelectBigDecimalOracleTest : AbstractVertxCoroutinesOracleTest<SelectBigDecimalOracleRepository>(),
    CoroutinesSelectBigDecimalTest<OracleBigDecimals, SelectBigDecimalOracleRepository, Transaction> {

    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = SelectBigDecimalOracleRepository(sqlClient)
}


class SelectBigDecimalOracleRepository(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectBigDecimalRepository<OracleBigDecimals>(sqlClient, OracleBigDecimals)
