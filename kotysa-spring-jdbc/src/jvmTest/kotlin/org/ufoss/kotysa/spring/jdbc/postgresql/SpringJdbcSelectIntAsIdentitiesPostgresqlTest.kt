/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.junit.jupiter.api.Order
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectIntAsIdentitiesRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectIntAsIdentitiesTest

@Order(1)
class SpringJdbcSelectIntAsIdentitiesPostgresqlTest :
    AbstractSpringJdbcPostgresqlTest<SelectIntAsIdentitiesRepositoryPostgresqlSelect>(),
    SelectIntAsIdentitiesTest<PostgresqlIntAsIdentities, SelectIntAsIdentitiesRepositoryPostgresqlSelect,
            SpringJdbcTransaction> {
    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        SelectIntAsIdentitiesRepositoryPostgresqlSelect(jdbcOperations)
}

class SelectIntAsIdentitiesRepositoryPostgresqlSelect(client: JdbcOperations) :
    SelectIntAsIdentitiesRepository<PostgresqlIntAsIdentities>(
        client.sqlClient(postgresqlTables),
        PostgresqlIntAsIdentities
    )
