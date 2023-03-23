/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.oracle

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.OracleCustomers
import org.ufoss.kotysa.test.oracleTables
import org.ufoss.kotysa.test.repositories.blocking.SelectOrderByRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectOrderByTest

class SpringJdbcSelectOrderByOracleTest : AbstractSpringJdbcOracleTest<OrderByRepositoryOracleSelect>(),
    SelectOrderByTest<OracleCustomers, OrderByRepositoryOracleSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) = OrderByRepositoryOracleSelect(jdbcOperations)
}

class OrderByRepositoryOracleSelect(client: JdbcOperations) :
    SelectOrderByRepository<OracleCustomers>(client.sqlClient(oracleTables), OracleCustomers)
