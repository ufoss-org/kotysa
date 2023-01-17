/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.oracle

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.OracleInheriteds
import org.ufoss.kotysa.test.repositories.blocking.InheritanceRepository
import org.ufoss.kotysa.test.repositories.blocking.InheritanceTest

class JdbcInheritanceOracleTest : AbstractJdbcOracleTest<InheritanceOracleRepository>(),
    InheritanceTest<OracleInheriteds, InheritanceOracleRepository, JdbcTransaction> {
    override val table = OracleInheriteds
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = InheritanceOracleRepository(sqlClient)
}

class InheritanceOracleRepository(sqlClient: JdbcSqlClient) :
    InheritanceRepository<OracleInheriteds>(sqlClient, OracleInheriteds)
