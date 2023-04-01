/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MariadbRoles
import org.ufoss.kotysa.test.MariadbUserRoles
import org.ufoss.kotysa.test.MariadbUsers
import org.ufoss.kotysa.test.repositories.blocking.SubQueryRepository
import org.ufoss.kotysa.test.repositories.blocking.SubQueryTest

class JdbcSubQueryMariadbTest : AbstractJdbcMariadbTest<UserRepositoryJdbcMariadbSubQuery>(),
    SubQueryTest<MariadbRoles, MariadbUsers, MariadbUserRoles, UserRepositoryJdbcMariadbSubQuery, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcMariadbSubQuery(sqlClient)
}

class UserRepositoryJdbcMariadbSubQuery(sqlClient: JdbcSqlClient) :
    SubQueryRepository<MariadbRoles, MariadbUsers, MariadbUserRoles>(
        sqlClient, MariadbRoles, MariadbUsers,
        MariadbUserRoles
    )
