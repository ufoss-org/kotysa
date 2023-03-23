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
import org.ufoss.kotysa.test.repositories.blocking.SelectBooleanRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBooleanTest

class SpringJdbcSelectBooleanPostgresqlTest :
    AbstractSpringJdbcPostgresqlTest<UserRepositorySpringJdbcPostgresqlSelectBoolean>(),
    SelectBooleanTest<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles,
            UserRepositorySpringJdbcPostgresqlSelectBoolean, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        UserRepositorySpringJdbcPostgresqlSelectBoolean(jdbcOperations)
}

class UserRepositorySpringJdbcPostgresqlSelectBoolean(client: JdbcOperations) :
    SelectBooleanRepository<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles>(
        client.sqlClient(postgresqlTables),
        PostgresqlRoles,
        PostgresqlUsers,
        PostgresqlUserRoles
    )
