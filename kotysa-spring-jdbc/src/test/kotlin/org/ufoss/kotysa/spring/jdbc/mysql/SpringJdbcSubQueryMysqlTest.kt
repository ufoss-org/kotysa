/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MysqlRoles
import org.ufoss.kotysa.test.MysqlUserRoles
import org.ufoss.kotysa.test.MysqlUsers
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.blocking.SubQueryRepository
import org.ufoss.kotysa.test.repositories.blocking.SubQueryTest

class SpringJdbcSubQueryMysqlTest : AbstractSpringJdbcMysqlTest<UserRepositoryJdbcMysqlSubQuery>(),
    SubQueryTest<MysqlRoles, MysqlUsers, MysqlUserRoles, UserRepositoryJdbcMysqlSubQuery, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositoryJdbcMysqlSubQuery>(resource)
    }

    override val repository: UserRepositoryJdbcMysqlSubQuery by lazy {
        getContextRepository()
    }
}

class UserRepositoryJdbcMysqlSubQuery(client: JdbcOperations) :
    SubQueryRepository<MysqlRoles, MysqlUsers, MysqlUserRoles>(
        client.sqlClient(mysqlTables),
        MysqlRoles,
        MysqlUsers,
        MysqlUserRoles
    )
