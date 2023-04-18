/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MariadbRoles
import org.ufoss.kotysa.test.MariadbUserRoles
import org.ufoss.kotysa.test.MariadbUsers
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.blocking.SelectDistinctRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectDistinctTest

class SpringJdbcSelectDistinctMariadbTest :
    AbstractSpringJdbcMariadbTest<UserRepositorySpringJdbcMariadbSelectDistinct>(),
    SelectDistinctTest<MariadbRoles, MariadbUsers, MariadbUserRoles, UserRepositorySpringJdbcMariadbSelectDistinct,
            SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        UserRepositorySpringJdbcMariadbSelectDistinct(jdbcOperations)
}

class UserRepositorySpringJdbcMariadbSelectDistinct(client: JdbcOperations) :
    SelectDistinctRepository<MariadbRoles, MariadbUsers, MariadbUserRoles>(
        client.sqlClient(mariadbTables),
        MariadbRoles,
        MariadbUsers,
        MariadbUserRoles
    )