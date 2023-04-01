/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MariadbCustomers
import org.ufoss.kotysa.test.repositories.blocking.SelectGroupByRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectGroupByTest

class JdbcSelectGroupByMariadbTest : AbstractJdbcMariadbTest<GroupByRepositoryMariadbSelect>(),
    SelectGroupByTest<MariadbCustomers, GroupByRepositoryMariadbSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = GroupByRepositoryMariadbSelect(sqlClient)
}

class GroupByRepositoryMariadbSelect(sqlClient: JdbcSqlClient) :
    SelectGroupByRepository<MariadbCustomers>(sqlClient, MariadbCustomers)
