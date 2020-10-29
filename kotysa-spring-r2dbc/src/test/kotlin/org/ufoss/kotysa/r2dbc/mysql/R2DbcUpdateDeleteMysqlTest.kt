/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.getBean
import org.springframework.transaction.reactive.TransactionalOperator
import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.r2dbc.transaction.transactionalOp
import org.ufoss.kotysa.test.*
import reactor.kotlin.test.test


class R2DbcUpdateDeleteMysqlTest : AbstractR2dbcMysqlTest<UserRepositoryMysqlUpdateDelete>() {
    override val context = startContext<UserRepositoryMysqlUpdateDelete>()

    override val repository = getContextRepository<UserRepositoryMysqlUpdateDelete>()
    private val operator = context.getBean<TransactionalOperator>().transactionalOp()

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
            repository.deleteUserById(mysqlJdoe.id)
                    .doOnNext { n -> assertThat(n).isEqualTo(1) }
                    .thenMany(repository.selectAllUsers())
        }.test()
                .expectNext(mysqlBboss)
                .verifyComplete()
    }

    @Test
    fun `Verify deleteUserWithJoin works`() {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            repository.deleteUserWithJoin(mysqlUser.label)
                    .doOnNext { n -> assertThat(n).isEqualTo(1) }
                    .thenMany(repository.selectAllUsers())
        }.test()
                .expectNext(mysqlBboss)
                .verifyComplete()
    }

    @Test
    fun `Verify updateLastname works`() {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            repository.updateLastname("Do")
                    .doOnNext { n -> assertThat(n).isEqualTo(1) }
                    .then(repository.selectFirstByFirstame(mysqlJdoe.firstname))
        }.test()
                .expectNextMatches { user -> "Do" == user.lastname }
                .verifyComplete()
    }

    @Test
    fun `Verify updateWithJoin works`() {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            repository.updateWithJoin("Doee", mysqlUser.label)
                    .doOnNext { n -> assertThat(n).isEqualTo(1) }
                    .then(repository.selectFirstByFirstame(mysqlJdoe.firstname))
        }.test()
                .expectNextMatches { user -> "Doee" == user.lastname }
                .verifyComplete()
    }

    @Test
    fun `Verify updateAlias works`() {
        assertThat(repository.updateAlias("TheBigBoss").block()!!)
                .isEqualTo(1)
        assertThat(repository.selectFirstByFirstame(mysqlBboss.firstname).block())
                .extracting { user -> user?.alias }
                .isEqualTo("TheBigBoss")
        assertThat(repository.updateAlias(null).block()!!)
                .isEqualTo(1)
        assertThat(repository.selectFirstByFirstame(mysqlBboss.firstname).block())
                .extracting { user -> user?.alias }
                .isEqualTo(null)
        repository.updateAlias(mysqlBboss.alias).block()
    }
}


class UserRepositoryMysqlUpdateDelete(sqlClient: ReactorSqlClient) : AbstractUserRepositoryMysql(sqlClient) {

    fun deleteUserById(id: Int) = sqlClient.deleteFromTable<MysqlUser>()
            .where { it[MysqlUser::id] eq id }
            .execute()

    fun deleteUserWithJoin(roleLabel: String) = sqlClient.deleteFromTable<MysqlUser>()
            .innerJoin<MysqlRole>().on { it[MysqlUser::roleId] }
            .where { it[MysqlRole::label] eq roleLabel }
            .execute()

    fun updateLastname(newLastname: String) = sqlClient.updateTable<MysqlUser>()
            .set { it[MysqlUser::lastname] = newLastname }
            .where { it[MysqlUser::id] eq mysqlJdoe.id }
            .execute()

    fun updateAlias(newAlias: String?) = sqlClient.updateTable<MysqlUser>()
            .set { it[MysqlUser::alias] = newAlias }
            .where { it[MysqlUser::id] eq mysqlBboss.id }
            .execute()

    fun updateWithJoin(newLastname: String, roleLabel: String) = sqlClient.updateTable<MysqlUser>()
            .set { it[MysqlUser::lastname] = newLastname }
            .innerJoin<MysqlRole>().on { it[MysqlUser::roleId] }
            .where { it[MysqlRole::label] eq roleLabel }
            .execute()
}
