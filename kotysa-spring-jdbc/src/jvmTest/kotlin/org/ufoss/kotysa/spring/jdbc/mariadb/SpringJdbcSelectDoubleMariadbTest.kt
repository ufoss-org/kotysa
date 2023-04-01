/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MariadbDoubles
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.blocking.SelectDoubleRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectDoubleTest

class SpringJdbcSelectDoubleMariadbTest : AbstractSpringJdbcMariadbTest<DoubleRepositoryMariadbSelect>(),
    SelectDoubleTest<MariadbDoubles, DoubleRepositoryMariadbSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) = DoubleRepositoryMariadbSelect(jdbcOperations)
}

class DoubleRepositoryMariadbSelect(client: JdbcOperations) :
    SelectDoubleRepository<MariadbDoubles>(client.sqlClient(mariadbTables), MariadbDoubles)
