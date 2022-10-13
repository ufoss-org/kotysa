/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.repositories.blocking.SelectAndRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectAndTest

class SpringJdbcSelectAndMysqlTest : AbstractSpringJdbcMysqlTest<UserRepositorySpringJdbcMysqlSelectAnd>(),
    SelectAndTest<MysqlRoles, MysqlUsers, MysqlUserRoles, UserRepositorySpringJdbcMysqlSelectAnd,
            SpringJdbcTransaction> {
    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositorySpringJdbcMysqlSelectAnd>(resource)
    }

    override val repository: UserRepositorySpringJdbcMysqlSelectAnd by lazy {
        getContextRepository()
    }
}

class UserRepositorySpringJdbcMysqlSelectAnd(client: JdbcOperations) :
    SelectAndRepository<MysqlRoles, MysqlUsers, MysqlUserRoles>(
        client.sqlClient(mysqlTables),
        MysqlRoles, MysqlUsers, MysqlUserRoles
    )
