/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.junit.jupiter.api.Order
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectIntRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectIntTest

@Order(1)
class SpringJdbcSelectIntPostgresqlTest : AbstractSpringJdbcPostgresqlTest<SelectIntRepositoryPostgresqlSelect>(),
    SelectIntTest<PostgresqlInts, SelectIntRepositoryPostgresqlSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        SelectIntRepositoryPostgresqlSelect(jdbcOperations)
}


class SelectIntRepositoryPostgresqlSelect(client: JdbcOperations) :
    SelectIntRepository<PostgresqlInts>(client.sqlClient(postgresqlTables), PostgresqlInts)
