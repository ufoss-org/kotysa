/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.oracle

import org.junit.jupiter.api.Order
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectIntAsIdentitiesRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectIntAsIdentitiesTest

@Order(1)
class SpringJdbcSelectIntOracleTest : AbstractSpringJdbcOracleTest<SelectIntRepositoryOracleSelect>(),
    SelectIntAsIdentitiesTest<OracleIntAsIdentities, SelectIntRepositoryOracleSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) = SelectIntRepositoryOracleSelect(jdbcOperations)
}


class SelectIntRepositoryOracleSelect(client: JdbcOperations) :
    SelectIntAsIdentitiesRepository<OracleIntAsIdentities>(client.sqlClient(oracleTables), OracleIntAsIdentities)
