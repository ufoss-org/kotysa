/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.PostgresqlInheriteds
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.blocking.InheritanceRepository
import org.ufoss.kotysa.test.repositories.blocking.InheritanceTest

class SpringJdbcInheritancePostgresqlTest : AbstractSpringJdbcPostgresqlTest<InheritancePostgresqlRepository>(),
    InheritanceTest<PostgresqlInheriteds, InheritancePostgresqlRepository, SpringJdbcTransaction> {
    override val table = PostgresqlInheriteds

    override fun instantiateRepository(jdbcOperations: JdbcOperations) = InheritancePostgresqlRepository(jdbcOperations)
}

class InheritancePostgresqlRepository(client: JdbcOperations) :
    InheritanceRepository<PostgresqlInheriteds>(client.sqlClient(postgresqlTables), PostgresqlInheriteds)
