/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.PostgresqlRoles
import org.ufoss.kotysa.test.PostgresqlUserRoles
import org.ufoss.kotysa.test.PostgresqlUsers
import org.ufoss.kotysa.test.repositories.blocking.UpdateDeleteRepository
import org.ufoss.kotysa.test.repositories.blocking.UpdateDeleteTest

class JdbcUpdateDeletePostgresqlTest : AbstractJdbcPostgresqlTest<UserRepositoryJdbcPostgresqlUpdateDelete>(),
    UpdateDeleteTest<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles, UserRepositoryJdbcPostgresqlUpdateDelete, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcPostgresqlUpdateDelete(sqlClient)
}

class UserRepositoryJdbcPostgresqlUpdateDelete(sqlClient: JdbcSqlClient) :
    UpdateDeleteRepository<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles>(
        sqlClient,
        PostgresqlRoles,
        PostgresqlUsers,
        PostgresqlUserRoles
    )
