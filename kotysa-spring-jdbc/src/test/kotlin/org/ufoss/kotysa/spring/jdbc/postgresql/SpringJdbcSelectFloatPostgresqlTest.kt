/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.PostgresqlFloats
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectFloatRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectFloatTest

class SpringJdbcSelectFloatPostgresqlTest : AbstractSpringJdbcPostgresqlTest<FloatRepositoryPostgresqlSelect>(),
    SelectFloatTest<PostgresqlFloats, FloatRepositoryPostgresqlSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) = FloatRepositoryPostgresqlSelect(jdbcOperations)
}

class FloatRepositoryPostgresqlSelect(client: JdbcOperations) :
    SelectFloatRepository<PostgresqlFloats>(client.sqlClient(postgresqlTables), PostgresqlFloats)
