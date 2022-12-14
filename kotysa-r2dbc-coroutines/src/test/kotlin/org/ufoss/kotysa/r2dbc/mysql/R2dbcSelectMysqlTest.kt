/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.NoResultException
import org.ufoss.kotysa.NonUniqueResultException
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.test.*

class R2dbcSelectMysqlTest : AbstractR2dbcMysqlTest<UserRepositoryJdbcMysqlSelect>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryJdbcMysqlSelect(sqlClient)

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
                UserDto("John Doe", false, null),
                UserDto("Big Boss", true, "TheBoss")
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


class UserRepositoryJdbcMysqlSelect(private val sqlClient: R2dbcSqlClient) :
    AbstractUserRepositoryR2dbcMysql(sqlClient) {

    suspend fun selectOneNonUnique() =
        (sqlClient selectFrom MysqlUsers
                ).fetchOne()

    fun selectAllMappedToDto() =
        (sqlClient selectAndBuild {
            UserDto("${it[MysqlUsers.firstname]} ${it[MysqlUsers.lastname]}", it[MysqlUsers.isAdmin]!!,
                it[MysqlUsers.alias])
        }
                from MysqlUsers
                ).fetchAll()

    fun selectWithJoin() =
        (sqlClient selectAndBuild { UserWithRoleDto(it[MysqlUsers.lastname]!!, it[MysqlRoles.label]!!) }
                from MysqlUsers innerJoin MysqlRoles on MysqlUsers.roleId eq MysqlRoles.id
                ).fetchAll()

    fun selectWithEqJoin() =
        (sqlClient selectAndBuild { UserWithRoleDto(it[MysqlUsers.lastname]!!, it[MysqlRoles.label]!!) }
                from MysqlUsers and MysqlRoles
                where MysqlUsers.roleId eq MysqlRoles.id
                ).fetchAll()

    fun selectAllIn(aliases: Collection<String>) =
        (sqlClient selectFrom MysqlUsers
                where MysqlUsers.alias `in` aliases
                ).fetchAll()

    suspend fun selectOneById(id: Int) =
        (sqlClient select MysqlUsers
                from MysqlUsers
                where MysqlUsers.id eq id
                ).fetchOne()

    suspend fun selectFirstnameById(id: Int) =
        (sqlClient select MysqlUsers.firstname
                from MysqlUsers
                where MysqlUsers.id eq id
                ).fetchOne()

    suspend fun selectAliasById(id: Int) =
        (sqlClient select MysqlUsers.alias
                from MysqlUsers
                where MysqlUsers.id eq id
                ).fetchOneOrNull()

    suspend fun selectFirstnameAndAliasById(id: Int) =
        (sqlClient select MysqlUsers.firstname and MysqlUsers.alias
                from MysqlUsers
                where MysqlUsers.id eq id
                ).fetchOne()

    fun selectAllFirstnameAndAlias() =
        (sqlClient select MysqlUsers.firstname and MysqlUsers.alias
                from MysqlUsers
                ).fetchAll()

    suspend fun selectFirstnameAndLastnameAndAliasById(id: Int) =
        (sqlClient select MysqlUsers.firstname and MysqlUsers.lastname and MysqlUsers.alias
                from MysqlUsers
                where MysqlUsers.id eq id
                ).fetchOne()

    suspend fun selectFirstnameAndLastnameAndAliasAndIsAdminById(id: Int) =
        (sqlClient select MysqlUsers.firstname and MysqlUsers.lastname and MysqlUsers.alias and MysqlUsers.isAdmin
                from MysqlUsers
                where MysqlUsers.id eq id
                ).fetchOne()

    suspend fun countAllUsersAndAliases() =
        (sqlClient selectCount MysqlUsers.id
                andCount MysqlUsers.alias
                from MysqlUsers
                ).fetchOne()

    suspend fun selectRoleLabelFromUserId(userId: Int) =
        (sqlClient select MysqlRoles.label
                from MysqlRoles innerJoin MysqlUsers on MysqlRoles.id eq MysqlUsers.roleId
                where MysqlUsers.id eq userId)
            .fetchOne()

    suspend fun countAllUsers() = sqlClient selectCountAllFrom MysqlUsers

    fun selectRoleLabelsFromUserId(userId: Int) =
        (sqlClient select MysqlRoles.label
                from MysqlUserRoles innerJoin MysqlRoles on MysqlUserRoles.roleId eq MysqlRoles.id
                where MysqlUserRoles.userId eq userId)
            .fetchAll()
}
