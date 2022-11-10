/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.PostgresqlJdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.PostgresqlLocalDates
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTest

class JdbcSelectLocalDatePostgresqlTest : AbstractJdbcPostgresqlTest<LocalDateRepositoryPostgresqlSelect>(),
    SelectLocalDateTest<PostgresqlLocalDates, LocalDateRepositoryPostgresqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlJdbcSqlClient) =
        LocalDateRepositoryPostgresqlSelect(sqlClient)
}

class LocalDateRepositoryPostgresqlSelect(sqlClient: JdbcSqlClient) :
    SelectLocalDateRepository<PostgresqlLocalDates>(sqlClient, PostgresqlLocalDates)
