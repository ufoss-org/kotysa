/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.NoResultException
import org.ufoss.kotysa.NonUniqueResultException
import org.ufoss.kotysa.r2dbc.R2dbcSqlClient
import org.ufoss.kotysa.test.*

class R2dbcSelectMariadbTest : AbstractR2dbcMariadbTest<UserRepositoryJdbcMariadbSelect>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryJdbcMariadbSelect(sqlClient)

    @Test
    fun `Verify selectAllUsers returns all users`() = runTest {
        assertThat(repository.selectAllUsers().toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(userJdoe, userBboss)
    }

    @Test
    fun `Verify countAllUsersAndAliases returns all users' count`() = runTest {
        assertThat(repository.countAllUsersAndAliases())
            .isEqualTo(Pair(2L, 1L))
    }

    @Test
    fun `Verify selectOneNonUnique throws NonUniqueResultException`() = runTest {
        assertThatThrownBy {
            runBlocking {
                repository.selectOneNonUnique()
            }
        }.isInstanceOf(NonUniqueResultException::class.java)
    }

    @Test
    fun `Verify selectAllMappedToDto does the mapping`() = runTest {
        assertThat(repository.selectAllMappedToDto().toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(
                UserDto("John Doe", null),
                UserDto("Big Boss", "TheBoss")
            )
    }

    @Test
    fun `Verify selectWithJoin works correctly`() = runTest {
        assertThat(repository.selectWithJoin().toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(
                UserWithRoleDto(userJdoe.lastname, roleUser.label),
                UserWithRoleDto(userBboss.lastname, roleAdmin.label)
            )
    }

    @Test
    fun `Verify selectWithEqJoin works correctly`() = runTest {
        assertThat(repository.selectWithEqJoin().toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(
                UserWithRoleDto(userJdoe.lastname, roleUser.label),
                UserWithRoleDto(userBboss.lastname, roleAdmin.label)
            )
    }

    @Test
    fun `Verify selectAllIn returns TheBoss`() = runTest {
        assertThat(repository.selectAllIn(setOf("TheBoss", "TheStar", "TheBest")).toList())
            .hasSize(1)
            .containsExactly(userBboss)
    }

    @Test
    fun `Verify selectAllIn returns no result`() = runTest {
        val coll = ArrayDeque<String>()
        coll.addFirst("TheStar")
        coll.addLast("TheBest")
        assertThat(repository.selectAllIn(coll).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectOneById returns TheBoss`() = runTest {
        assertThat(repository.selectOneById(userBboss.id))
            .isEqualTo(userBboss)
    }

    @Test
    fun `Verify selectOneById finds no result for -1, throws NoResultException`() = runTest {
        assertThatThrownBy {
            runBlocking {
                repository.selectOneById(-1)
            }
        }.isInstanceOf(NoResultException::class.java)
    }

    @Test
    fun `Verify selectFirstnameById returns TheBoss firstname`() = runTest {
        assertThat(repository.selectFirstnameById(userBboss.id))
            .isEqualTo(userBboss.firstname)
    }

    @Test
    fun `Verify selectAliasById returns null as J Doe alias`() = runTest {
        assertThat(repository.selectAliasById(userJdoe.id))
            .isNull()
    }

    @Test
    fun `Verify selectFirstnameAndAliasById returns J Doe firstname and alias`() = runTest {
        assertThat(repository.selectFirstnameAndAliasById(userJdoe.id))
            .isEqualTo(Pair(userJdoe.firstname, null))
    }

    @Test
    fun `Verify selectAllFirstnameAndAlias returns all users firstname and alias`() = runTest {
        assertThat(repository.selectAllFirstnameAndAlias().toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(
                Pair(userJdoe.firstname, null),
                Pair(userBboss.firstname, userBboss.alias),
            )
    }

    @Test
    fun `Verify selectFirstnameAndLastnameAndAliasById returns J Doe firstname, lastname and alias`() = runTest {
        assertThat(repository.selectFirstnameAndLastnameAndAliasById(userJdoe.id))
            .isEqualTo(Triple(userJdoe.firstname, userJdoe.lastname, null))
    }

    @Test
    fun `Verify selectFirstnameAndLastnameAndAliasAndIsAdminById returns J Doe firstname, lastname, alias and isAdmin`() =
        runTest {
            assertThat(repository.selectFirstnameAndLastnameAndAliasAndIsAdminById(userJdoe.id))
                .isEqualTo(listOf(userJdoe.firstname, userJdoe.lastname, null, false))
        }

    @Test
    fun `Verify selectRoleLabelFromUserId returns Admin role for TheBoss`() = runTest {
        assertThat(repository.selectRoleLabelFromUserId(userBboss.id))
            .isEqualTo(roleAdmin.label)
    }

    @Test
    fun `Verify countAllUsers returns 2`() = runTest {
        assertThat(repository.countAllUsers())
            .isEqualTo(2L)
    }

    @Test
    fun `Verify selectRoleLabelsFromUserId returns Admin role for TheBoss`() = runTest {
        assertThat(repository.selectRoleLabelsFromUserId(userBboss.id).toList())
            .hasSize(1)
            .containsExactly(roleAdmin.label)
    }
}


class UserRepositoryJdbcMariadbSelect(private val sqlClient: R2dbcSqlClient) :
    AbstractUserRepositoryR2dbcMariadb(sqlClient) {

    suspend fun selectOneNonUnique() =
        (sqlClient selectFrom MariadbUsers
                ).fetchOne()

    fun selectAllMappedToDto() =
        (sqlClient select {
            UserDto("${it[MariadbUsers.firstname]} ${it[MariadbUsers.lastname]}", it[MariadbUsers.alias])
        }
                from MariadbUsers
                ).fetchAll()

    fun selectWithJoin() =
        (sqlClient select { UserWithRoleDto(it[MariadbUsers.lastname]!!, it[MariadbRoles.label]!!) }
                from MariadbUsers innerJoin MariadbRoles on MariadbUsers.roleId eq MariadbRoles.id
                ).fetchAll()

    fun selectWithEqJoin() =
        (sqlClient select { UserWithRoleDto(it[MariadbUsers.lastname]!!, it[MariadbRoles.label]!!) }
                from MariadbUsers and MariadbRoles
                where MariadbUsers.roleId eq MariadbRoles.id
                ).fetchAll()

    fun selectAllIn(aliases: Collection<String>) =
        (sqlClient selectFrom MariadbUsers
                where MariadbUsers.alias `in` aliases
                ).fetchAll()

    suspend fun selectOneById(id: Int) =
        (sqlClient select MariadbUsers
                from MariadbUsers
                where MariadbUsers.id eq id
                ).fetchOne()

    suspend fun selectFirstnameById(id: Int) =
        (sqlClient select MariadbUsers.firstname
                from MariadbUsers
                where MariadbUsers.id eq id
                ).fetchOne()

    suspend fun selectAliasById(id: Int) =
        (sqlClient select MariadbUsers.alias
                from MariadbUsers
                where MariadbUsers.id eq id
                ).fetchOneOrNull()

    suspend fun selectFirstnameAndAliasById(id: Int) =
        (sqlClient select MariadbUsers.firstname and MariadbUsers.alias
                from MariadbUsers
                where MariadbUsers.id eq id
                ).fetchOne()

    fun selectAllFirstnameAndAlias() =
        (sqlClient select MariadbUsers.firstname and MariadbUsers.alias
                from MariadbUsers
                ).fetchAll()

    suspend fun selectFirstnameAndLastnameAndAliasById(id: Int) =
        (sqlClient select MariadbUsers.firstname and MariadbUsers.lastname and MariadbUsers.alias
                from MariadbUsers
                where MariadbUsers.id eq id
                ).fetchOne()

    suspend fun selectFirstnameAndLastnameAndAliasAndIsAdminById(id: Int) =
        (sqlClient select MariadbUsers.firstname and MariadbUsers.lastname and MariadbUsers.alias and MariadbUsers.isAdmin
                from MariadbUsers
                where MariadbUsers.id eq id
                ).fetchOne()

    suspend fun countAllUsersAndAliases() =
        (sqlClient selectCount MariadbUsers.id
                andCount MariadbUsers.alias
                from MariadbUsers
                ).fetchOne()

    suspend fun selectRoleLabelFromUserId(userId: Int) =
        (sqlClient select MariadbRoles.label
                from MariadbRoles innerJoin MariadbUsers on MariadbRoles.id eq MariadbUsers.roleId
                where MariadbUsers.id eq userId)
            .fetchOne()

    suspend fun countAllUsers() = sqlClient selectCountAllFrom MariadbUsers

    fun selectRoleLabelsFromUserId(userId: Int) =
        (sqlClient select MariadbRoles.label
                from MariadbUserRoles innerJoin MariadbRoles on MariadbUserRoles.roleId eq MariadbRoles.id
                where MariadbUserRoles.userId eq userId)
            .fetchAll()
}
