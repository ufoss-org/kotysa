/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.getBean
import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.r2dbc.transaction.ReactorTransactionalOp
import org.ufoss.kotysa.test.*
import reactor.kotlin.test.test
import java.util.*


class R2DbcUpdateDeletePostgresqlTest : AbstractR2dbcPostgresqlTest<UserRepositoryPostgresqlUpdateDelete>() {
    override val context = startContext<UserRepositoryPostgresqlUpdateDelete>()

    override val repository = getContextRepository<UserRepositoryPostgresqlUpdateDelete>()
    private val operator = context.getBean<ReactorTransactionalOp>()

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
            repository.deleteUserById(postgresqlJdoe.id)
                    .doOnNext { n -> assertThat(n).isEqualTo(1) }
                    .thenMany(repository.selectAllUsers())
        }.test()
                .expectNext(postgresqlBboss)
                .verifyComplete()
    }

    @Test
    fun `Verify deleteUserWithJoin works`() {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            repository.deleteUserWithJoin(postgresqlUser.label)
                    .doOnNext { n -> assertThat(n).isEqualTo(1) }
                    .thenMany(repository.selectAllUsers())
        }.test()
                .expectNext(postgresqlBboss)
                .verifyComplete()
    }

    @Test
    fun `Verify updateLastname works`() {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            repository.updateLastname("Do")
                    .doOnNext { n -> assertThat(n).isEqualTo(1) }
                    .then(repository.selectFirstByFirstame(postgresqlJdoe.firstname))
        }.test()
                .expectNextMatches { user -> "Do" == user.lastname }
                .verifyComplete()
    }

    @Test
    fun `Verify updateWithJoin works`() {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            repository.updateWithJoin("Doee", postgresqlUser.label)
                    .doOnNext { n -> assertThat(n).isEqualTo(1) }
                    .then(repository.selectFirstByFirstame(postgresqlJdoe.firstname))
        }.test()
                .expectNextMatches { user -> "Doee" == user.lastname }
                .verifyComplete()
    }

    @Test
    fun `Verify updateAlias works`() {
        assertThat(repository.updateAlias("TheBigBoss").block()!!)
                .isEqualTo(1)
        assertThat(repository.selectFirstByFirstame(postgresqlBboss.firstname).block())
                .extracting { user -> user?.alias }
                .isEqualTo("TheBigBoss")
        assertThat(repository.updateAlias(null).block()!!)
                .isEqualTo(1)
        assertThat(repository.selectFirstByFirstame(postgresqlBboss.firstname).block())
                .extracting { user -> user?.alias }
                .isEqualTo(null)
        repository.updateAlias(postgresqlBboss.alias).block()
    }
}


class UserRepositoryPostgresqlUpdateDelete(sqlClient: ReactorSqlClient) : AbstractUserRepositoryPostgresql(sqlClient) {

    fun deleteUserById(id: UUID) = sqlClient.deleteFromTable<PostgresqlUser>()
            .where { it[PostgresqlUser::id] eq id }
            .execute()

    fun deleteUserWithJoin(roleLabel: String) = sqlClient.deleteFromTable<PostgresqlUser>()
            .innerJoin<PostgresqlRole>().on { it[PostgresqlUser::roleId] }
            .where { it[PostgresqlRole::label] eq roleLabel }
            .execute()

    fun updateLastname(newLastname: String) = sqlClient.updateTable<PostgresqlUser>()
            .set { it[PostgresqlUser::lastname] = newLastname }
            .where { it[PostgresqlUser::id] eq postgresqlJdoe.id }
            .execute()

    fun updateAlias(newAlias: String?) = sqlClient.updateTable<PostgresqlUser>()
            .set { it[PostgresqlUser::alias] = newAlias }
            .where { it[PostgresqlUser::id] eq postgresqlBboss.id }
            .execute()

    fun updateWithJoin(newLastname: String, roleLabel: String) = sqlClient.updateTable<PostgresqlUser>()
            .set { it[PostgresqlUser::lastname] = newLastname }
            .innerJoin<PostgresqlRole>().on { it[PostgresqlUser::roleId] }
            .where { it[PostgresqlRole::label] eq roleLabel }
            .execute()
}
