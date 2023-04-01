/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.MysqlCoroutinesSqlClient
import org.ufoss.kotysa.MysqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.*
import reactor.kotlin.test.test

class R2DbcUpdateDeleteMysqlTest : AbstractR2dbcMysqlTest<UserRepositoryMysqlUpdateDelete>() {

    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        UserRepositoryMysqlUpdateDelete(sqlClient)

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


class UserRepositoryMysqlUpdateDelete(sqlClient: ReactorSqlClient) : AbstractUserRepositoryMysql(sqlClient) {

    fun deleteUserById(id: Int) =
        (sqlClient deleteFrom MysqlUsers
                where MysqlUsers.id eq id
                ).execute()

    fun deleteUserIn(ids: Collection<Int>) =
        (sqlClient deleteFrom MysqlUsers
                where MysqlUsers.id `in` ids
                ).execute()

    fun deleteUserWithJoin(roleLabel: String) =
        (sqlClient deleteFrom MysqlUsers
                innerJoin MysqlRoles on MysqlUsers.roleId eq MysqlRoles.id
                where MysqlRoles.label eq roleLabel
                ).execute()

    fun updateLastname(newLastname: String) =
        (sqlClient update MysqlUsers
                set MysqlUsers.lastname eq newLastname
                where MysqlUsers.id eq userJdoe.id
                ).execute()

    fun updateLastnameIn(newLastname: String, ids: Collection<Int>) =
        (sqlClient update MysqlUsers
                set MysqlUsers.lastname eq newLastname
                where MysqlUsers.id `in` ids
                ).execute()

    fun updateAlias(newAlias: String?) =
        (sqlClient update MysqlUsers
                set MysqlUsers.alias eq newAlias
                where MysqlUsers.id eq userBboss.id
                ).execute()

    fun updateWithJoin(newLastname: String, roleLabel: String) =
        (sqlClient update MysqlUsers
                set MysqlUsers.lastname eq newLastname
                innerJoin MysqlRoles on MysqlUsers.roleId eq MysqlRoles.id
                where MysqlRoles.label eq roleLabel
                ).execute()

    fun updateAndIncrementRoleId() =
        (sqlClient update MysqlUsers
                set MysqlUsers.roleId eq MysqlUsers.roleId plus 2
                where MysqlUsers.id eq userJdoe.id
                ).execute()

    fun updateAndDecrementRoleId() =
        (sqlClient update MysqlUsers
                set MysqlUsers.roleId eq MysqlUsers.roleId minus 1
                where MysqlUsers.id eq userBboss.id
                ).execute()
}
