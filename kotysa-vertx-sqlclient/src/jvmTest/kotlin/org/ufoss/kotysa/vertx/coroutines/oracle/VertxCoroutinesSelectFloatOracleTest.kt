/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.oracle

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.OracleFloats
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectFloatRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectFloatTest

class VertxCoroutinesSelectFloatOracleTest : AbstractVertxCoroutinesOracleTest<SelectFloatOracleRepository>(),
    CoroutinesSelectFloatTest<OracleFloats, SelectFloatOracleRepository, Transaction> {

    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = SelectFloatOracleRepository(sqlClient)
}


class SelectFloatOracleRepository(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectFloatRepository<OracleFloats>(sqlClient, OracleFloats)
