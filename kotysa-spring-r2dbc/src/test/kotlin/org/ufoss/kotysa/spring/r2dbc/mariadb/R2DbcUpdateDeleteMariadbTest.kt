/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.spring.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import reactor.kotlin.test.test


class R2DbcUpdateDeleteMariadbTest : AbstractR2dbcMariadbTest<UserRepositoryMariadbUpdateDelete>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositoryMariadbUpdateDelete>(resource)
    }

    override val repository: UserRepositoryMariadbUpdateDelete by lazy {
        getContextRepository()
    }

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
        assertThat(repository.updateAlias("TheBigBoss").block()!!)
                .isEqualTo(1)
        assertThat(repository.selectFirstByFirstname(userBboss.firstname).block())
                .extracting { user -> user?.alias }
                .isEqualTo("TheBigBoss")
        assertThat(repository.updateAlias(null).block()!!)
                .isEqualTo(1)
        assertThat(repository.selectFirstByFirstname(userBboss.firstname).block())
                .extracting { user -> user?.alias }
                .isEqualTo(null)
        repository.updateAlias(userBboss.alias).block()
    }
}


class UserRepositoryMariadbUpdateDelete(sqlClient: ReactorSqlClient) : org.ufoss.kotysa.spring.r2dbc.mariadb.AbstractUserRepositoryMariadb(sqlClient) {

    fun deleteUserById(id: Int) =
            (sqlClient deleteFrom MARIADB_USER
                    where MARIADB_USER.id eq id
                    ).execute()

    fun deleteUserIn(ids: Collection<Int>) =
            (sqlClient deleteFrom MARIADB_USER
                    where MARIADB_USER.id `in` ids
                    ).execute()

    fun deleteUserWithJoin(roleLabel: String) =
            (sqlClient deleteFrom MARIADB_USER
                    innerJoin MARIADB_ROLE on MARIADB_USER.roleId eq MARIADB_ROLE.id
                    where MARIADB_ROLE.label eq roleLabel
                    ).execute()

    fun updateLastname(newLastname: String) =
            (sqlClient update MARIADB_USER
                    set MARIADB_USER.lastname eq newLastname
                    where MARIADB_USER.id eq userJdoe.id
                    ).execute()

    fun updateLastnameIn(newLastname: String, ids: Collection<Int>) =
            (sqlClient update MARIADB_USER
                    set MARIADB_USER.lastname eq newLastname
                    where MARIADB_USER.id `in` ids
                    ).execute()

    fun updateAlias(newAlias: String?) =
            (sqlClient update MARIADB_USER
                    set MARIADB_USER.alias eq newAlias
                    where MARIADB_USER.id eq userBboss.id
                    ).execute()

    fun updateWithJoin(newLastname: String, roleLabel: String) =
            (sqlClient update MARIADB_USER
                    set MARIADB_USER.lastname eq newLastname
                    innerJoin MARIADB_ROLE on MARIADB_USER.roleId eq MARIADB_ROLE.id
                    where MARIADB_ROLE.label eq roleLabel
                    ).execute()
}
