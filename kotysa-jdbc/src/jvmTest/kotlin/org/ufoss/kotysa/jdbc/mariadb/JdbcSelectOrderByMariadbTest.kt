/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MariadbCustomers
import org.ufoss.kotysa.test.repositories.blocking.SelectOrderByRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectOrderByTest

class JdbcSelectOrderByMariadbTest : AbstractJdbcMariadbTest<OrderByRepositoryMariadbSelect>(),
    SelectOrderByTest<MariadbCustomers, OrderByRepositoryMariadbSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = OrderByRepositoryMariadbSelect(sqlClient)
}

class OrderByRepositoryMariadbSelect(sqlClient: JdbcSqlClient) :
    SelectOrderByRepository<MariadbCustomers>(sqlClient, MariadbCustomers)
