/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.oracle

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.OracleKotlinxLocalDateTimeAsTimestamps
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.oracleTables
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTimeAsTimestampRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTimeAsTimestampTest

class SpringJdbcSelectKotlinxLocalDateTimeAsTimestampOracleTest :
    AbstractSpringJdbcOracleTest<KotlinxLocalDateTimeAsTimestampRepositoryOracleSelect>(),
    SelectKotlinxLocalDateTimeAsTimestampTest<OracleKotlinxLocalDateTimeAsTimestamps,
            KotlinxLocalDateTimeAsTimestampRepositoryOracleSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<KotlinxLocalDateTimeAsTimestampRepositoryOracleSelect>(resource)
    }

    override val repository: KotlinxLocalDateTimeAsTimestampRepositoryOracleSelect by lazy {
        getContextRepository()
    }
}

class KotlinxLocalDateTimeAsTimestampRepositoryOracleSelect(client: JdbcOperations) :
    SelectKotlinxLocalDateTimeAsTimestampRepository<OracleKotlinxLocalDateTimeAsTimestamps>(
        client.sqlClient(oracleTables),
        OracleKotlinxLocalDateTimeAsTimestamps
    )
