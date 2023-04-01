/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MariadbCustomers
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.blocking.SelectMinMaxAvgSumRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectMinMaxAvgSumTest

class SpringJdbcSelectMinMaxAvgSumMariadbTest : AbstractSpringJdbcMariadbTest<MinMaxAvgSumRepositoryMariadbSelect>(),
    SelectMinMaxAvgSumTest<MariadbCustomers, MinMaxAvgSumRepositoryMariadbSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        MinMaxAvgSumRepositoryMariadbSelect(jdbcOperations)
}

class MinMaxAvgSumRepositoryMariadbSelect(client: JdbcOperations) :
    SelectMinMaxAvgSumRepository<MariadbCustomers>(client.sqlClient(mariadbTables), MariadbCustomers)
