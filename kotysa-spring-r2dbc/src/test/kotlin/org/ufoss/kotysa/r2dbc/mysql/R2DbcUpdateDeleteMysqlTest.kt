/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import reactor.kotlin.test.test


class R2DbcUpdateDeleteMysqlTest : AbstractR2dbcMysqlTest<UserRepositoryMysqlUpdateDelete>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositoryMysqlUpdateDelete>(resource)
        repository = getContextRepository()
    }

    @Test
    fun `Verify deleteAllFromUser works correctly`() {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            repository.deleteAllFromUsers()
                    .doOnNext { n -> assertThat(n).isEqualTo(2) }
                    .thenMany(repository.selectAllUsers())
        }.test()
                .verifyComplete()
    }

    @Test
    fun `Verify deleteUserById works`() {
        operator.execute { transaction ->
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
        operator.execute { transaction ->
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
        operator.execute { transaction ->
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
        operator.execute { transaction ->
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
        operator.execute { transaction ->
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
        operator.execute { transaction ->
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


class UserRepositoryMysqlUpdateDelete(sqlClient: ReactorSqlClient) : AbstractUserRepositoryMysql(sqlClient) {

    fun deleteUserById(id: Int) =
            (sqlClient deleteFrom MYSQL_USER
                    where MYSQL_USER.id eq id
                    ).execute()

    fun deleteUserIn(ids: Collection<Int>) =
            (sqlClient deleteFrom MYSQL_USER
                    where MYSQL_USER.id `in` ids
                    ).execute()

    fun deleteUserWithJoin(roleLabel: String) =
            (sqlClient deleteFrom MYSQL_USER
                    innerJoin MYSQL_ROLE on MYSQL_USER.roleId eq MYSQL_ROLE.id
                    where MYSQL_ROLE.label eq roleLabel
                    ).execute()

    fun updateLastname(newLastname: String) =
            (sqlClient update MYSQL_USER
                    set MYSQL_USER.lastname eq newLastname
                    where MYSQL_USER.id eq userJdoe.id
                    ).execute()

    fun updateLastnameIn(newLastname: String, ids: Collection<Int>) =
            (sqlClient update MYSQL_USER
                    set MYSQL_USER.lastname eq newLastname
                    where MYSQL_USER.id `in` ids
                    ).execute()

    fun updateAlias(newAlias: String?) =
            (sqlClient update MYSQL_USER
                    set MYSQL_USER.alias eq newAlias
                    where MYSQL_USER.id eq userBboss.id
                    ).execute()

    fun updateWithJoin(newLastname: String, roleLabel: String) =
            (sqlClient update MYSQL_USER
                    set MYSQL_USER.lastname eq newLastname
                    innerJoin MYSQL_ROLE on MYSQL_USER.roleId eq MYSQL_ROLE.id
                    where MYSQL_ROLE.label eq roleLabel
                    ).execute()
}
