/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.oracle

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.OracleKotlinxLocalDates
import org.ufoss.kotysa.test.oracleTables
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTest

class SpringJdbcSelectKotlinxLocalDateOracleTest :
    AbstractSpringJdbcOracleTest<KotlinxLocalDateRepositoryOracleSelect>(),
    SelectKotlinxLocalDateTest<OracleKotlinxLocalDates, KotlinxLocalDateRepositoryOracleSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        KotlinxLocalDateRepositoryOracleSelect(jdbcOperations)
}

class KotlinxLocalDateRepositoryOracleSelect(client: JdbcOperations) :
    SelectKotlinxLocalDateRepository<OracleKotlinxLocalDates>(client.sqlClient(oracleTables), OracleKotlinxLocalDates)
