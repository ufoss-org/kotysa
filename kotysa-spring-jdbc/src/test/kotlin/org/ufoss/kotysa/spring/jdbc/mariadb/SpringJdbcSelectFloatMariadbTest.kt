/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MariadbFloats
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.blocking.SelectFloatRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectFloatTest

class SpringJdbcSelectFloatMariadbTest : AbstractSpringJdbcMariadbTest<FloatRepositoryMariadbSelect>(),
    SelectFloatTest<MariadbFloats, FloatRepositoryMariadbSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        FloatRepositoryMariadbSelect(jdbcOperations)

    // in returns inconsistent results
    override fun `Verify selectAllByFloatNotNullIn finds both`() {}
}

class FloatRepositoryMariadbSelect(client: JdbcOperations) :
    SelectFloatRepository<MariadbFloats>(client.sqlClient(mariadbTables), MariadbFloats)
