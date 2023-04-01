/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.H2Inheriteds
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.blocking.InheritanceRepository
import org.ufoss.kotysa.test.repositories.blocking.InheritanceTest

class SpringJdbcInheritanceH2Test : AbstractSpringJdbcH2Test<InheritanceH2Repository>(),
    InheritanceTest<H2Inheriteds, InheritanceH2Repository, SpringJdbcTransaction> {
    override val table = H2Inheriteds
    override fun instantiateRepository(jdbcOperations: JdbcOperations) = InheritanceH2Repository(jdbcOperations)
}

class InheritanceH2Repository(client: JdbcOperations) :
    InheritanceRepository<H2Inheriteds>(client.sqlClient(h2Tables), H2Inheriteds)
