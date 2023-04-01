/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.oracle

import org.junit.jupiter.api.Order
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectIntRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectIntTest

@Order(1)
class SpringJdbcSelectIntOracleTest : AbstractSpringJdbcOracleTest<SelectIntRepositoryOracleSelect>(),
    SelectIntTest<OracleInts, SelectIntRepositoryOracleSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) = SelectIntRepositoryOracleSelect(jdbcOperations)
}


class SelectIntRepositoryOracleSelect(client: JdbcOperations) :
    SelectIntRepository<OracleInts>(client.sqlClient(oracleTables), OracleInts)
