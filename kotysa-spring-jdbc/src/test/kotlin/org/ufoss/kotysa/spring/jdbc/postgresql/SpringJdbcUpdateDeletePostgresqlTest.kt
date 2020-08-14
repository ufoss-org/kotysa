/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcTemplate
import org.ufoss.kotysa.test.*
import java.util.*


class SpringJdbcUpdateDeletePostgresqlTest : AbstractSpringJdbcPostgresqlTest<UserRepositorySpringJdbcPostgresqlUpdateDelete>() {
    override val context = startContext<UserRepositorySpringJdbcPostgresqlUpdateDelete>()

    override val repository = getContextRepository<UserRepositorySpringJdbcPostgresqlUpdateDelete>()

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
        assertThat(repository.deleteUserById(postgresqlJdoe.id))
                .isEqualTo(1)
        assertThat(repository.selectAllUsers())
                .hasSize(1)
                .containsOnly(postgresqlBboss)
        // re-insertUsers jdoe
        repository.insertJDoe()
    }

    @Test
    fun `Verify deleteUserWithJoin works`() {
        assertThat(repository.deleteUserWithJoin(postgresqlUser.label))
                .isEqualTo(1)
        assertThat(repository.selectAllUsers())
                .hasSize(1)
                .containsOnly(postgresqlBboss)
        // re-insertUsers jdoe
        repository.insertJDoe()
    }

    @Test
    fun `Verify updateLastname works`() {
        assertThat(repository.updateLastname("Do"))
                .isEqualTo(1)
        assertThat(repository.selectFirstByFirstame(postgresqlJdoe.firstname))
                .extracting { user -> user?.lastname }
                .isEqualTo("Do")
        repository.updateLastname(postgresqlJdoe.lastname)
    }

    @Test
    fun `Verify updateWithJoin works`() {
        assertThat(repository.updateWithJoin("Do", postgresqlUser.label))
                .isEqualTo(1)
        assertThat(repository.selectFirstByFirstame(postgresqlJdoe.firstname))
                .extracting { user -> user?.lastname }
                .isEqualTo("Do")
        repository.updateLastname(postgresqlJdoe.lastname)
    }

    @Test
    fun `Verify updateAlias works`() {
        assertThat(repository.updateAlias("TheBigBoss"))
                .isEqualTo(1)
        assertThat(repository.selectFirstByFirstame(postgresqlBboss.firstname))
                .extracting { user -> user?.alias }
                .isEqualTo("TheBigBoss")
        assertThat(repository.updateAlias(null))
                .isEqualTo(1)
        assertThat(repository.selectFirstByFirstame(postgresqlBboss.firstname))
                .extracting { user -> user?.alias }
                .isEqualTo(null)
        repository.updateAlias(postgresqlBboss.alias)
    }
}


class UserRepositorySpringJdbcPostgresqlUpdateDelete(client: JdbcTemplate) : AbstractUserRepositorySpringJdbcPostgresql(client) {

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
