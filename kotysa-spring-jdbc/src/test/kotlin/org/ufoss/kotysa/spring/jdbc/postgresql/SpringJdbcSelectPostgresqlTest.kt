/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql
/*
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.NonUniqueResultException
import org.ufoss.kotysa.count
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource


class SpringJdbcSelectPostgresqlTest : AbstractSpringJdbcPostgresqlTest<UserRepositorySpringJdbcPostgresqlSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositorySpringJdbcPostgresqlSelect>(resource)
        repository = getContextRepository()
    }

    @Test
    fun `Verify selectAll returns all users`() {
        assertThat(repository.selectAllUsers())
                .hasSize(2)
                .containsExactlyInAnyOrder(postgresqlJdoe, postgresqlBboss)
    }

    @Test
    fun `Verify countUsers returns 2`() {
        assertThat(repository.countAllUsers())
                .isEqualTo(2L)
    }

    @Test
    fun `Verify countUsers with alias returns 1`() {
        assertThat(repository.countUsersWithAlias())
                .isEqualTo(1L)
    }

    @Test
    fun `Verify selectOneNonUnique throws NonUniqueResultException`() {
        assertThatThrownBy { repository.selectOneNonUnique() }
                .isInstanceOf(NonUniqueResultException::class.java)
    }

    @Test
    fun `Verify selectAllMappedToDto does the mapping`() {
        assertThat(repository.selectAllMappedToDto())
                .hasSize(2)
                .containsExactlyInAnyOrder(
                        UserDto("${postgresqlJdoe.firstname} ${postgresqlJdoe.lastname}", postgresqlJdoe.alias),
                        UserDto("${postgresqlBboss.firstname} ${postgresqlBboss.lastname}", postgresqlBboss.alias))
    }

    @Test
    fun `Verify selectWithJoin works correctly`() {
        assertThat(repository.selectWithJoin())
                .hasSize(2)
                .containsExactlyInAnyOrder(
                        UserWithRoleDto(postgresqlJdoe.lastname, postgresqlUser.label),
                        UserWithRoleDto(postgresqlBboss.lastname, postgresqlAdmin.label)
                )
    }
}


class UserRepositorySpringJdbcPostgresqlSelect(client: JdbcOperations) : AbstractUserRepositorySpringJdbcPostgresql(client) {

    fun countAllUsers() = sqlClient.countAll<PostgresqlUser>()

    fun countUsersWithAlias() = sqlClient.select { count { it[PostgresqlUser::alias] } }.fetchOne()

    fun selectOneNonUnique() = sqlClient.select<PostgresqlUser>()
            .fetchOne()

    fun selectAllMappedToDto() =
            sqlClient.select {
                UserDto("${it[PostgresqlUser::firstname]} ${it[PostgresqlUser::lastname]}",
                        it[PostgresqlUser::alias])
            }.fetchAll()

    fun selectWithJoin() =
            sqlClient.select { UserWithRoleDto(it[PostgresqlUser::lastname], it[PostgresqlRole::label]) }
                    .innerJoin<PostgresqlRole>().on { it[PostgresqlUser::roleId] }
                    .fetchAll()
}
*/