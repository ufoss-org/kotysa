/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.NonUniqueResultException
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import reactor.kotlin.test.test


class R2DbcSelectMariadbTest : AbstractR2dbcMariadbTest<UserRepositoryMariadbSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositoryMariadbSelect>(resource)
    }

    override val repository: UserRepositoryMariadbSelect by lazy {
        getContextRepository()
    }

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
                UserDto("John Doe", false, null),
                UserDto("Big Boss", true, "TheBoss")
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
        assertThat(repository.selectAliasById(userJdoe.id).block())
            .isNull()
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


class UserRepositoryMariadbSelect(sqlClient: ReactorSqlClient) : AbstractUserRepositoryMariadb(sqlClient) {

    fun selectOneNonUnique() =
        (sqlClient selectFrom MariadbUsers
                ).fetchOne()

    fun selectAllMappedToDto() =
        (sqlClient selectAndBuild {
            UserDto("${it[MariadbUsers.firstname]} ${it[MariadbUsers.lastname]}", it[MariadbUsers.isAdmin]!!,
                it[MariadbUsers.alias])
        }
                from MariadbUsers
                ).fetchAll()

    fun selectWithJoin() =
        (sqlClient selectAndBuild { UserWithRoleDto(it[MariadbUsers.lastname]!!, it[MariadbRoles.label]!!) }
                from MariadbUsers innerJoin MariadbRoles on MariadbUsers.roleId eq MariadbRoles.id
                ).fetchAll()

    fun selectWithEqJoin() =
        (sqlClient selectAndBuild { UserWithRoleDto(it[MariadbUsers.lastname]!!, it[MariadbRoles.label]!!) }
                from MariadbUsers and MariadbRoles
                where MariadbUsers.roleId eq MariadbRoles.id
                ).fetchAll()

    fun selectAllIn(aliases: Collection<String>) =
        (sqlClient selectFrom MariadbUsers
                where MariadbUsers.alias `in` aliases
                ).fetchAll()

    fun selectOneById(id: Int) =
        (sqlClient select MariadbUsers
                from MariadbUsers
                where MariadbUsers.id eq id
                ).fetchOne()

    fun selectFirstnameById(id: Int) =
        (sqlClient select MariadbUsers.firstname
                from MariadbUsers
                where MariadbUsers.id eq id
                ).fetchOne()

    fun selectAliasById(id: Int) =
        (sqlClient select MariadbUsers.alias
                from MariadbUsers
                where MariadbUsers.id eq id
                ).fetchOne()

    fun selectFirstnameAndAliasById(id: Int) =
        (sqlClient select MariadbUsers.firstname and MariadbUsers.alias
                from MariadbUsers
                where MariadbUsers.id eq id
                ).fetchOne()

    fun selectAllFirstnameAndAlias() =
        (sqlClient select MariadbUsers.firstname and MariadbUsers.alias
                from MariadbUsers
                ).fetchAll()

    fun selectFirstnameAndLastnameAndAliasById(id: Int) =
        (sqlClient select MariadbUsers.firstname and MariadbUsers.lastname and MariadbUsers.alias
                from MariadbUsers
                where MariadbUsers.id eq id
                ).fetchOne()

    fun selectFirstnameAndLastnameAndAliasAndIsAdminById(id: Int) =
        (sqlClient select MariadbUsers.firstname and MariadbUsers.lastname and MariadbUsers.alias and MariadbUsers.isAdmin
                from MariadbUsers
                where MariadbUsers.id eq id
                ).fetchOne()

    fun countAllUsersAndAliases() =
        (sqlClient selectCount MariadbUsers.id
                andCount MariadbUsers.alias
                from MariadbUsers
                ).fetchOne()

    fun selectRoleLabelFromUserId(userId: Int) =
        (sqlClient select MariadbRoles.label
                from MariadbRoles innerJoin MariadbUsers on MariadbRoles.id eq MariadbUsers.roleId
                where MariadbUsers.id eq userId)
            .fetchOne()

    fun countAllUsers() = sqlClient selectCountAllFrom MariadbUsers

    fun selectRoleLabelsFromUserId(userId: Int) =
        (sqlClient select MariadbRoles.label
                from MariadbUserRoles innerJoin MariadbRoles on MariadbUserRoles.roleId eq MariadbRoles.id
                where MariadbUserRoles.userId eq userId)
            .fetchAll()
}
