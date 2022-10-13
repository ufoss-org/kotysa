/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.PostgresqlOffsetDateTimes
import org.ufoss.kotysa.test.repositories.blocking.SelectOffsetDateTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectOffsetDateTimeTest

class JdbcSelectOffsetDateTimePostgresqlTest : AbstractJdbcPostgresqlTest<OffsetDateTimeRepositoryPostgresqlSelect>(),
    SelectOffsetDateTimeTest<PostgresqlOffsetDateTimes, OffsetDateTimeRepositoryPostgresqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = OffsetDateTimeRepositoryPostgresqlSelect(sqlClient)
}

class OffsetDateTimeRepositoryPostgresqlSelect(sqlClient: JdbcSqlClient) :
    SelectOffsetDateTimeRepository<PostgresqlOffsetDateTimes>(sqlClient, PostgresqlOffsetDateTimes)
