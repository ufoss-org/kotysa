/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.reactor

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.transaction.Transaction
import reactor.kotlin.test.test

interface ReactorUpdateDeleteTest<T : Roles, U : Users, V : UserRoles, W : Companies,
        X : ReactorUpdateDeleteRepository<T, U, V, W>, Y : Transaction> : ReactorRepositoryTest<X, Y> {

    @Test
    fun `Verify deleteAllFromUserRoles works correctly`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.deleteAllFromUserRoles()
                .doOnNext { n -> assertThat(n).isEqualTo(1) }
                .thenMany(repository.countAllUserRoles())
        }.test()
            .expectNext(0)
            .verifyComplete()
    }

    @Test
    fun `Verify deleteUserById works`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.deleteUserById(userJdoe.id)
                .doOnNext { n -> assertThat(n).isEqualTo(1) }
                .thenMany(repository.selectAllUsers())
        }.test()
            .expectNext(userBboss)
            .verifyComplete()
    }

    @Test
    fun `Verify deleteUserWithJoin works`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.deleteUserWithJoin(roleUser.label)
                .doOnNext { n -> assertThat(n).isEqualTo(1) }
                .thenMany(repository.selectAllUsers())
        }.test()
            .expectNext(userBboss)
            .verifyComplete()
    }

    @Test
    fun `Verify deleteUserIn works`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.deleteUserIn(listOf(userJdoe.id, 9999999))
                .doOnNext { n -> assertThat(n).isEqualTo(1) }
                .thenMany(repository.selectAllUsers())
        }.test()
            .expectNext(userBboss)
            .verifyComplete()
    }

    @Test
    fun `Verify deleteUserIn no match`() {
        assertThat(repository.deleteUserIn(listOf(99999, 9999999)).block()!!)
            .isEqualTo(0)
        assertThat(repository.selectAllUsers().toIterable())
            .hasSize(2)
    }

    @Test
    fun `Verify updateLastname works`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.updateLastname("Do")
                .doOnNext { n -> assertThat(n).isEqualTo(1) }
                .then(repository.selectFirstByFirstname(userJdoe.firstname))
        }.test()
            .expectNextMatches { user -> "Do" == user!!.lastname }
            .verifyComplete()
    }

    @Test
    fun `Verify updateWithJoin works`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.updateWithJoin("Doee", roleUser.label)
                .doOnNext { n -> assertThat(n).isEqualTo(1) }
                .then(repository.selectFirstByFirstname(userJdoe.firstname))
        }.test()
            .expectNextMatches { user -> "Doee" == user!!.lastname }
            .verifyComplete()
    }

    @Test
    fun `Verify updateAlias works`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.updateAlias("TheBigBoss")
                .doOnNext { n -> assertThat(n).isEqualTo(1) }
                .then(repository.selectFirstByFirstname(userBboss.firstname))
                .doOnNext { user -> assertThat(user.alias).isEqualTo("TheBigBoss") }
                .then(repository.updateAlias(null))
                .doOnNext { n -> assertThat(n).isEqualTo(1) }
                .then(repository.selectFirstByFirstname(userBboss.firstname))
        }.test()
            .expectNextMatches { user -> null == user!!.alias }
            .verifyComplete()
    }

    @Test
    fun `Verify updateLastnameIn works`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.updateLastnameIn("Do", listOf(userJdoe.id, 9999999))
                .doOnNext { n -> assertThat(n).isEqualTo(1) }
                .then(repository.selectFirstByFirstname(userJdoe.firstname))
        }.test()
            .expectNextMatches { user -> "Do" == user!!.lastname }
            .verifyComplete()
    }

    @Test
    fun `Verify updateLastnameIn no match`() {
        assertThat(repository.updateLastnameIn("Do", listOf(99999, 9999999)).block()!!)
            .isEqualTo(0)
        assertThat(repository.selectFirstByFirstname(userJdoe.firstname).block())
            .extracting { user -> user!!.lastname }
            .isEqualTo("Doe")
    }

    @Test
    fun `Verify updateAndIncrementRoleId works`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.updateAndIncrementRoleId()
                .doOnNext { n -> assertThat(n).isEqualTo(1) }
                .then(repository.selectFirstByFirstname(userJdoe.firstname))
        }.test()
            .expectNextMatches { user -> roleGod.id == user!!.roleId }
            .verifyComplete()
    }

    @Test
    fun `Verify updateAndDecrementRoleId works`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.updateAndDecrementRoleId()
                .doOnNext { n -> assertThat(n).isEqualTo(1) }
                .then(repository.selectFirstByFirstname(userBboss.firstname))
        }.test()
            .expectNextMatches { user -> roleUser.id == user!!.roleId }
            .verifyComplete()
    }
}
