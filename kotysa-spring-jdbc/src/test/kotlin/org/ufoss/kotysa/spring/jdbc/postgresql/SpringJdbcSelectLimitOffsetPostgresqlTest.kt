/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.PostgresqlCustomers
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectLimitOffsetRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLimitOffsetTest

class SpringJdbcSelectLimitOffsetPostgresqlTest :
    AbstractSpringJdbcPostgresqlTest<LimitOffsetRepositoryPostgresqlSelect>(),
    SelectLimitOffsetTest<PostgresqlCustomers, LimitOffsetRepositoryPostgresqlSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        LimitOffsetRepositoryPostgresqlSelect(jdbcOperations)
}

class LimitOffsetRepositoryPostgresqlSelect(client: JdbcOperations) :
    SelectLimitOffsetRepository<PostgresqlCustomers>(client.sqlClient(postgresqlTables), PostgresqlCustomers)
