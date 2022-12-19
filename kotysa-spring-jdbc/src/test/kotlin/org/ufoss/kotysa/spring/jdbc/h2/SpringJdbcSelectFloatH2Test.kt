/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.H2Floats
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.blocking.SelectFloatRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectFloatTest

class SpringJdbcSelectFloatH2Test : AbstractSpringJdbcH2Test<FloatRepositoryH2Select>(),
    SelectFloatTest<H2Floats, FloatRepositoryH2Select, SpringJdbcTransaction> {
    override val context = startContext<FloatRepositoryH2Select>()
    override val repository = getContextRepository<FloatRepositoryH2Select>()
}

class FloatRepositoryH2Select(client: JdbcOperations) :
    SelectFloatRepository<H2Floats>(client.sqlClient(h2Tables), H2Floats)
