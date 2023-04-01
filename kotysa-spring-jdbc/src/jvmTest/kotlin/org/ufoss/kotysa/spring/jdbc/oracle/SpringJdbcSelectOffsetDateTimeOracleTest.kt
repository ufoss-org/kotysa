/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.oracle

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.OracleOffsetDateTimes
import org.ufoss.kotysa.test.oracleTables
import org.ufoss.kotysa.test.repositories.blocking.SelectOffsetDateTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectOffsetDateTimeTest

class SpringJdbcSelectOffsetDateTimeOracleTest :
    AbstractSpringJdbcOracleTest<OffsetDateTimeRepositoryOracleSelect>(),
    SelectOffsetDateTimeTest<OracleOffsetDateTimes, OffsetDateTimeRepositoryOracleSelect,
            SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        OffsetDateTimeRepositoryOracleSelect(jdbcOperations)
}

class OffsetDateTimeRepositoryOracleSelect(client: JdbcOperations) :
    SelectOffsetDateTimeRepository<OracleOffsetDateTimes>(
        client.sqlClient(oracleTables),
        OracleOffsetDateTimes
    )
