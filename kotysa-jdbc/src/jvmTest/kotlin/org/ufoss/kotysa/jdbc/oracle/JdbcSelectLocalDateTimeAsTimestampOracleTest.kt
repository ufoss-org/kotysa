/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.oracle

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.OracleLocalDateTimeAsTimestamps
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTimeAsTimestampRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTimeAsTimestampTest

class JdbcSelectLocalDateTimeAsTimestampOracleTest :
    AbstractJdbcOracleTest<LocalDateTimeAsTimestampRepositoryOracleSelect>(),
    SelectLocalDateTimeAsTimestampTest<OracleLocalDateTimeAsTimestamps,
            LocalDateTimeAsTimestampRepositoryOracleSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) =
        LocalDateTimeAsTimestampRepositoryOracleSelect(sqlClient)
}

class LocalDateTimeAsTimestampRepositoryOracleSelect(sqlClient: JdbcSqlClient) :
    SelectLocalDateTimeAsTimestampRepository<OracleLocalDateTimeAsTimestamps>(
        sqlClient,
        OracleLocalDateTimeAsTimestamps
    )
