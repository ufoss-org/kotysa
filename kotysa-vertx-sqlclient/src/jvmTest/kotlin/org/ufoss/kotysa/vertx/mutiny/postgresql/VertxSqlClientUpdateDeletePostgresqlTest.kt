/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.PostgresqlMutinyVertxSqlClient
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient

class VertxSqlClientUpdateDeletePostgresqlTest :
    AbstractVertxSqlClientPostgresqlTest<UserRepositoryPostgresqlUpdateDelete>() {

    override fun instantiateRepository(sqlClient: PostgresqlMutinyVertxSqlClient) = UserRepositoryPostgresqlUpdateDelete(sqlClient)

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


class UserRepositoryPostgresqlUpdateDelete(sqlClient: MutinyVertxSqlClient) : AbstractUserRepositoryPostgresql(sqlClient) {

    fun deleteUserById(id: Int) =
        (sqlClient deleteFrom PostgresqlUsers
                where PostgresqlUsers.id eq id
                ).execute()

    fun deleteUserIn(ids: Collection<Int>) =
        (sqlClient deleteFrom PostgresqlUsers
                where PostgresqlUsers.id `in` ids
                ).execute()

    fun deleteUserWithJoin(roleLabel: String) =
        (sqlClient deleteFrom PostgresqlUsers
                innerJoin PostgresqlRoles on PostgresqlUsers.roleId eq PostgresqlRoles.id
                where PostgresqlRoles.label eq roleLabel
                ).execute()

    fun updateLastname(newLastname: String) =
        (sqlClient update PostgresqlUsers
                set PostgresqlUsers.lastname eq newLastname
                where PostgresqlUsers.id eq userJdoe.id
                ).execute()

    fun updateLastnameIn(newLastname: String, ids: Collection<Int>) =
        (sqlClient update PostgresqlUsers
                set PostgresqlUsers.lastname eq newLastname
                where PostgresqlUsers.id `in` ids
                ).execute()

    fun updateAlias(newAlias: String?) =
        (sqlClient update PostgresqlUsers
                set PostgresqlUsers.alias eq newAlias
                where PostgresqlUsers.id eq userBboss.id
                ).execute()

    fun updateWithJoin(newLastname: String, roleLabel: String) =
        (sqlClient update PostgresqlUsers
                set PostgresqlUsers.lastname eq newLastname
                innerJoin PostgresqlRoles on PostgresqlUsers.roleId eq PostgresqlRoles.id
                where PostgresqlRoles.label eq roleLabel
                ).execute()

    fun updateAndIncrementRoleId() =
        (sqlClient update PostgresqlUsers
                set PostgresqlUsers.roleId eq PostgresqlUsers.roleId plus 2
                where PostgresqlUsers.id eq userJdoe.id
                ).execute()

    fun updateAndDecrementRoleId() =
        (sqlClient update PostgresqlUsers
                set PostgresqlUsers.roleId eq PostgresqlUsers.roleId minus 1
                where PostgresqlUsers.id eq userBboss.id
                ).execute()
}
