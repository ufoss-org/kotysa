/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.ufoss.kotysa.test.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.data.r2dbc.core.DatabaseClient
import java.util.*


class R2DbcUpdateDeleteH2Test : AbstractR2dbcH2Test<UserRepositoryH2UpdateDelete>() {
    override val context = startContext<UserRepositoryH2UpdateDelete>()

    override val repository = getContextRepository<UserRepositoryH2UpdateDelete>()

    @Test
    fun `Verify deleteAllFromUser works correctly`() {
        assertThat(repository.deleteAllFromUsers().block()!!)
                .isEqualTo(2)
        assertThat(repository.selectAllUsers().toIterable())
                .isEmpty()
        // re-insertUsers users
        repository.insertUsers().block()
    }

    @Test
    fun `Verify deleteUserById works`() {
        assertThat(repository.deleteUserById(h2Jdoe.id).block()!!)
                .isEqualTo(1)
        assertThat(repository.selectAllUsers().toIterable())
                .hasSize(1)
                .containsOnly(h2Bboss)
        // re-insertUsers jdoe
        repository.insertJDoe().block()
    }

    @Test
    fun `Verify deleteUserWithJoin works`() {
        assertThat(repository.deleteUserWithJoin(h2User.label).block()!!)
                .isEqualTo(1)
        assertThat(repository.selectAllUsers().toIterable())
                .hasSize(1)
                .containsOnly(h2Bboss)
        // re-insertUsers jdoe
        repository.insertJDoe().block()
    }

    @Test
    fun `Verify updateLastname works`() {
        assertThat(repository.updateLastname("Do").block()!!)
                .isEqualTo(1)
        assertThat(repository.selectFirstByFirstame(h2Jdoe.firstname).block())
                .extracting { user -> user?.lastname }
                .isEqualTo("Do")
        repository.updateLastname(h2Jdoe.lastname).block()
    }

    @Test
    fun `Verify updateWithJoin works`() {
        assertThat(repository.updateWithJoin("Do", h2User.label).block()!!)
                .isEqualTo(1)
        assertThat(repository.selectFirstByFirstame(h2Jdoe.firstname).block())
                .extracting { user -> user?.lastname }
                .isEqualTo("Do")
        repository.updateLastname(h2Jdoe.lastname).block()
    }

    @Test
    fun `Verify updateAlias works`() {
        assertThat(repository.updateAlias("TheBigBoss").block()!!)
                .isEqualTo(1)
        assertThat(repository.selectFirstByFirstame(h2Bboss.firstname).block())
                .extracting { user -> user?.alias }
                .isEqualTo("TheBigBoss")
        assertThat(repository.updateAlias(null).block()!!)
                .isEqualTo(1)
        assertThat(repository.selectFirstByFirstame(h2Bboss.firstname).block())
                .extracting { user -> user?.alias }
                .isEqualTo(null)
        repository.updateAlias(h2Bboss.alias).block()
    }
}


class UserRepositoryH2UpdateDelete(dbClient: DatabaseClient) : AbstractUserRepositoryH2(dbClient) {

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
