/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcTemplate
import org.ufoss.kotysa.test.*
import java.util.*


class SpringJdbcUpdateDeleteH2Test : AbstractSpringJdbcH2Test<UserRepositorySpringJdbcH2UpdateDelete>() {
    override val context = startContext<UserRepositorySpringJdbcH2UpdateDelete>()

    override val repository = getContextRepository<UserRepositorySpringJdbcH2UpdateDelete>()

    @Test
    fun `Verify deleteAllFromUser works correctly`() {
        assertThat(repository.deleteAllFromUsers())
                .isEqualTo(2)
        assertThat(repository.selectAllUsers())
                .isEmpty()
        // re-insertUsers users
        repository.insertUsers()
    }

    @Test
    fun `Verify deleteUserById works`() {
        assertThat(repository.deleteUserById(h2Jdoe.id))
                .isEqualTo(1)
        assertThat(repository.selectAllUsers())
                .hasSize(1)
                .containsOnly(h2Bboss)
        // re-insertUsers jdoe
        repository.insertJDoe()
    }

    @Test
    fun `Verify deleteUserWithJoin works`() {
        assertThat(repository.deleteUserWithJoin(h2User.label))
                .isEqualTo(1)
        assertThat(repository.selectAllUsers())
                .hasSize(1)
                .containsOnly(h2Bboss)
        // re-insertUsers jdoe
        repository.insertJDoe()
    }

    @Test
    fun `Verify updateLastname works`() {
        assertThat(repository.updateLastname("Do"))
                .isEqualTo(1)
        assertThat(repository.selectFirstByFirstame(h2Jdoe.firstname))
                .extracting { user -> user?.lastname }
                .isEqualTo("Do")
        repository.updateLastname(h2Jdoe.lastname)
    }

    @Test
    fun `Verify updateWithJoin works`() {
        assertThat(repository.updateWithJoin("Do", h2User.label))
                .isEqualTo(1)
        assertThat(repository.selectFirstByFirstame(h2Jdoe.firstname))
                .extracting { user -> user?.lastname }
                .isEqualTo("Do")
        repository.updateLastname(h2Jdoe.lastname)
    }

    @Test
    fun `Verify updateAlias works`() {
        assertThat(repository.updateAlias("TheBigBoss"))
                .isEqualTo(1)
        assertThat(repository.selectFirstByFirstame(h2Bboss.firstname))
                .extracting { user -> user?.alias }
                .isEqualTo("TheBigBoss")
        assertThat(repository.updateAlias(null))
                .isEqualTo(1)
        assertThat(repository.selectFirstByFirstame(h2Bboss.firstname))
                .extracting { user -> user?.alias }
                .isEqualTo(null)
        repository.updateAlias(h2Bboss.alias)
    }
}


class UserRepositorySpringJdbcH2UpdateDelete(client: JdbcTemplate) : AbstractUserRepositorySpringJdbcH2(client) {

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
