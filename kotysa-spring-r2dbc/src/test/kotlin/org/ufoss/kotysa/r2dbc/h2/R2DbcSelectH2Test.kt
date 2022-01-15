/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.NonUniqueResultException
import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.*
import reactor.kotlin.test.test


class R2DbcSelectH2Test : AbstractR2dbcH2Test<UserRepositoryH2Select>() {
    override val context = startContext<UserRepositoryH2Select>()
    override val repository = getContextRepository<UserRepositoryH2Select>()

    @Test
    fun `Verify selectAllUsers returns all users`() {
        assertThat(repository.selectAllUsers().toIterable())
            .hasSize(2)
            .containsExactlyInAnyOrder(userJdoe, userBboss)
    }

    @Test
    fun `Verify countAllUsersAndAliases returns all users' count`() {
        assertThat(repository.countAllUsersAndAliases().block())
            .isEqualTo(Pair(2L, 1L))
    }

    @Test
    fun `Verify selectOneNonUnique throws NonUniqueResultException`() {
        assertThatThrownBy { repository.selectOneNonUnique().block() }
            .isInstanceOf(NonUniqueResultException::class.java)
    }

    @Test
    fun `Verify selectAllMappedToDto does the mapping`() {
        assertThat(repository.selectAllMappedToDto().toIterable())
            .hasSize(2)
            .containsExactlyInAnyOrder(
                UserDto("John Doe", null),
                UserDto("Big Boss", "TheBoss")
            )
    }

    @Test
    fun `Verify selectWithJoin works correctly`() {
        assertThat(repository.selectWithJoin().toIterable())
            .hasSize(2)
            .containsExactlyInAnyOrder(
                UserWithRoleDto(userJdoe.lastname, roleUser.label),
                UserWithRoleDto(userBboss.lastname, roleAdmin.label)
            )
    }

    @Test
    fun `Verify selectWithEqJoin works correctly`() {
        assertThat(repository.selectWithEqJoin().toIterable())
            .hasSize(2)
            .containsExactlyInAnyOrder(
                UserWithRoleDto(userJdoe.lastname, roleUser.label),
                UserWithRoleDto(userBboss.lastname, roleAdmin.label)
            )
    }

    @Test
    fun `Verify selectAllIn returns TheBoss`() {
        assertThat(repository.selectAllIn(setOf("TheBoss", "TheStar", "TheBest")).toIterable())
            .hasSize(1)
            .containsExactly(userBboss)
    }

    @Test
    fun `Verify selectAllIn returns no result`() {
        val coll = ArrayDeque<String>()
        coll.addFirst("TheStar")
        coll.addLast("TheBest")
        assertThat(repository.selectAllIn(coll).toIterable())
            .isEmpty()
    }

    @Test
    fun `Verify selectOneById returns TheBoss`() {
        assertThat(repository.selectOneById(userBboss.id).block())
            .isEqualTo(userBboss)
    }

    @Test
    fun `Verify selectOneById finds no result for -1`() {
        repository.selectOneById(-1)
            .test()
            .verifyComplete()
    }

    @Test
    fun `Verify selectFirstnameById returns TheBoss firstname`() {
        assertThat(repository.selectFirstnameById(userBboss.id).block())
            .isEqualTo(userBboss.firstname)
    }

    @Test
    fun `Verify selectAliasById returns null as J Doe alias`() {
        repository.selectAliasById(userJdoe.id)
            .test()
            .verifyComplete()
    }

    @Test
    fun `Verify selectFirstnameAndAliasById returns J Doe firstname and alias`() {
        assertThat(repository.selectFirstnameAndAliasById(userJdoe.id).block())
            .isEqualTo(Pair(userJdoe.firstname, null))
    }

    @Test
    fun `Verify selectAllFirstnameAndAlias returns all users firstname and alias`() {
        assertThat(repository.selectAllFirstnameAndAlias().toIterable())
            .hasSize(2)
            .containsExactlyInAnyOrder(
                Pair(userJdoe.firstname, null),
                Pair(userBboss.firstname, userBboss.alias),
            )
    }

    @Test
    fun `Verify selectFirstnameAndLastnameAndAliasById returns J Doe firstname, lastname and alias`() {
        assertThat(repository.selectFirstnameAndLastnameAndAliasById(userJdoe.id).block())
            .isEqualTo(Triple(userJdoe.firstname, userJdoe.lastname, null))
    }

    @Test
    fun `Verify selectFirstnameAndLastnameAndAliasAndIsAdminById returns J Doe firstname, lastname, alias and isAdmin`() {
        assertThat(repository.selectFirstnameAndLastnameAndAliasAndIsAdminById(userJdoe.id).block())
            .isEqualTo(listOf(userJdoe.firstname, userJdoe.lastname, null, false))
    }

    @Test
    fun `Verify selectRoleLabelFromUserId returns Admin role for TheBoss`() {
        assertThat(repository.selectRoleLabelFromUserId(userBboss.id).block())
            .isEqualTo(roleAdmin.label)
    }

    @Test
    fun `Verify countAllUsers returns 2`() {
        assertThat(repository.countAllUsers().block()!!)
            .isEqualTo(2L)
    }

    @Test
    fun `Verify selectRoleLabelsFromUserId returns Admin role for TheBoss`() {
        assertThat(repository.selectRoleLabelsFromUserId(userBboss.id).toIterable())
            .hasSize(1)
            .containsExactly(roleAdmin.label)
    }
}


class UserRepositoryH2Select(
    sqlClient: ReactorSqlClient,
) : AbstractUserRepositoryH2(sqlClient) {

    fun selectOneNonUnique() =
        (sqlClient selectFrom H2_USER
                ).fetchOne()

    fun selectAllMappedToDto() =
        (sqlClient select {
            UserDto("${it[H2_USER.firstname]} ${it[H2_USER.lastname]}", it[H2_USER.alias])
        }
                from H2_USER
                ).fetchAll()

    fun selectWithJoin() =
        (sqlClient select { UserWithRoleDto(it[H2_USER.lastname]!!, it[H2_ROLE.label]!!) }
                from H2_USER innerJoin H2_ROLE on H2_USER.roleId eq H2_ROLE.id
                ).fetchAll()

    fun selectWithEqJoin() =
        (sqlClient select { UserWithRoleDto(it[H2_USER.lastname]!!, it[H2_ROLE.label]!!) }
                from H2_USER and H2_ROLE
                where H2_USER.roleId eq H2_ROLE.id
                ).fetchAll()

    fun selectAllIn(aliases: Collection<String>) =
        (sqlClient selectFrom H2_USER
                where H2_USER.alias `in` aliases
                ).fetchAll()

    fun selectOneById(id: Int) =
        (sqlClient select H2_USER
                from H2_USER
                where H2_USER.id eq id
                ).fetchOne()

    fun selectFirstnameById(id: Int) =
        (sqlClient select H2_USER.firstname
                from H2_USER
                where H2_USER.id eq id
                ).fetchOne()

    fun selectAliasById(id: Int) =
        (sqlClient select H2_USER.alias
                from H2_USER
                where H2_USER.id eq id
                ).fetchOne()

    fun selectFirstnameAndAliasById(id: Int) =
        (sqlClient select H2_USER.firstname and H2_USER.alias
                from H2_USER
                where H2_USER.id eq id
                ).fetchOne()

    fun selectAllFirstnameAndAlias() =
        (sqlClient select H2_USER.firstname and H2_USER.alias
                from H2_USER
                ).fetchAll()

    fun selectFirstnameAndLastnameAndAliasById(id: Int) =
        (sqlClient select H2_USER.firstname and H2_USER.lastname and H2_USER.alias
                from H2_USER
                where H2_USER.id eq id
                ).fetchOne()

    fun selectFirstnameAndLastnameAndAliasAndIsAdminById(id: Int) =
        (sqlClient select H2_USER.firstname and H2_USER.lastname and H2_USER.alias and H2_USER.isAdmin
                from H2_USER
                where H2_USER.id eq id
                ).fetchOne()

    fun countAllUsersAndAliases() =
        (sqlClient selectCount H2_USER.id
                andCount H2_USER.alias
                from H2_USER
                ).fetchOne()

    fun selectRoleLabelFromUserId(userId: Int) =
        (sqlClient select H2_ROLE.label
                from H2_ROLE innerJoin H2_USER on H2_ROLE.id eq H2_USER.roleId
                where H2_USER.id eq userId)
            .fetchOne()

    fun countAllUsers() = sqlClient selectCountAllFrom H2_USER

    fun selectRoleLabelsFromUserId(userId: Int) =
        (sqlClient select H2_ROLE.label
                from H2_USER_ROLE innerJoin H2_ROLE on H2_USER_ROLE.roleId eq H2_ROLE.id
                where H2_USER_ROLE.userId eq userId)
            .fetchAll()
}
