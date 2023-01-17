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
import org.ufoss.kotysa.test.repositories.blocking.SelectDistinctRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectDistinctTest

class SpringJdbcSelectDistinctOracleTest : AbstractSpringJdbcOracleTest<UserRepositorySpringJdbcOracleSelectDistinct>(),
    SelectDistinctTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositorySpringJdbcOracleSelectDistinct,
            SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositorySpringJdbcOracleSelectDistinct>(resource)
    }

    override val repository: UserRepositorySpringJdbcOracleSelectDistinct by lazy {
        getContextRepository()
    }
}

class UserRepositorySpringJdbcOracleSelectDistinct(client: JdbcOperations) :
    SelectDistinctRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        client.sqlClient(oracleTables),
        OracleRoles,
        OracleUsers,
        OracleUserRoles
    )
