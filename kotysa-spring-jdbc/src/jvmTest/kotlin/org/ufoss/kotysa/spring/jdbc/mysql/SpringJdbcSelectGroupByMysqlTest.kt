/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MysqlCustomers
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectGroupByRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectGroupByTest

class SpringJdbcSelectGroupByMysqlTest : AbstractSpringJdbcMysqlTest<GroupByRepositoryMysqlSelect>(),
    SelectGroupByTest<MysqlCustomers, GroupByRepositoryMysqlSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) = GroupByRepositoryMysqlSelect(jdbcOperations)
}

class GroupByRepositoryMysqlSelect(client: JdbcOperations) :
    SelectGroupByRepository<MysqlCustomers>(client.sqlClient(mysqlTables), MysqlCustomers)
