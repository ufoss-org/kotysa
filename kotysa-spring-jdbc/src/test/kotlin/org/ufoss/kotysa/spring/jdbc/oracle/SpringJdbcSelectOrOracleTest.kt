/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.oracle

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.OracleRoles
import org.ufoss.kotysa.test.OracleUserRoles
import org.ufoss.kotysa.test.OracleUsers
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.oracleTables
import org.ufoss.kotysa.test.repositories.blocking.SelectOrRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectOrTest

class SpringJdbcSelectOrOracleTest : AbstractSpringJdbcOracleTest<UserRepositorySpringJdbcOracleSelectOr>(),
    SelectOrTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositorySpringJdbcOracleSelectOr, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositorySpringJdbcOracleSelectOr>(resource)
    }

    override val repository: UserRepositorySpringJdbcOracleSelectOr by lazy {
        getContextRepository()
    }
}

class UserRepositorySpringJdbcOracleSelectOr(client: JdbcOperations) :
    SelectOrRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        client.sqlClient(oracleTables),
        OracleRoles,
        OracleUsers,
        OracleUserRoles
    )
