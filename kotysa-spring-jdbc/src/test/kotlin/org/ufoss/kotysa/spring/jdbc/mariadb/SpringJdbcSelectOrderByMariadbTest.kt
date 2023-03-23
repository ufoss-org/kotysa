/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MariadbCustomers
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.blocking.SelectOrderByRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectOrderByTest

class SpringJdbcSelectOrderByMariadbTest : AbstractSpringJdbcMariadbTest<OrderByRepositoryMariadbSelect>(),
    SelectOrderByTest<MariadbCustomers, OrderByRepositoryMariadbSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) = OrderByRepositoryMariadbSelect(jdbcOperations)
}

class OrderByRepositoryMariadbSelect(client: JdbcOperations) :
    SelectOrderByRepository<MariadbCustomers>(client.sqlClient(mariadbTables), MariadbCustomers)
