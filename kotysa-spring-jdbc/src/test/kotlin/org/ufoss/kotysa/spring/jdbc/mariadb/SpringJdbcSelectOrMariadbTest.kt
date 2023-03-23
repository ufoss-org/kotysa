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
import org.ufoss.kotysa.test.repositories.blocking.SelectOrRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectOrTest

class SpringJdbcSelectOrMariadbTest : AbstractSpringJdbcMariadbTest<UserRepositorySpringJdbcMariadbSelectOr>(),
    SelectOrTest<MariadbRoles, MariadbUsers, MariadbUserRoles, UserRepositorySpringJdbcMariadbSelectOr,
            SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        UserRepositorySpringJdbcMariadbSelectOr(jdbcOperations)
}

class UserRepositorySpringJdbcMariadbSelectOr(client: JdbcOperations) :
    SelectOrRepository<MariadbRoles, MariadbUsers, MariadbUserRoles>(
        client.sqlClient(mariadbTables),
        MariadbRoles,
        MariadbUsers,
        MariadbUserRoles
    )
