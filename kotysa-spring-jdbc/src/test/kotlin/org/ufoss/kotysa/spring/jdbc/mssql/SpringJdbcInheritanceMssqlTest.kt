/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MssqlInheriteds
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.blocking.InheritanceRepository
import org.ufoss.kotysa.test.repositories.blocking.InheritanceTest

class SpringJdbcInheritanceMssqlTest : AbstractSpringJdbcMssqlTest<InheritanceMssqlRepository>(),
    InheritanceTest<MssqlInheriteds, InheritanceMssqlRepository, SpringJdbcTransaction> {
    override val table = MssqlInheriteds

    override fun instantiateRepository(jdbcOperations: JdbcOperations) = InheritanceMssqlRepository(jdbcOperations)
}

class InheritanceMssqlRepository(client: JdbcOperations) :
    InheritanceRepository<MssqlInheriteds>(client.sqlClient(mssqlTables), MssqlInheriteds)
