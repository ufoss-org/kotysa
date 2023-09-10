/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.oracle

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.OracleDoubles
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectDoubleRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectDoubleTest

class VertxCoroutinesSelectDoubleOracleTest : AbstractVertxCoroutinesOracleTest<SelectDoubleOracleRepository>(),
    CoroutinesSelectDoubleTest<OracleDoubles, SelectDoubleOracleRepository, Transaction> {

    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = SelectDoubleOracleRepository(sqlClient)
}


class SelectDoubleOracleRepository(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectDoubleRepository<OracleDoubles>(sqlClient, OracleDoubles)
