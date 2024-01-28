/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.UpdateDeleteRepository
import org.ufoss.kotysa.test.repositories.blocking.UpdateDeleteTest

class SpringJdbcUpdateDeleteMariadbTest : AbstractSpringJdbcMariadbTest<UserRepositorySpringJdbcMariadbUpdateDelete>(),
    UpdateDeleteTest<MariadbRoles, MariadbUsers, MariadbUserRoles, MariadbCompanies,
            UserRepositorySpringJdbcMariadbUpdateDelete, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        UserRepositorySpringJdbcMariadbUpdateDelete(jdbcOperations)
}

class UserRepositorySpringJdbcMariadbUpdateDelete(client: JdbcOperations) :
    UpdateDeleteRepository<MariadbRoles, MariadbUsers, MariadbUserRoles, MariadbCompanies>(
        client.sqlClient(mariadbTables),
        MariadbRoles,
        MariadbUsers,
        MariadbUserRoles,
        MariadbCompanies
    )
