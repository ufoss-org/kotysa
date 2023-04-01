/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MariadbInheriteds
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.blocking.InheritanceRepository
import org.ufoss.kotysa.test.repositories.blocking.InheritanceTest

class SpringJdbcInheritanceMariadbTest : AbstractSpringJdbcMariadbTest<InheritanceMariadbRepository>(),
    InheritanceTest<MariadbInheriteds, InheritanceMariadbRepository, SpringJdbcTransaction> {
    override val table = MariadbInheriteds

    override fun instantiateRepository(jdbcOperations: JdbcOperations) = InheritanceMariadbRepository(jdbcOperations)
}

class InheritanceMariadbRepository(client: JdbcOperations) :
    InheritanceRepository<MariadbInheriteds>(client.sqlClient(mariadbTables), MariadbInheriteds)
