/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*

interface MutinyUpdateDeleteTest<T : Roles, U : Users, V : UserRoles, W : MutinyUpdateDeleteRepository<T, U, V>>
    : MutinyRepositoryTest<W> {

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
