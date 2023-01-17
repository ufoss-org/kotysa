/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.oracle

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.OracleOffsetDateTimes
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.oracleTables
import org.ufoss.kotysa.test.repositories.blocking.SelectOffsetDateTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectOffsetDateTimeTest

class SpringJdbcSelectOffsetDateTimeOracleTest :
    AbstractSpringJdbcOracleTest<OffsetDateTimeRepositoryOracleSelect>(),
    SelectOffsetDateTimeTest<OracleOffsetDateTimes, OffsetDateTimeRepositoryOracleSelect,
            SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<OffsetDateTimeRepositoryOracleSelect>(resource)
    }

    override val repository: OffsetDateTimeRepositoryOracleSelect by lazy {
        getContextRepository()
    }
}

class OffsetDateTimeRepositoryOracleSelect(client: JdbcOperations) :
    SelectOffsetDateTimeRepository<OracleOffsetDateTimes>(
        client.sqlClient(oracleTables),
        OracleOffsetDateTimes
    )
