/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.H2Customers
import org.ufoss.kotysa.test.repositories.blocking.SelectGroupByRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectGroupByTest

class JdbcSelectGroupByH2Test : AbstractJdbcH2Test<GroupByRepositoryH2Select>(),
    SelectGroupByTest<H2Customers, GroupByRepositoryH2Select, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = GroupByRepositoryH2Select(sqlClient)
}

class GroupByRepositoryH2Select(sqlClient: JdbcSqlClient) : SelectGroupByRepository<H2Customers>(sqlClient, H2Customers)
