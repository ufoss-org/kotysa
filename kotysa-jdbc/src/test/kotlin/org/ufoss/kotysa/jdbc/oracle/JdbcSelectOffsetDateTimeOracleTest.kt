/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.oracle

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.OracleOffsetDateTimes
import org.ufoss.kotysa.test.repositories.blocking.SelectOffsetDateTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectOffsetDateTimeTest

class JdbcSelectOffsetDateTimeOracleTest : AbstractJdbcOracleTest<OffsetDateTimeRepositoryOracleSelect>(),
    SelectOffsetDateTimeTest<OracleOffsetDateTimes, OffsetDateTimeRepositoryOracleSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) =
        OffsetDateTimeRepositoryOracleSelect(sqlClient)
}

class OffsetDateTimeRepositoryOracleSelect(sqlClient: JdbcSqlClient) :
    SelectOffsetDateTimeRepository<OracleOffsetDateTimes>(sqlClient, OracleOffsetDateTimes)
