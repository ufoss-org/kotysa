/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcTemplate
import org.ufoss.kotysa.NonUniqueResultException
import org.ufoss.kotysa.count
import org.ufoss.kotysa.test.*


class SpringJdbcSelectH2Test : AbstractSpringJdbcH2Test<UserRepositorySpringJdbcH2Select>() {
    override val context = startContext<UserRepositorySpringJdbcH2Select>()

    override val repository = getContextRepository<UserRepositorySpringJdbcH2Select>()

    @Test
    fun `Verify selectAll returns all users`() {
        assertThat(repository.selectAllUsers())
                .hasSize(2)
                .containsExactlyInAnyOrder(h2Jdoe, h2Bboss)
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
                        UserDto("${h2Jdoe.firstname} ${h2Jdoe.lastname}", h2Jdoe.alias),
                        UserDto("${h2Bboss.firstname} ${h2Bboss.lastname}", h2Bboss.alias))
    }

    @Test
    fun `Verify selectWithJoin works correctly`() {
        assertThat(repository.selectWithJoin())
                .hasSize(2)
                .containsExactlyInAnyOrder(
                        UserWithRoleDto(h2Jdoe.lastname, h2User.label),
                        UserWithRoleDto(h2Bboss.lastname, h2Admin.label)
                )
    }
}


class UserRepositorySpringJdbcH2Select(client: JdbcTemplate) : AbstractUserRepositorySpringJdbcH2(client) {

    fun countAllUsers() = sqlClient.countAll<H2User>()

    fun countUsersWithAlias() = sqlClient.select { count { it[H2User::alias] } }.fetchOne()

    fun selectOneNonUnique() = sqlClient.select<H2User>()
            .fetchOne()

    fun selectAllMappedToDto() =
            sqlClient.select {
                UserDto("${it[H2User::firstname]} ${it[H2User::lastname]}",
                        it[H2User::alias])
            }.fetchAll()

    fun selectWithJoin() =
            sqlClient.select { UserWithRoleDto(it[H2User::lastname], it[H2Role::label]) }
                    .innerJoin<H2Role>().on { it[H2User::roleId] }
                    .fetchAll()
}
