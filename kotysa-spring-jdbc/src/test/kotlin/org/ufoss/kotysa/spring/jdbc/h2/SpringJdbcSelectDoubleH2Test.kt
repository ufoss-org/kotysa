/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.H2Doubles
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.blocking.SelectDoubleRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectDoubleTest

class SpringJdbcSelectDoubleH2Test : AbstractSpringJdbcH2Test<DoubleRepositoryH2Select>(),
    SelectDoubleTest<H2Doubles, DoubleRepositoryH2Select, SpringJdbcTransaction> {
    override val context = startContext<DoubleRepositoryH2Select>()
    override val repository = getContextRepository<DoubleRepositoryH2Select>()
}

class DoubleRepositoryH2Select(client: JdbcOperations) :
    SelectDoubleRepository<H2Doubles>(client.sqlClient(h2Tables), H2Doubles)
