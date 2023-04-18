/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.PostgresqlRoles
import org.ufoss.kotysa.test.PostgresqlUserRoles
import org.ufoss.kotysa.test.PostgresqlUsers
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectStringRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectStringTest

class SpringJdbcSelectStringPostgresqlTest :
    AbstractSpringJdbcPostgresqlTest<UserRepositorySpringJdbcPostgresqlSelectString>(),
    SelectStringTest<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles,
            UserRepositorySpringJdbcPostgresqlSelectString, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        UserRepositorySpringJdbcPostgresqlSelectString(jdbcOperations)
}

class UserRepositorySpringJdbcPostgresqlSelectString(client: JdbcOperations) :
    SelectStringRepository<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles>(
        client.sqlClient(postgresqlTables), PostgresqlRoles,
        PostgresqlUsers, PostgresqlUserRoles
    )