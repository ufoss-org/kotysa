/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.PostgresqlJdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.PostgresqlLocalTimes
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalTimeTest

class JdbcSelectLocalTimePostgresqlTest : AbstractJdbcPostgresqlTest<LocalTimeRepositoryPostgresqlSelect>(),
    SelectLocalTimeTest<PostgresqlLocalTimes, LocalTimeRepositoryPostgresqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlJdbcSqlClient) =
        LocalTimeRepositoryPostgresqlSelect(sqlClient)
}

class LocalTimeRepositoryPostgresqlSelect(sqlClient: JdbcSqlClient) :
    SelectLocalTimeRepository<PostgresqlLocalTimes>(sqlClient, PostgresqlLocalTimes)
