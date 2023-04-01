/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.oracle

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.OracleInheriteds
import org.ufoss.kotysa.test.oracleTables
import org.ufoss.kotysa.test.repositories.blocking.InheritanceRepository
import org.ufoss.kotysa.test.repositories.blocking.InheritanceTest

class SpringJdbcInheritanceOracleTest : AbstractSpringJdbcOracleTest<InheritanceOracleRepository>(),
    InheritanceTest<OracleInheriteds, InheritanceOracleRepository, SpringJdbcTransaction> {
    override val table = OracleInheriteds

    override fun instantiateRepository(jdbcOperations: JdbcOperations) = InheritanceOracleRepository(jdbcOperations)
}

class InheritanceOracleRepository(client: JdbcOperations) :
    InheritanceRepository<OracleInheriteds>(client.sqlClient(oracleTables), OracleInheriteds)
