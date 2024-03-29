/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.PostgresqlJdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.PostgresqlCompanies
import org.ufoss.kotysa.test.PostgresqlRoles
import org.ufoss.kotysa.test.PostgresqlUserRoles
import org.ufoss.kotysa.test.PostgresqlUsers
import org.ufoss.kotysa.test.repositories.blocking.SelectAndRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectAndTest

class JdbcSelectAndPostgresqlTest : AbstractJdbcPostgresqlTest<UserRepositoryJdbcPostgresqlSelectAnd>(),
    SelectAndTest<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles, PostgresqlCompanies,
            UserRepositoryJdbcPostgresqlSelectAnd, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlJdbcSqlClient) =
        UserRepositoryJdbcPostgresqlSelectAnd(sqlClient)
}

class UserRepositoryJdbcPostgresqlSelectAnd(sqlClient: JdbcSqlClient) :
    SelectAndRepository<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles, PostgresqlCompanies>(
        sqlClient,
        PostgresqlRoles,
        PostgresqlUsers,
        PostgresqlUserRoles,
        PostgresqlCompanies
    )
