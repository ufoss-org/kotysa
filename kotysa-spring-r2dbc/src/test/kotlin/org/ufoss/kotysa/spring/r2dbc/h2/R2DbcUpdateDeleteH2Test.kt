/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.H2CoroutinesSqlClient
import org.ufoss.kotysa.H2ReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.*
import reactor.kotlin.test.test


class R2DbcUpdateDeleteH2Test : AbstractR2dbcH2Test<UserRepositoryH2UpdateDelete>() {
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        UserRepositoryH2UpdateDelete(sqlClient)

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


class UserRepositoryH2UpdateDelete(sqlClient: ReactorSqlClient) : AbstractUserRepositoryH2(sqlClient) {

    fun deleteUserById(id: Int) =
        (sqlClient deleteFrom H2Users
                where H2Users.id eq id
                ).execute()

    fun deleteUserIn(ids: Collection<Int>) =
        (sqlClient deleteFrom H2Users
                where H2Users.id `in` ids
                ).execute()

    fun deleteUserWithJoin(roleLabel: String) =
        (sqlClient deleteFrom H2Users
                innerJoin H2Roles on H2Users.roleId eq H2Roles.id
                where H2Roles.label eq roleLabel
                ).execute()

    fun updateLastname(newLastname: String) =
        (sqlClient update H2Users
                set H2Users.lastname eq newLastname
                where H2Users.id eq userJdoe.id
                ).execute()

    fun updateLastnameIn(newLastname: String, ids: Collection<Int>) =
        (sqlClient update H2Users
                set H2Users.lastname eq newLastname
                where H2Users.id `in` ids
                ).execute()

    fun updateAlias(newAlias: String?) =
        (sqlClient update H2Users
                set H2Users.alias eq newAlias
                where H2Users.id eq userBboss.id
                ).execute()

    fun updateWithJoin(newLastname: String, roleLabel: String) =
        (sqlClient update H2Users
                set H2Users.lastname eq newLastname
                innerJoin H2Roles on H2Users.roleId eq H2Roles.id
                where H2Roles.label eq roleLabel
                ).execute()

    fun updateAndIncrementRoleId() =
        (sqlClient update H2Users
                set H2Users.roleId eq H2Users.roleId plus 2
                where H2Users.id eq userJdoe.id
                ).execute()

    fun updateAndDecrementRoleId() =
        (sqlClient update H2Users
                set H2Users.roleId eq H2Users.roleId minus 1
                where H2Users.id eq userBboss.id
                ).execute()
}
