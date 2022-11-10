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
import org.ufoss.kotysa.test.repositories.blocking.SelectOrRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectOrTest

class JdbcSelectOrPostgresqlTest : AbstractJdbcPostgresqlTest<UserRepositoryJdbcPostgresqlSelectOr>(),
    SelectOrTest<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles, UserRepositoryJdbcPostgresqlSelectOr,
            JdbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlJdbcSqlClient) =
        UserRepositoryJdbcPostgresqlSelectOr(sqlClient)
}

class UserRepositoryJdbcPostgresqlSelectOr(sqlClient: JdbcSqlClient) :
    SelectOrRepository<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles>(
        sqlClient, PostgresqlRoles,
        PostgresqlUsers, PostgresqlUserRoles
    )
