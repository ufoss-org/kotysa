/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.oracle

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.OracleInheriteds
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInheritanceRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInheritanceTest

class VertxCoroutinesInheritanceOracleTest : AbstractVertxCoroutinesOracleTest<InheritanceOracleRepository>(),
    CoroutinesInheritanceTest<OracleInheriteds, InheritanceOracleRepository, Transaction> {
    override val table = OracleInheriteds
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = InheritanceOracleRepository(sqlClient)
}

class InheritanceOracleRepository(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesInheritanceRepository<OracleInheriteds>(sqlClient, OracleInheriteds)
