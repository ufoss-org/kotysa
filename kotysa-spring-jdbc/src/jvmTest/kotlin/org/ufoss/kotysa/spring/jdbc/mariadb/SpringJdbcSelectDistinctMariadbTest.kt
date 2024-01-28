/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectDistinctRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectDistinctTest

class SpringJdbcSelectDistinctMariadbTest :
    AbstractSpringJdbcMariadbTest<UserRepositorySpringJdbcMariadbSelectDistinct>(),
    SelectDistinctTest<MariadbRoles, MariadbUsers, MariadbUserRoles, MariadbCompanies,
            UserRepositorySpringJdbcMariadbSelectDistinct, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        UserRepositorySpringJdbcMariadbSelectDistinct(jdbcOperations)
}

class UserRepositorySpringJdbcMariadbSelectDistinct(client: JdbcOperations) :
    SelectDistinctRepository<MariadbRoles, MariadbUsers, MariadbUserRoles, MariadbCompanies>(
        client.sqlClient(mariadbTables),
        MariadbRoles,
        MariadbUsers,
        MariadbUserRoles,
        MariadbCompanies
    )
