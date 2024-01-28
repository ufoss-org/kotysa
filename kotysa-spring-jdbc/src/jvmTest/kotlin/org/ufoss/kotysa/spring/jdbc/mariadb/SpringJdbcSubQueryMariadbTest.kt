/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SubQueryRepository
import org.ufoss.kotysa.test.repositories.blocking.SubQueryTest

class SpringJdbcSubQueryMariadbTest : AbstractSpringJdbcMariadbTest<UserRepositoryJdbcMariadbSubQuery>(),
    SubQueryTest<MariadbRoles, MariadbUsers, MariadbUserRoles, MariadbCompanies, UserRepositoryJdbcMariadbSubQuery,
            SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        UserRepositoryJdbcMariadbSubQuery(jdbcOperations)
}

class UserRepositoryJdbcMariadbSubQuery(client: JdbcOperations) :
    SubQueryRepository<MariadbRoles, MariadbUsers, MariadbUserRoles, MariadbCompanies>(
        client.sqlClient(mariadbTables),
        MariadbRoles,
        MariadbUsers,
        MariadbUserRoles,
        MariadbCompanies
    )
