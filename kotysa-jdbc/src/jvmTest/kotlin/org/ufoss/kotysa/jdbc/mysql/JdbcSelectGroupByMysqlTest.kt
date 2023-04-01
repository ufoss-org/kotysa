/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MysqlCustomers
import org.ufoss.kotysa.test.repositories.blocking.SelectGroupByRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectGroupByTest

class JdbcSelectGroupByMysqlTest : AbstractJdbcMysqlTest<GroupByRepositoryMysqlSelect>(),
    SelectGroupByTest<MysqlCustomers, GroupByRepositoryMysqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = GroupByRepositoryMysqlSelect(sqlClient)
}

class GroupByRepositoryMysqlSelect(sqlClient: JdbcSqlClient) :
    SelectGroupByRepository<MysqlCustomers>(sqlClient, MysqlCustomers)
