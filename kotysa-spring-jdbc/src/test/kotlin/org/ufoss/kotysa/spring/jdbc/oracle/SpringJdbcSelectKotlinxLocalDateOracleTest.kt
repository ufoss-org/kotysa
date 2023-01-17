/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.oracle

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.OracleKotlinxLocalDates
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.oracleTables
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTest

class SpringJdbcSelectKotlinxLocalDateOracleTest : AbstractSpringJdbcOracleTest<KotlinxLocalDateRepositoryOracleSelect>(),
    SelectKotlinxLocalDateTest<OracleKotlinxLocalDates, KotlinxLocalDateRepositoryOracleSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<KotlinxLocalDateRepositoryOracleSelect>(resource)
    }

    override val repository: KotlinxLocalDateRepositoryOracleSelect by lazy {
        getContextRepository()
    }
}

class KotlinxLocalDateRepositoryOracleSelect(client: JdbcOperations) :
    SelectKotlinxLocalDateRepository<OracleKotlinxLocalDates>(client.sqlClient(oracleTables), OracleKotlinxLocalDates)
