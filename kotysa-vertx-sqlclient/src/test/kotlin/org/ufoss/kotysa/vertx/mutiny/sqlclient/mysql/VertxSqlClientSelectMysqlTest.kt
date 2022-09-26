/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mysql

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.NonUniqueResultException
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient


class VertxSqlClientSelectMysqlTest : AbstractVertxSqlClientMysqlTest<UserRepositoryMysqlSelect>() {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = UserRepositoryMysqlSelect(sqlClient)

    @Test
    fun `Verify selectAllUsers returns all users`() {
        assertThat(repository.selectAllUsers().await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(userJdoe, userBboss)
    }

    @Test
    fun `Verify countAllUsersAndAliases returns all users' count`() {
        assertThat(repository.countAllUsersAndAliases().await().indefinitely())
            .isEqualTo(Pair(2L, 1L))
    }

    @Test
    fun `Verify selectOneNonUnique throws NonUniqueResultException`() {
        assertThatThrownBy { repository.selectOneNonUnique().await().indefinitely() }
            .isInstanceOf(NonUniqueResultException::class.java)
    }

    @Test
    fun `Verify selectAllMappedToDto does the mapping`() {
        assertThat(repository.selectAllMappedToDto().await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(
                UserDto("John Doe", null),
                UserDto("Big Boss", "TheBoss")
            )
    }

    @Test
    fun `Verify selectWithJoin works correctly`() {
        assertThat(repository.selectWithJoin().await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(
                UserWithRoleDto(userJdoe.lastname, roleUser.label),
                UserWithRoleDto(userBboss.lastname, roleAdmin.label)
            )
    }

    @Test
    fun `Verify selectWithEqJoin works correctly`() {
        assertThat(repository.selectWithEqJoin().await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(
                UserWithRoleDto(userJdoe.lastname, roleUser.label),
                UserWithRoleDto(userBboss.lastname, roleAdmin.label)
            )
    }

    @Test
    fun `Verify selectAllIn returns TheBoss`() {
        assertThat(repository.selectAllIn(setOf("TheBoss", "TheStar", "TheBest")).await().indefinitely())
            .hasSize(1)
            .containsExactly(userBboss)
    }

    @Test
    fun `Verify selectAllIn returns no result`() {
        val coll = ArrayDeque<String>()
        coll.addFirst("TheStar")
        coll.addLast("TheBest")
        assertThat(repository.selectAllIn(coll).await().indefinitely())
            .isEmpty()
    }

    @Test
    fun `Verify selectOneById returns TheBoss`() {
        assertThat(repository.selectOneById(userBboss.id).await().indefinitely())
            .isEqualTo(userBboss)
    }

    @Test
    fun `Verify selectOneById finds no result for -1`() {
        assertThat(repository.selectOneById(-1).await().indefinitely()).isNull()
    }

    @Test
    fun `Verify selectFirstnameById returns TheBoss firstname`() {
        assertThat(repository.selectFirstnameById(userBboss.id).await().indefinitely())
            .isEqualTo(userBboss.firstname)
    }

    @Test
    fun `Verify selectAliasById returns null as J Doe alias`() {
        assertThat(repository.selectAliasById(userJdoe.id).await().indefinitely())
            .isNull()
    }

    @Test
    fun `Verify selectFirstnameAndAliasById returns J Doe firstname and alias`() {
        assertThat(repository.selectFirstnameAndAliasById(userJdoe.id).await().indefinitely())
            .isEqualTo(Pair(userJdoe.firstname, null))
    }

    @Test
    fun `Verify selectAllFirstnameAndAlias returns all users firstname and alias`() {
        assertThat(repository.selectAllFirstnameAndAlias().await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(
                Pair(userJdoe.firstname, null),
                Pair(userBboss.firstname, userBboss.alias),
            )
    }

    @Test
    fun `Verify selectFirstnameAndLastnameAndAliasById returns J Doe firstname, lastname and alias`() {
        assertThat(repository.selectFirstnameAndLastnameAndAliasById(userJdoe.id).await().indefinitely())
            .isEqualTo(Triple(userJdoe.firstname, userJdoe.lastname, null))
    }

    @Test
    fun `Verify selectFirstnameAndLastnameAndAliasAndIsAdminById returns J Doe firstname, lastname, alias and isAdmin`() {
        assertThat(repository.selectFirstnameAndLastnameAndAliasAndIsAdminById(userJdoe.id).await().indefinitely())
            .isEqualTo(listOf(userJdoe.firstname, userJdoe.lastname, null, false))
    }

    @Test
    fun `Verify selectRoleLabelFromUserId returns Admin role for TheBoss`() {
        assertThat(repository.selectRoleLabelFromUserId(userBboss.id).await().indefinitely())
            .isEqualTo(roleAdmin.label)
    }

    @Test
    fun `Verify countAllUsers returns 2`() {
        assertThat(repository.countAllUsers().await().indefinitely()!!)
            .isEqualTo(2L)
    }

    @Test
    fun `Verify selectRoleLabelsFromUserId returns Admin role for TheBoss`() {
        assertThat(repository.selectRoleLabelsFromUserId(userBboss.id).await().indefinitely())
            .hasSize(1)
            .containsExactly(roleAdmin.label)
    }
}


class UserRepositoryMysqlSelect(sqlClient: VertxSqlClient) : AbstractUserRepositoryMysql(sqlClient) {

    fun selectOneNonUnique() =
        (sqlClient selectFrom MysqlUsers
                ).fetchOne()

    fun selectAllMappedToDto() =
        (sqlClient selectAndBuild {
            UserDto("${it[MysqlUsers.firstname]} ${it[MysqlUsers.lastname]}", it[MysqlUsers.alias])
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
