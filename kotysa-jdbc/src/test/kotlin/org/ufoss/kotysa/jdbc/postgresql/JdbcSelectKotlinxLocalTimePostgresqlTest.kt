/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.PostgresqlJdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.PostgresqlKotlinxLocalTimes
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalTimeTest

class JdbcSelectKotlinxLocalTimePostgresqlTest :
    AbstractJdbcPostgresqlTest<KotlinxLocalTimeRepositoryPostgresqlSelect>(),
    SelectKotlinxLocalTimeTest<PostgresqlKotlinxLocalTimes, KotlinxLocalTimeRepositoryPostgresqlSelect,
            JdbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlJdbcSqlClient) =
        KotlinxLocalTimeRepositoryPostgresqlSelect(sqlClient)
}

class KotlinxLocalTimeRepositoryPostgresqlSelect(sqlClient: JdbcSqlClient) :
    SelectKotlinxLocalTimeRepository<PostgresqlKotlinxLocalTimes>(sqlClient, PostgresqlKotlinxLocalTimes)
