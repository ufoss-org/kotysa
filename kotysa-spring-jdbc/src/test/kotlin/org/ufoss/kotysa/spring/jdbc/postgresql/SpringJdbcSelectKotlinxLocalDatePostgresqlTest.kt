/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.PostgresqlKotlinxLocalDates
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTest

class SpringJdbcSelectKotlinxLocalDatePostgresqlTest :
    AbstractSpringJdbcPostgresqlTest<KotlinxLocalDateRepositoryPostgresqlSelect>(),
    SelectKotlinxLocalDateTest<PostgresqlKotlinxLocalDates, KotlinxLocalDateRepositoryPostgresqlSelect,
            SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        KotlinxLocalDateRepositoryPostgresqlSelect(jdbcOperations)
}

class KotlinxLocalDateRepositoryPostgresqlSelect(client: JdbcOperations) :
    SelectKotlinxLocalDateRepository<PostgresqlKotlinxLocalDates>(
        client.sqlClient(postgresqlTables),
        PostgresqlKotlinxLocalDates
    )
