/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.oracle

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.OracleCustomers
import org.ufoss.kotysa.test.repositories.blocking.SelectGroupByRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectGroupByTest

class JdbcSelectGroupByOracleTest : AbstractJdbcOracleTest<GroupByRepositoryOracleSelect>(),
    SelectGroupByTest<OracleCustomers, GroupByRepositoryOracleSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = GroupByRepositoryOracleSelect(sqlClient)
}

class GroupByRepositoryOracleSelect(sqlClient: JdbcSqlClient) :
    SelectGroupByRepository<OracleCustomers>(sqlClient, OracleCustomers)
