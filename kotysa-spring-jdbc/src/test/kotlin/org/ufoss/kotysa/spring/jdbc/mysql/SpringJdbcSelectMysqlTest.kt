/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.NonUniqueResultException
import org.ufoss.kotysa.count
import org.ufoss.kotysa.test.*


class SpringJdbcSelectMysqlTest : AbstractSpringJdbcMysqlTest<UserRepositorySpringJdbcMysqlSelect>() {
    override val context = startContext<UserRepositorySpringJdbcMysqlSelect>()

    override val repository = getContextRepository<UserRepositorySpringJdbcMysqlSelect>()

    @Test
    fun `Verify selectAll returns all users`() {
        assertThat(repository.selectAllUsers())
                .hasSize(2)
                .containsExactlyInAnyOrder(mysqlJdoe, mysqlBboss)
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
                        UserDto("${mysqlJdoe.firstname} ${mysqlJdoe.lastname}", mysqlJdoe.alias),
                        UserDto("${mysqlBboss.firstname} ${mysqlBboss.lastname}", mysqlBboss.alias))
    }

    @Test
    fun `Verify selectWithJoin works correctly`() {
        assertThat(repository.selectWithJoin())
                .hasSize(2)
                .containsExactlyInAnyOrder(
                        UserWithRoleDto(mysqlJdoe.lastname, mysqlUser.label),
                        UserWithRoleDto(mysqlBboss.lastname, mysqlAdmin.label)
                )
    }
}


class UserRepositorySpringJdbcMysqlSelect(client: JdbcOperations) : AbstractUserRepositorySpringJdbcMysql(client) {

    fun countAllUsers() = sqlClient.countAll<MysqlUser>()

    fun countUsersWithAlias() = sqlClient.select { count { it[MysqlUser::alias] } }.fetchOne()

    fun selectOneNonUnique() = sqlClient.select<MysqlUser>()
            .fetchOne()

    fun selectAllMappedToDto() =
            sqlClient.select {
                UserDto("${it[MysqlUser::firstname]} ${it[MysqlUser::lastname]}",
                        it[MysqlUser::alias])
            }.fetchAll()

    fun selectWithJoin() =
            sqlClient.select { UserWithRoleDto(it[MysqlUser::lastname], it[MysqlRole::label]) }
                    .innerJoin<MysqlRole>().on { it[MysqlUser::roleId] }
                    .fetchAll()
}
