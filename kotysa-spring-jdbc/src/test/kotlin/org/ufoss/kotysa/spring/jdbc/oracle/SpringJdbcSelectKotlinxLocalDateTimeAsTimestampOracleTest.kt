/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.oracle

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.OracleKotlinxLocalDateTimeAsTimestamps
import org.ufoss.kotysa.test.oracleTables
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTimeAsTimestampRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTimeAsTimestampTest

class SpringJdbcSelectKotlinxLocalDateTimeAsTimestampOracleTest :
    AbstractSpringJdbcOracleTest<KotlinxLocalDateTimeAsTimestampRepositoryOracleSelect>(),
    SelectKotlinxLocalDateTimeAsTimestampTest<OracleKotlinxLocalDateTimeAsTimestamps,
            KotlinxLocalDateTimeAsTimestampRepositoryOracleSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        KotlinxLocalDateTimeAsTimestampRepositoryOracleSelect(jdbcOperations)
}

class KotlinxLocalDateTimeAsTimestampRepositoryOracleSelect(client: JdbcOperations) :
    SelectKotlinxLocalDateTimeAsTimestampRepository<OracleKotlinxLocalDateTimeAsTimestamps>(
        client.sqlClient(oracleTables),
        OracleKotlinxLocalDateTimeAsTimestamps
    )
