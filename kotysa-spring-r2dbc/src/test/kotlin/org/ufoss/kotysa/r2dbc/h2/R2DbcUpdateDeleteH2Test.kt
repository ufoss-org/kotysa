/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.getBean
import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.r2dbc.transaction.ReactorTransactionalOp
import org.ufoss.kotysa.test.*
import reactor.kotlin.test.test
import java.util.*


class R2DbcUpdateDeleteH2Test : AbstractR2dbcH2Test<UserRepositoryH2UpdateDelete>() {
    override val context = startContext<UserRepositoryH2UpdateDelete>()

    override val repository = getContextRepository<UserRepositoryH2UpdateDelete>()
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
            repository.deleteUserById(h2Jdoe.id)
                    .doOnNext { n -> assertThat(n).isEqualTo(1) }
                    .thenMany(repository.selectAllUsers())
        }.test()
                .expectNext(h2Bboss)
                .verifyComplete()
    }

    @Test
    fun `Verify deleteUserWithJoin works`() {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            repository.deleteUserWithJoin(h2User.label)
                    .doOnNext { n -> assertThat(n).isEqualTo(1) }
                    .thenMany(repository.selectAllUsers())
        }.test()
                .expectNext(h2Bboss)
                .verifyComplete()
    }

    @Test
    fun `Verify updateLastname works`() {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            repository.updateLastname("Do")
                    .doOnNext { n -> assertThat(n).isEqualTo(1) }
                    .then(repository.selectFirstByFirstame(h2Jdoe.firstname))
        }.test()
                .expectNextMatches { user -> "Do" == user.lastname }
                .verifyComplete()
    }

    @Test
    fun `Verify updateWithJoin works`() {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            repository.updateWithJoin("Doee", h2User.label)
                    .doOnNext { n -> assertThat(n).isEqualTo(1) }
                    .then(repository.selectFirstByFirstame(h2Jdoe.firstname))
        }.test()
                .expectNextMatches { user -> "Doee" == user.lastname }
                .verifyComplete()
    }

    @Test
    fun `Verify updateAlias works`() {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            repository.updateAlias("TheBigBoss")
                    .doOnNext { n -> assertThat(n).isEqualTo(1) }
                    .then(repository.selectFirstByFirstame(h2Bboss.firstname))
                    .doOnNext { user -> assertThat(user.alias).isEqualTo("TheBigBoss") }
                    .then(repository.updateAlias(null))
                    .doOnNext { n -> assertThat(n).isEqualTo(1) }
                    .then(repository.selectFirstByFirstame(h2Bboss.firstname))
        }.test()
                .expectNextMatches { user -> null == user.alias }
                .verifyComplete()
    }
}


class UserRepositoryH2UpdateDelete(
        sqlClient: ReactorSqlClient,
        operator: ReactorTransactionalOp
) : AbstractUserRepositoryH2(sqlClient, operator) {

    fun deleteUserById(id: UUID) = sqlClient.deleteFromTable<H2User>()
            .where { it[H2User::id] eq id }
            .execute()

    fun deleteUserWithJoin(roleLabel: String) = sqlClient.deleteFromTable<H2User>()
            .innerJoin<H2Role>().on { it[H2User::roleId] }
            .where { it[H2Role::label] eq roleLabel }
            .execute()

    fun updateLastname(newLastname: String) = sqlClient.updateTable<H2User>()
            .set { it[H2User::lastname] = newLastname }
            .where { it[H2User::id] eq h2Jdoe.id }
            .execute()

    fun updateAlias(newAlias: String?) = sqlClient.updateTable<H2User>()
            .set { it[H2User::alias] = newAlias }
            .where { it[H2User::id] eq h2Bboss.id }
            .execute()

    fun updateWithJoin(newLastname: String, roleLabel: String) = sqlClient.updateTable<H2User>()
            .set { it[H2User::lastname] = newLastname }
            .innerJoin<H2Role>().on { it[H2User::roleId] }
            .where { it[H2Role::label] eq roleLabel }
            .execute()
}
