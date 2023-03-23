/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.MysqlCoroutinesSqlClient
import org.ufoss.kotysa.MysqlReactorSqlClient
import org.ufoss.kotysa.NonUniqueResultException
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.*
import reactor.kotlin.test.test


class R2DbcSelectMysqlTest : AbstractR2dbcMysqlTest<UserRepositoryMysqlSelect>() {

    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        UserRepositoryMysqlSelect(sqlClient)

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


class UserRepositoryMysqlSelect(sqlClient: ReactorSqlClient) : AbstractUserRepositoryMysql(sqlClient) {

    fun selectOneNonUnique() =
        (sqlClient selectFrom MysqlUsers
                ).fetchOne()

    fun selectAllMappedToDto() =
        (sqlClient selectAndBuild {
            UserDto(
                "${it[MysqlUsers.firstname]} ${it[MysqlUsers.lastname]}", it[MysqlUsers.isAdmin]!!,
                it[MysqlUsers.alias]
            )
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

    fun selectOneById(id: Int) =
        (sqlClient select MysqlUsers
                from MysqlUsers
                where MysqlUsers.id eq id
                ).fetchOne()

    fun selectFirstnameById(id: Int) =
        (sqlClient select MysqlUsers.firstname
                from MysqlUsers
                where MysqlUsers.id eq id
                ).fetchOne()

    fun selectAliasById(id: Int) =
        (sqlClient select MysqlUsers.alias
                from MysqlUsers
                where MysqlUsers.id eq id
                ).fetchOne()

    fun selectFirstnameAndAliasById(id: Int) =
        (sqlClient select MysqlUsers.firstname and MysqlUsers.alias
                from MysqlUsers
                where MysqlUsers.id eq id
                ).fetchOne()

    fun selectAllFirstnameAndAlias() =
        (sqlClient select MysqlUsers.firstname and MysqlUsers.alias
                from MysqlUsers
                ).fetchAll()

    fun selectFirstnameAndLastnameAndAliasById(id: Int) =
        (sqlClient select MysqlUsers.firstname and MysqlUsers.lastname and MysqlUsers.alias
                from MysqlUsers
                where MysqlUsers.id eq id
                ).fetchOne()

    fun selectFirstnameAndLastnameAndAliasAndIsAdminById(id: Int) =
        (sqlClient select MysqlUsers.firstname and MysqlUsers.lastname and MysqlUsers.alias and MysqlUsers.isAdmin
                from MysqlUsers
                where MysqlUsers.id eq id
                ).fetchOne()

    fun countAllUsersAndAliases() =
        (sqlClient selectCount MysqlUsers.id
                andCount MysqlUsers.alias
                from MysqlUsers
                ).fetchOne()

    fun selectRoleLabelFromUserId(userId: Int) =
        (sqlClient select MysqlRoles.label
                from MysqlRoles innerJoin MysqlUsers on MysqlRoles.id eq MysqlUsers.roleId
                where MysqlUsers.id eq userId)
            .fetchOne()

    fun countAllUsers() = sqlClient selectCountAllFrom MysqlUsers

    fun selectRoleLabelsFromUserId(userId: Int) =
        (sqlClient select MysqlRoles.label
                from MysqlUserRoles innerJoin MysqlRoles on MysqlUserRoles.roleId eq MysqlRoles.id
                where MysqlUserRoles.userId eq userId)
            .fetchAll()
}
