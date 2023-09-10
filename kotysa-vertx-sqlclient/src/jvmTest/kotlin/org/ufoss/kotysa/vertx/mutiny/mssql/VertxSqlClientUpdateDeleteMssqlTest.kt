/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mssql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient

class VertxSqlClientUpdateDeleteMssqlTest :
    AbstractVertxSqlClientMssqlTest<UserRepositoryMssqlUpdateDelete>() {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = UserRepositoryMssqlUpdateDelete(sqlClient)

    @Test
    fun `Verify deleteAllFromUserRoles works correctly`() {
        val countUserRoles = operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.deleteAllFromUserRoles()
                .onItem().invoke { n -> assertThat(n).isEqualTo(1) }
                .chain { -> repository.countAllUserRoles() }
        }.await().indefinitely()
        assertThat(countUserRoles).isEqualTo(0)
    }

    @Test
    fun `Verify deleteUserById works`() {
        val allUsers = operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.deleteUserById(userJdoe.id)
                .onItem().invoke { n -> assertThat(n).isEqualTo(1) }
                .chain { -> repository.selectAllUsers() }
        }.await().indefinitely()
        assertThat(allUsers)
            .hasSize(1)
            .containsExactly(userBboss)
    }

    @Test
    fun `Verify deleteUserIn works`() {
        val allUsers = operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.deleteUserIn(listOf(userJdoe.id, 9999999))
                .onItem().invoke { n -> assertThat(n).isEqualTo(1) }
                .chain { -> repository.selectAllUsers() }
        }.await().indefinitely()
        assertThat(allUsers)
            .hasSize(1)
            .containsExactly(userBboss)
    }

    @Test
    fun `Verify deleteUserWithJoin works`() {
        val allUsers = operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.deleteUserWithJoin(roleUser.label)
                .onItem().invoke { n -> assertThat(n).isEqualTo(1) }
                .chain { -> repository.selectAllUsers() }
        }.await().indefinitely()
        assertThat(allUsers)
            .hasSize(1)
            .containsExactly(userBboss)
    }

    @Test
    fun `Verify updateLastname works`() {
        val userJ = operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.updateLastname("Do")
                .onItem().invoke { n -> assertThat(n).isEqualTo(1) }
                .chain { -> repository.selectFirstByFirstname(userJdoe.firstname) }
        }.await().indefinitely()
        assertThat(userJ).matches { user -> "Do" == user!!.lastname }
    }

    @Test
    fun `Verify updateLastnameIn works`() {
        val userJ = operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.updateLastnameIn("Do", listOf(userJdoe.id, 9999999))
                .onItem().invoke { n -> assertThat(n).isEqualTo(1) }
                .chain { -> repository.selectFirstByFirstname(userJdoe.firstname) }
        }.await().indefinitely()
        assertThat(userJ).matches { user -> "Do" == user!!.lastname }
    }

    @Test
    fun `Verify updateWithJoin works`() {
        val userJ = operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.updateWithJoin("Doee", roleUser.label)
                .onItem().invoke { n -> assertThat(n).isEqualTo(1) }
                .chain { -> repository.selectFirstByFirstname(userJdoe.firstname) }
        }.await().indefinitely()
        assertThat(userJ).matches { user -> "Doee" == user!!.lastname }
    }

    @Test
    fun `Verify updateAlias works`() {
        assertThat(repository.updateAlias("TheBigBoss").await().indefinitely())
            .isEqualTo(1)
        assertThat(repository.selectFirstByFirstname(userBboss.firstname).await().indefinitely())
            .extracting { user -> user?.alias }
            .isEqualTo("TheBigBoss")
        assertThat(repository.updateAlias(null).await().indefinitely())
            .isEqualTo(1)
        assertThat(repository.selectFirstByFirstname(userBboss.firstname).await().indefinitely())
            .extracting { user -> user?.alias }
            .isEqualTo(null)
        // restore initial alias
        repository.updateAlias(userBboss.alias).await().indefinitely()
    }

    @Test
    fun `Verify updateAndIncrementRoleId works`() {
        val userJ = operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.updateAndIncrementRoleId()
                .onItem().invoke { n -> assertThat(n).isEqualTo(1) }
                .chain { -> repository.selectFirstByFirstname(userJdoe.firstname) }
        }.await().indefinitely()
        assertThat(userJ).matches { user -> roleGod.id == user!!.roleId }
    }

    @Test
    fun `Verify updateAndDecrementRoleId works`() {
        val userB = operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.updateAndDecrementRoleId()
                .onItem().invoke { n -> assertThat(n).isEqualTo(1) }
                .chain { -> repository.selectFirstByFirstname(userBboss.firstname) }
        }.await().indefinitely()
        assertThat(userB).matches { user -> roleUser.id == user!!.roleId }
    }
}


class UserRepositoryMssqlUpdateDelete(sqlClient: MutinyVertxSqlClient) : AbstractUserRepositoryMssql(sqlClient) {

    fun deleteUserById(id: Int) =
        (sqlClient deleteFrom MssqlUsers
                where MssqlUsers.id eq id
                ).execute()

    fun deleteUserIn(ids: Collection<Int>) =
        (sqlClient deleteFrom MssqlUsers
                where MssqlUsers.id `in` ids
                ).execute()

    fun deleteUserWithJoin(roleLabel: String) =
        (sqlClient deleteFrom MssqlUsers
                innerJoin MssqlRoles on MssqlUsers.roleId eq MssqlRoles.id
                where MssqlRoles.label eq roleLabel
                ).execute()

    fun updateLastname(newLastname: String) =
        (sqlClient update MssqlUsers
                set MssqlUsers.lastname eq newLastname
                where MssqlUsers.id eq userJdoe.id
                ).execute()

    fun updateLastnameIn(newLastname: String, ids: Collection<Int>) =
        (sqlClient update MssqlUsers
                set MssqlUsers.lastname eq newLastname
                where MssqlUsers.id `in` ids
                ).execute()

    fun updateAlias(newAlias: String?) =
        (sqlClient update MssqlUsers
                set MssqlUsers.alias eq newAlias
                where MssqlUsers.id eq userBboss.id
                ).execute()

    fun updateWithJoin(newLastname: String, roleLabel: String) =
        (sqlClient update MssqlUsers
                set MssqlUsers.lastname eq newLastname
                innerJoin MssqlRoles on MssqlUsers.roleId eq MssqlRoles.id
                where MssqlRoles.label eq roleLabel
                ).execute()

    fun updateAndIncrementRoleId() =
        (sqlClient update MssqlUsers
                set MssqlUsers.roleId eq MssqlUsers.roleId plus 2
                where MssqlUsers.id eq userJdoe.id
                ).execute()

    fun updateAndDecrementRoleId() =
        (sqlClient update MssqlUsers
                set MssqlUsers.roleId eq MssqlUsers.roleId minus 1
                where MssqlUsers.id eq userBboss.id
                ).execute()
}
