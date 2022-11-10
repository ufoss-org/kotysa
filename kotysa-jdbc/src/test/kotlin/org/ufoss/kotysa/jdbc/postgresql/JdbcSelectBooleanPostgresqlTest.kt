/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.PostgresqlJdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.PostgresqlRoles
import org.ufoss.kotysa.test.PostgresqlUserRoles
import org.ufoss.kotysa.test.PostgresqlUsers
import org.ufoss.kotysa.test.repositories.blocking.SelectBooleanRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBooleanTest

class JdbcSelectBooleanPostgresqlTest : AbstractJdbcPostgresqlTest<UserRepositoryJdbcPostgresqlSelectBoolean>(),
    SelectBooleanTest<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles, UserRepositoryJdbcPostgresqlSelectBoolean,
            JdbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlJdbcSqlClient) =
        UserRepositoryJdbcPostgresqlSelectBoolean(sqlClient)
}

class UserRepositoryJdbcPostgresqlSelectBoolean(sqlClient: JdbcSqlClient) :
    SelectBooleanRepository<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles>(
        sqlClient,
        PostgresqlRoles,
        PostgresqlUsers,
        PostgresqlUserRoles
    )
