/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import reactor.kotlin.test.test


class R2DbcUpdateDeleteMssqlTest : AbstractR2dbcMssqlTest<UserRepositoryMssqlUpdateDelete>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositoryMssqlUpdateDelete>(resource)
    }

    override val repository: UserRepositoryMssqlUpdateDelete by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify deleteAllFromUserRoles works correctly`() {
        operator.execute { transaction ->
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


class UserRepositoryMssqlUpdateDelete(sqlClient: ReactorSqlClient) : AbstractUserRepositoryMssql(sqlClient) {

    fun deleteUserById(id: Int) =
            (sqlClient deleteFrom MSSQL_USER
                    where MSSQL_USER.id eq id
                    ).execute()

    fun deleteUserIn(ids: Collection<Int>) =
            (sqlClient deleteFrom MSSQL_USER
                    where MSSQL_USER.id `in` ids
                    ).execute()

    fun deleteUserWithJoin(roleLabel: String) =
            (sqlClient deleteFrom MSSQL_USER
                    innerJoin MSSQL_ROLE on MSSQL_USER.roleId eq MSSQL_ROLE.id
                    where MSSQL_ROLE.label eq roleLabel
                    ).execute()

    fun updateLastname(newLastname: String) =
            (sqlClient update MSSQL_USER
                    set MSSQL_USER.lastname eq newLastname
                    where MSSQL_USER.id eq userJdoe.id
                    ).execute()

    fun updateLastnameIn(newLastname: String, ids: Collection<Int>) =
            (sqlClient update MSSQL_USER
                    set MSSQL_USER.lastname eq newLastname
                    where MSSQL_USER.id `in` ids
                    ).execute()

    fun updateAlias(newAlias: String?) =
            (sqlClient update MSSQL_USER
                    set MSSQL_USER.alias eq newAlias
                    where MSSQL_USER.id eq userBboss.id
                    ).execute()

    fun updateWithJoin(newLastname: String, roleLabel: String) =
            (sqlClient update MSSQL_USER
                    set MSSQL_USER.lastname eq newLastname
                    innerJoin MSSQL_ROLE on MSSQL_USER.roleId eq MSSQL_ROLE.id
                    where MSSQL_ROLE.label eq roleLabel
                    ).execute()
}
