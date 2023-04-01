/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.PostgresqlLocalDates
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTest

class SpringJdbcSelectLocalDatePostgresqlTest : AbstractSpringJdbcPostgresqlTest<LocalDateRepositoryPostgresqlSelect>(),
    SelectLocalDateTest<PostgresqlLocalDates, LocalDateRepositoryPostgresqlSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        LocalDateRepositoryPostgresqlSelect(jdbcOperations)
}

class LocalDateRepositoryPostgresqlSelect(client: JdbcOperations) :
    SelectLocalDateRepository<PostgresqlLocalDates>(client.sqlClient(postgresqlTables), PostgresqlLocalDates)
