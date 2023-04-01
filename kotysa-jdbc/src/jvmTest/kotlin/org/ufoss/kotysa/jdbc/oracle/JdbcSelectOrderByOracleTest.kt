/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.oracle

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.OracleCustomers
import org.ufoss.kotysa.test.repositories.blocking.SelectOrderByRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectOrderByTest

class JdbcSelectOrderByOracleTest : AbstractJdbcOracleTest<OrderByRepositoryOracleSelect>(),
    SelectOrderByTest<OracleCustomers, OrderByRepositoryOracleSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = OrderByRepositoryOracleSelect(sqlClient)
}

class OrderByRepositoryOracleSelect(sqlClient: JdbcSqlClient) :
    SelectOrderByRepository<OracleCustomers>(sqlClient, OracleCustomers)
