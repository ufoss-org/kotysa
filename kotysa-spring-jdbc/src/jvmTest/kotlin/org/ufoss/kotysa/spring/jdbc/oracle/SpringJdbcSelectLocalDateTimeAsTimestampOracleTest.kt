/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.oracle

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.OracleLocalDateTimeAsTimestamps
import org.ufoss.kotysa.test.oracleTables
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTimeAsTimestampRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTimeAsTimestampTest

class SpringJdbcSelectLocalDateTimeAsTimestampOracleTest :
    AbstractSpringJdbcOracleTest<LocalDateTimeAsTimestampRepositoryOracleSelect>(),
    SelectLocalDateTimeAsTimestampTest<OracleLocalDateTimeAsTimestamps,
            LocalDateTimeAsTimestampRepositoryOracleSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        LocalDateTimeAsTimestampRepositoryOracleSelect(jdbcOperations)
}

class LocalDateTimeAsTimestampRepositoryOracleSelect(client: JdbcOperations) :
    SelectLocalDateTimeAsTimestampRepository<OracleLocalDateTimeAsTimestamps>(
        client.sqlClient(oracleTables),
        OracleLocalDateTimeAsTimestamps
    )
