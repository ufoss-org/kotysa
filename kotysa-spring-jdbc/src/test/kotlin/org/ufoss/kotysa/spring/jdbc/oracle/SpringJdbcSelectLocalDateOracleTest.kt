/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.oracle

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.OracleLocalDates
import org.ufoss.kotysa.test.oracleTables
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTest

class SpringJdbcSelectLocalDateOracleTest : AbstractSpringJdbcOracleTest<LocalDateRepositoryOracleSelect>(),
    SelectLocalDateTest<OracleLocalDates, LocalDateRepositoryOracleSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) = LocalDateRepositoryOracleSelect(jdbcOperations)
}

class LocalDateRepositoryOracleSelect(client: JdbcOperations) :
    SelectLocalDateRepository<OracleLocalDates>(client.sqlClient(oracleTables), OracleLocalDates)
