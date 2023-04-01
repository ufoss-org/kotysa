/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MariadbRoles
import org.ufoss.kotysa.test.MariadbUserRoles
import org.ufoss.kotysa.test.MariadbUsers
import org.ufoss.kotysa.test.repositories.blocking.SelectStringRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectStringTest

class JdbcSelectStringMariadbTest : AbstractJdbcMariadbTest<UserRepositoryJdbcMariadbSelectString>(),
    SelectStringTest<MariadbRoles, MariadbUsers, MariadbUserRoles, UserRepositoryJdbcMariadbSelectString,
            JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcMariadbSelectString(sqlClient)
}

class UserRepositoryJdbcMariadbSelectString(sqlClient: JdbcSqlClient) :
    SelectStringRepository<MariadbRoles, MariadbUsers, MariadbUserRoles>(
        sqlClient, MariadbRoles, MariadbUsers,
        MariadbUserRoles
    )
