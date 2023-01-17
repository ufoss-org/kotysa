/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.oracle

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.OracleInheriteds
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInheritanceRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInheritanceTest

class R2dbcInheritanceOracleTest : AbstractR2dbcOracleTest<InheritanceOracleRepository>(),
    CoroutinesInheritanceTest<OracleInheriteds, InheritanceOracleRepository, R2dbcTransaction> {
    override val table = OracleInheriteds
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = InheritanceOracleRepository(sqlClient)
}

class InheritanceOracleRepository(sqlClient: R2dbcSqlClient) :
    CoroutinesInheritanceRepository<OracleInheriteds>(sqlClient, OracleInheriteds)
