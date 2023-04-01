/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MysqlCustomers
import org.ufoss.kotysa.test.repositories.blocking.SelectOrderByRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectOrderByTest

class JdbcSelectOrderByMysqlTest : AbstractJdbcMysqlTest<OrderByRepositoryMysqlSelect>(),
    SelectOrderByTest<MysqlCustomers, OrderByRepositoryMysqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = OrderByRepositoryMysqlSelect(sqlClient)
}

class OrderByRepositoryMysqlSelect(sqlClient: JdbcSqlClient) :
    SelectOrderByRepository<MysqlCustomers>(sqlClient, MysqlCustomers)
