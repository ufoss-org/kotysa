/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.NoResultException
import org.ufoss.kotysa.NonUniqueResultException
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource


class SpringJdbcSelectMssqlTest : AbstractSpringJdbcMssqlTest<UserRepositorySpringJdbcMssqlSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositorySpringJdbcMssqlSelect>(resource)
    }

    override val repository: UserRepositorySpringJdbcMssqlSelect by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify selectAllUsers returns all users`() {
        assertThat(repository.selectAllUsers())
            .hasSize(2)
            .containsExactlyInAnyOrder(userJdoe, userBboss)
    }

    @Test
    fun `Verify countAllUsersAndAliases returns all users' count`() {
        assertThat(repository.countAllUsersAndAliases())
            .isEqualTo(Pair(2L, 1L))
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
                UserDto("John Doe", null),
                UserDto("Big Boss", "TheBoss")
            )
    }

    @Test
    fun `Verify selectWithJoin works correctly`() {
        assertThat(repository.selectWithJoin())
            .hasSize(2)
            .containsExactlyInAnyOrder(
                UserWithRoleDto(userJdoe.lastname, roleUser.label),
                UserWithRoleDto(userBboss.lastname, roleAdmin.label)
            )
    }

    @Test
    fun `Verify selectWithEqJoin works correctly`() {
        assertThat(repository.selectWithEqJoin())
            .hasSize(2)
            .containsExactlyInAnyOrder(
                UserWithRoleDto(userJdoe.lastname, roleUser.label),
                UserWithRoleDto(userBboss.lastname, roleAdmin.label)
            )
    }

    @Test
    fun `Verify selectAllStream returns all users`() {
        assertThat(repository.selectAllStream())
            .hasSize(2)
            .containsExactlyInAnyOrder(userJdoe, userBboss)
    }

    @Test
    fun `Verify selectAllIn returns TheBoss`() {
        assertThat(repository.selectAllIn(setOf("TheBoss", "TheStar", "TheBest")))
            .hasSize(1)
            .containsExactly(userBboss)
    }

    @Test
    fun `Verify selectAllIn returns no result`() {
        val coll = ArrayDeque<String>()
        coll.addFirst("TheStar")
        coll.addLast("TheBest")
        assertThat(repository.selectAllIn(coll))
            .isEmpty()
    }

    @Test
    fun `Verify selectOneById returns TheBoss`() {
        assertThat(repository.selectOneById(userBboss.id))
            .isEqualTo(userBboss)
    }

    @Test
    fun `Verify selectOneById finds no result for -1, throws NoResultException`() {
        assertThatThrownBy { repository.selectOneById(-1) }
            .isInstanceOf(NoResultException::class.java)
    }

    @Test
    fun `Verify selectFirstnameById returns TheBoss firstname`() {
        assertThat(repository.selectFirstnameById(userBboss.id))
            .isEqualTo(userBboss.firstname)
    }

    @Test
    fun `Verify selectAliasById returns null as J Doe alias`() {
        assertThat(repository.selectAliasById(userJdoe.id))
            .isNull()
    }

    @Test
    fun `Verify selectFirstnameAndAliasById returns J Doe firstname and alias`() {
        assertThat(repository.selectFirstnameAndAliasById(userJdoe.id))
            .isEqualTo(Pair(userJdoe.firstname, null))
    }

    @Test
    fun `Verify selectAllFirstnameAndAlias returns all users firstname and alias`() {
        assertThat(repository.selectAllFirstnameAndAlias())
            .hasSize(2)
            .containsExactlyInAnyOrder(
                Pair(userJdoe.firstname, null),
                Pair(userBboss.firstname, userBboss.alias),
            )
    }

    @Test
    fun `Verify selectFirstnameAndLastnameAndAliasById returns J Doe firstname, lastname and alias`() {
        assertThat(repository.selectFirstnameAndLastnameAndAliasById(userJdoe.id))
            .isEqualTo(Triple(userJdoe.firstname, userJdoe.lastname, null))
    }

    @Test
    fun `Verify selectFirstnameAndLastnameAndAliasAndIsAdminById returns J Doe firstname, lastname, alias and isAdmin`() {
        assertThat(repository.selectFirstnameAndLastnameAndAliasAndIsAdminById(userJdoe.id))
            .isEqualTo(listOf(userJdoe.firstname, userJdoe.lastname, null, false))
    }

    @Test
    fun `Verify selectRoleLabelFromUserId returns Admin role for TheBoss`() {
        assertThat(repository.selectRoleLabelFromUserId(userBboss.id))
            .isEqualTo(roleAdmin.label)
    }

    @Test
    fun `Verify countAllUsers returns 2`() {
        assertThat(repository.countAllUsers())
            .isEqualTo(2L)
    }

    @Test
    fun `Verify selectRoleLabelsFromUserId returns Admin role for TheBoss`() {
        assertThat(repository.selectRoleLabelsFromUserId(userBboss.id))
            .hasSize(1)
            .containsExactly(roleAdmin.label)
    }
}


class UserRepositorySpringJdbcMssqlSelect(client: JdbcOperations) : AbstractUserRepositorySpringJdbcMssql(client) {

    fun selectOneNonUnique() =
        (sqlClient selectFrom MssqlUsers
                ).fetchOne()

    fun selectAllMappedToDto() =
        (sqlClient selectAndBuild {
            UserDto("${it[MssqlUsers.firstname]} ${it[MssqlUsers.lastname]}", it[MssqlUsers.alias])
        }
                from MssqlUsers
                ).fetchAll()

    fun selectWithJoin() =
        (sqlClient selectAndBuild { UserWithRoleDto(it[MssqlUsers.lastname]!!, it[MssqlRoles.label]!!) }
                from MssqlUsers innerJoin MssqlRoles on MssqlUsers.roleId eq MssqlRoles.id
                ).fetchAll()

    fun selectWithEqJoin() =
        (sqlClient selectAndBuild { UserWithRoleDto(it[MssqlUsers.lastname]!!, it[MssqlRoles.label]!!) }
                from MssqlUsers and MssqlRoles
                where MssqlUsers.roleId eq MssqlRoles.id
                ).fetchAll()

    fun selectAllStream() =
        (sqlClient selectFrom MssqlUsers
                ).fetchAllStream()

    fun selectAllIn(aliases: Collection<String>) =
        (sqlClient selectFrom MssqlUsers
                where MssqlUsers.alias `in` aliases
                ).fetchAll()

    fun selectOneById(id: Int) =
        (sqlClient select MssqlUsers
                from MssqlUsers
                where MssqlUsers.id eq id
                ).fetchOne()

    fun selectFirstnameById(id: Int) =
        (sqlClient select MssqlUsers.firstname
                from MssqlUsers
                where MssqlUsers.id eq id
                ).fetchOne()

    fun selectAliasById(id: Int) =
        (sqlClient select MssqlUsers.alias
                from MssqlUsers
                where MssqlUsers.id eq id
                ).fetchOneOrNull()

    fun selectFirstnameAndAliasById(id: Int) =
        (sqlClient select MssqlUsers.firstname and MssqlUsers.alias
                from MssqlUsers
                where MssqlUsers.id eq id
                ).fetchOne()

    fun selectAllFirstnameAndAlias() =
        (sqlClient select MssqlUsers.firstname and MssqlUsers.alias
                from MssqlUsers
                ).fetchAll()

    fun selectFirstnameAndLastnameAndAliasById(id: Int) =
        (sqlClient select MssqlUsers.firstname and MssqlUsers.lastname and MssqlUsers.alias
                from MssqlUsers
                where MssqlUsers.id eq id
                ).fetchOne()

    fun selectFirstnameAndLastnameAndAliasAndIsAdminById(id: Int) =
        (sqlClient select MssqlUsers.firstname and MssqlUsers.lastname and MssqlUsers.alias and MssqlUsers.isAdmin
                from MssqlUsers
                where MssqlUsers.id eq id
                ).fetchOne()

    fun countAllUsersAndAliases() =
        (sqlClient selectCount MssqlUsers.id
                andCount MssqlUsers.alias
                from MssqlUsers
                ).fetchOne()

    fun selectRoleLabelFromUserId(userId: Int) =
        (sqlClient select MssqlRoles.label
                from MssqlRoles innerJoin MssqlUsers on MssqlRoles.id eq MssqlUsers.roleId
                where MssqlUsers.id eq userId)
            .fetchOne()

    fun countAllUsers() = sqlClient selectCountAllFrom MssqlUsers

    fun selectRoleLabelsFromUserId(userId: Int) =
        (sqlClient select MssqlRoles.label
                from MssqlUserRoles innerJoin MssqlRoles on MssqlUserRoles.roleId eq MssqlRoles.id
                where MssqlUserRoles.userId eq userId)
            .fetchAll()
}
