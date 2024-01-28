/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectBooleanRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBooleanTest

class SpringJdbcSelectBooleanMariadbTest :
    AbstractSpringJdbcMariadbTest<UserRepositorySpringJdbcMariadbSelectBoolean>(),
    SelectBooleanTest<MariadbRoles, MariadbUsers, MariadbUserRoles, MariadbCompanies,
            UserRepositorySpringJdbcMariadbSelectBoolean, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        UserRepositorySpringJdbcMariadbSelectBoolean(jdbcOperations)
}

class UserRepositorySpringJdbcMariadbSelectBoolean(client: JdbcOperations) :
    SelectBooleanRepository<MariadbRoles, MariadbUsers, MariadbUserRoles, MariadbCompanies>(
        client.sqlClient(mariadbTables),
        MariadbRoles,
        MariadbUsers,
        MariadbUserRoles,
        MariadbCompanies
    )
