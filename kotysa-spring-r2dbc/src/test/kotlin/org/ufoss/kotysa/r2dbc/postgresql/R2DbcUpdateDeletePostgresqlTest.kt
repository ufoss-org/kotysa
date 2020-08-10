/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.test.*
import java.util.*


class R2DbcUpdateDeletePostgresqlTest : AbstractR2dbcPostgresqlTest<UserRepositoryPostgresqlUpdateDelete>() {
    override val context = startContext<UserRepositoryPostgresqlUpdateDelete>()

    override val repository = getContextRepository<UserRepositoryPostgresqlUpdateDelete>()

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
        assertThat(repository.deleteUserById(postgresqlJdoe.id).block()!!)
                .isEqualTo(1)
        assertThat(repository.selectAllUsers().toIterable())
                .hasSize(1)
                .containsOnly(postgresqlBboss)
        // re-insertUsers jdoe
        repository.insertJDoe().block()
    }

    @Test
    fun `Verify deleteUserWithJoin works`() {
        assertThat(repository.deleteUserWithJoin(postgresqlUser.label).block()!!)
                .isEqualTo(1)
        assertThat(repository.selectAllUsers().toIterable())
                .hasSize(1)
                .containsOnly(postgresqlBboss)
        // re-insertUsers jdoe
        repository.insertJDoe().block()
    }

    @Test
    fun `Verify updateLastname works`() {
        assertThat(repository.updateLastname("Do").block()!!)
                .isEqualTo(1)
        assertThat(repository.selectFirstByFirstame(postgresqlJdoe.firstname).block())
                .extracting { user -> user?.lastname }
                .isEqualTo("Do")
        repository.updateLastname(postgresqlJdoe.lastname).block()
    }

    @Test
    fun `Verify updateWithJoin works`() {
        assertThat(repository.updateWithJoin("Do", postgresqlUser.label).block()!!)
                .isEqualTo(1)
        assertThat(repository.selectFirstByFirstame(postgresqlJdoe.firstname).block())
                .extracting { user -> user?.lastname }
                .isEqualTo("Do")
        repository.updateLastname(postgresqlJdoe.lastname).block()
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


class UserRepositoryPostgresqlUpdateDelete(dbClient: DatabaseClient) : AbstractUserRepositoryPostgresql(dbClient) {

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
