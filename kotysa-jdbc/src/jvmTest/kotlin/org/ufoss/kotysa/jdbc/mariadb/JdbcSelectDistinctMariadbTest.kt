/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MariadbRoles
import org.ufoss.kotysa.test.MariadbUserRoles
import org.ufoss.kotysa.test.MariadbUsers
import org.ufoss.kotysa.test.repositories.blocking.SelectDistinctRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectDistinctTest

class JdbcSelectDistinctMariadbTest : AbstractJdbcMariadbTest<UserRepositoryJdbcMariadbSelectDistinct>(),
    SelectDistinctTest<MariadbRoles, MariadbUsers, MariadbUserRoles, UserRepositoryJdbcMariadbSelectDistinct,
            JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcMariadbSelectDistinct(sqlClient)
}

class UserRepositoryJdbcMariadbSelectDistinct(sqlClient: JdbcSqlClient) :
    SelectDistinctRepository<MariadbRoles, MariadbUsers, MariadbUserRoles>(
        sqlClient, MariadbRoles, MariadbUsers,
        MariadbUserRoles
    )
