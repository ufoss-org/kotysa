/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.oracle

import org.ufoss.kotysa.test.OracleInheriteds
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinyInheritanceRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinyInheritanceTest

class JdbcInheritanceOracleTest : AbstractVertxSqlClientOracleTest<InheritanceOracleRepository>(),
    MutinyInheritanceTest<OracleInheriteds, InheritanceOracleRepository> {
    override val table = OracleInheriteds
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = InheritanceOracleRepository(sqlClient)
}

class InheritanceOracleRepository(sqlClient: MutinyVertxSqlClient) :
    MutinyInheritanceRepository<OracleInheriteds>(sqlClient, OracleInheriteds)
