/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.PostgresqlDoubles
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectDoubleRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectDoubleTest

class SpringJdbcSelectDoublePostgresqlTest : AbstractSpringJdbcPostgresqlTest<DoubleRepositoryPostgresqlSelect>(),
    SelectDoubleTest<PostgresqlDoubles, DoubleRepositoryPostgresqlSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        DoubleRepositoryPostgresqlSelect(jdbcOperations)
}

class DoubleRepositoryPostgresqlSelect(client: JdbcOperations) :
    SelectDoubleRepository<PostgresqlDoubles>(client.sqlClient(postgresqlTables), PostgresqlDoubles)
