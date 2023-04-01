/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.oracle

import org.ufoss.kotysa.test.OracleInheriteds
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinyInheritanceRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinyInheritanceTest

class JdbcInheritanceOracleTest : AbstractVertxSqlClientOracleTest<InheritanceOracleRepository>(),
    MutinyInheritanceTest<OracleInheriteds, InheritanceOracleRepository> {
    override val table = OracleInheriteds
    override fun instantiateRepository(sqlClient: VertxSqlClient) = InheritanceOracleRepository(sqlClient)
}

class InheritanceOracleRepository(sqlClient: VertxSqlClient) :
    MutinyInheritanceRepository<OracleInheriteds>(sqlClient, OracleInheriteds)
