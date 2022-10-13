/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MariadbRoles
import org.ufoss.kotysa.test.MariadbUserRoles
import org.ufoss.kotysa.test.MariadbUsers
import org.ufoss.kotysa.test.repositories.blocking.SelectOrRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectOrTest

class JdbcSelectOrMariadbTest : AbstractJdbcMariadbTest<UserRepositoryJdbcMariadbSelectOr>(),
    SelectOrTest<MariadbRoles, MariadbUsers, MariadbUserRoles, UserRepositoryJdbcMariadbSelectOr, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcMariadbSelectOr(sqlClient)
}

class UserRepositoryJdbcMariadbSelectOr(sqlClient: JdbcSqlClient) :
    SelectOrRepository<MariadbRoles, MariadbUsers, MariadbUserRoles>(
        sqlClient, MariadbRoles, MariadbUsers,
        MariadbUserRoles
    )
