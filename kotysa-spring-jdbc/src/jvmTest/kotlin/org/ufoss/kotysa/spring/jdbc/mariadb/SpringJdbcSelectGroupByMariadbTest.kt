/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MariadbCustomers
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.blocking.SelectGroupByRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectGroupByTest

class SpringJdbcSelectGroupByMariadbTest : AbstractSpringJdbcMariadbTest<GroupByRepositoryMariadbSelect>(),
    SelectGroupByTest<MariadbCustomers, GroupByRepositoryMariadbSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        GroupByRepositoryMariadbSelect(jdbcOperations)
}

class GroupByRepositoryMariadbSelect(client: JdbcOperations) :
    SelectGroupByRepository<MariadbCustomers>(client.sqlClient(mariadbTables), MariadbCustomers)
