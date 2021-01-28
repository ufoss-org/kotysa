/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql
/*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.test.MysqlUser
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mysqlBboss
import org.ufoss.kotysa.test.mysqlJdoe


class SpringJdbcSelectBooleanMysqlTest : AbstractSpringJdbcMysqlTest<UserRepositorySpringJdbcMysqlSelectBoolean>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositorySpringJdbcMysqlSelectBoolean>(resource)
        repository = getContextRepository()
    }

    @Test
    fun `Verify selectAllByIsAdminEq true finds Big Boss`() {
        assertThat(repository.selectAllByIsAdminEq(true))
                .hasSize(1)
                .containsExactly(mysqlBboss)
    }

    @Test
    fun `Verify selectAllByIsAdminEq false finds John`() {
        assertThat(repository.selectAllByIsAdminEq(false))
                .hasSize(1)
                .containsExactly(mysqlJdoe)
    }
}


class UserRepositorySpringJdbcMysqlSelectBoolean(client: JdbcOperations) : AbstractUserRepositorySpringJdbcMysql(client) {

    fun selectAllByIsAdminEq(value: Boolean) =
            sqlClient.select<MysqlUser>()
                    .where { it[MysqlUser::isAdmin] eq value }
                    .fetchAll()
}
*/