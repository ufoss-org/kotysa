/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.PostgresqlKotlinxLocalTimes
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalTimeTest

class SpringJdbcSelectKotlinxLocalTimePostgresqlTest :
    AbstractSpringJdbcPostgresqlTest<KotlinxLocalTimeRepositoryPostgresqlSelect>(),
    SelectKotlinxLocalTimeTest<PostgresqlKotlinxLocalTimes, KotlinxLocalTimeRepositoryPostgresqlSelect,
            SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        KotlinxLocalTimeRepositoryPostgresqlSelect(jdbcOperations)
}

class KotlinxLocalTimeRepositoryPostgresqlSelect(client: JdbcOperations) :
    SelectKotlinxLocalTimeRepository<PostgresqlKotlinxLocalTimes>(
        client.sqlClient(postgresqlTables),
        PostgresqlKotlinxLocalTimes
    )
