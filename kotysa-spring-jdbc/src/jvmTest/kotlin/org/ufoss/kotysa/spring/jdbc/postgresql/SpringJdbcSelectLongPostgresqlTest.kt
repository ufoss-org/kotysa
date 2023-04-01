/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.junit.jupiter.api.Order
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectLongRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLongTest

@Order(2)
class SpringJdbcSelectLongPostgresqlTest : AbstractSpringJdbcPostgresqlTest<SelectLongRepositoryPostgresqlSelect>(),
    SelectLongTest<PostgresqlLongs, SelectLongRepositoryPostgresqlSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        SelectLongRepositoryPostgresqlSelect(jdbcOperations)
}

class SelectLongRepositoryPostgresqlSelect(client: JdbcOperations) :
    SelectLongRepository<PostgresqlLongs>(client.sqlClient(postgresqlTables), PostgresqlLongs)
