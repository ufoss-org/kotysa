/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2
/*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.getBean
import org.springframework.transaction.reactive.TransactionalOperator
import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.r2dbc.transaction.transactionalOp
import org.ufoss.kotysa.test.*
import reactor.kotlin.test.test
import java.util.*


class R2DbcUpdateDeleteH2Test : AbstractR2dbcH2Test<UserRepositoryH2UpdateDelete>() {

    @BeforeAll
    fun beforeAll() {
        context = startContext<UserRepositoryH2UpdateDelete>()
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
    fun `Verify deleteUserIn works`() {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            repository.deleteUserIn(listOf(h2Jdoe.id, UUID.randomUUID()))
                    .doOnNext { n -> assertThat(n).isEqualTo(1) }
                    .thenMany(repository.selectAllUsers())
        }.test()
                .expectNext(h2Bboss)
                .verifyComplete()
    }

    @Test
    fun `Verify deleteUserIn no match`() {
        assertThat(repository.deleteUserIn(listOf(UUID.randomUUID(), UUID.randomUUID())).block())
                .isEqualTo(0)
        assertThat(repository.selectAllUsers().toIterable())
                .hasSize(2)
    }

    @Test
    fun `Verify updateLastname works`() {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            repository.updateLastname("Do")
                    .doOnNext { n -> assertThat(n).isEqualTo(1) }
                    .then(repository.selectFirstByFirstname(h2Jdoe.firstname))
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
                    .then(repository.selectFirstByFirstname(h2Jdoe.firstname))
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
                    .then(repository.selectFirstByFirstname(h2Bboss.firstname))
                    .doOnNext { user -> assertThat(user.alias).isEqualTo("TheBigBoss") }
                    .then(repository.updateAlias(null))
                    .doOnNext { n -> assertThat(n).isEqualTo(1) }
                    .then(repository.selectFirstByFirstname(h2Bboss.firstname))
        }.test()
                .expectNextMatches { user -> null == user.alias }
                .verifyComplete()
    }

    @Test
    fun `Verify updateLastnameIn works`() {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            repository.updateLastnameIn("Do", listOf(h2Jdoe.id, UUID.randomUUID()))
                    .doOnNext { n -> assertThat(n).isEqualTo(1) }
                    .then(repository.selectFirstByFirstname(h2Jdoe.firstname))
        }.test()
                .expectNextMatches { user -> "Do" == user.lastname }
                .verifyComplete()
    }

    @Test
    fun `Verify updateLastnameIn no match`() {
        assertThat(repository.updateLastnameIn("Do", listOf(UUID.randomUUID(), UUID.randomUUID())).block())
                .isEqualTo(0)
        assertThat(repository.selectFirstByFirstname(h2Jdoe.firstname).block())
                .extracting { user -> user?.lastname }
                .isEqualTo("Doe")
    }
}


class UserRepositoryH2UpdateDelete(
        sqlClient: ReactorSqlClient,
) : AbstractUserRepositoryH2(sqlClient) {

    fun deleteUserById(id: UUID) = sqlClient.deleteFromTable<H2User>()
            .where { it[H2User::id] eq id }
            .execute()

    fun deleteUserWithJoin(roleLabel: String) = sqlClient.deleteFromTable<H2User>()
            .innerJoin<H2Role>().on { it[H2User::roleId] }
            .where { it[H2Role::label] eq roleLabel }
            .execute()

    fun deleteUserIn(ids: Collection<UUID>) =
            sqlClient.deleteFromTable<H2User>()
                    .where { it[H2User::id] `in` ids }
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

    fun updateLastnameIn(newLastname: String, ids: Collection<UUID>) =
            sqlClient.updateTable<H2User>()
                    .set { it[H2User::lastname] = newLastname }
                    .where { it[H2User::id] `in` ids }
                    .execute()
}
*/