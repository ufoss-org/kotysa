/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.oracle

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.repositories.blocking.SelectAndRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectAndTest

class SpringJdbcSelectAndOracleTest : AbstractSpringJdbcOracleTest<UserRepositorySpringJdbcOracleSelectAnd>(),
    SelectAndTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositorySpringJdbcOracleSelectAnd,
            SpringJdbcTransaction> {
    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositorySpringJdbcOracleSelectAnd>(resource)
    }

    override val repository: UserRepositorySpringJdbcOracleSelectAnd by lazy {
        getContextRepository()
    }
}

class UserRepositorySpringJdbcOracleSelectAnd(client: JdbcOperations) :
    SelectAndRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        client.sqlClient(oracleTables),
        OracleRoles, OracleUsers, OracleUserRoles
    )
