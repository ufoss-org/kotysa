/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.oracle

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.OracleKotlinxLocalDateTimeAsTimestamps
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTimeAsTimestampRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTimeAsTimestampTest

class JdbcSelectKotlinxLocalDateTimeAsTimestampOracleTest :
    AbstractJdbcOracleTest<KotlinxLocalDateTimeAsTimestampRepositoryOracleSelect>(),
    SelectKotlinxLocalDateTimeAsTimestampTest<OracleKotlinxLocalDateTimeAsTimestamps,
            KotlinxLocalDateTimeAsTimestampRepositoryOracleSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) =
        KotlinxLocalDateTimeAsTimestampRepositoryOracleSelect(sqlClient)
}

class KotlinxLocalDateTimeAsTimestampRepositoryOracleSelect(sqlClient: JdbcSqlClient) :
    SelectKotlinxLocalDateTimeAsTimestampRepository<OracleKotlinxLocalDateTimeAsTimestamps>(
        sqlClient,
        OracleKotlinxLocalDateTimeAsTimestamps
    )
