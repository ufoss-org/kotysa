/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import java.time.OffsetDateTime
import java.time.ZoneOffset


class SpringJdbcSelectOffsetDateTimePostgresqlTest : AbstractSpringJdbcPostgresqlTest<OffsetDateTimeRepositoryPostgresqlSelect>() {
    override val context = startContext<OffsetDateTimeRepositoryPostgresqlSelect>()

    override val repository = getContextRepository<OffsetDateTimeRepositoryPostgresqlSelect>()

    @Test
    fun `Verify selectAllByLocalDateTimeNotNull finds postgresqlOffsetDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNull(
                OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlOffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullNotEq finds postgresqlOffsetDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullNotEq(
                OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlOffsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBefore finds postgresqlOffsetDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBefore(
                OffsetDateTime.of(2019, 11, 4, 12, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlOffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBefore(
                OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds postgresqlOffsetDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBeforeOrEq(
                OffsetDateTime.of(2019, 11, 4, 12, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlOffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds postgresqlOffsetDateTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBeforeOrEq(
                OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlOffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfter finds postgresqlOffsetDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfter(
                OffsetDateTime.of(2019, 11, 5, 12, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlOffsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfter(
                OffsetDateTime.of(2019, 11, 6, 0, 0, 0, 0, ZoneOffset.UTC)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds postgresqlOffsetDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfterOrEq(
                OffsetDateTime.of(2019, 11, 5, 12, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlOffsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds postgresqlOffsetDateTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfterOrEq(
                OffsetDateTime.of(2019, 11, 6, 0, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlOffsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullable finds postgresqlOffsetDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullable(
                OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlOffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullable finds postgresqlOffsetDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullable(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlOffsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLocalDateTimeNullableNotEq(
                OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableNotEq finds postgresqlOffsetDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlOffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBefore finds postgresqlOffsetDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBefore(
                OffsetDateTime.of(2018, 11, 4, 12, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlOffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBefore(
                OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds postgresqlOffsetDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBeforeOrEq(
                OffsetDateTime.of(2018, 11, 5, 12, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlOffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds postgresqlOffsetDateTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBeforeOrEq(
                OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlOffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfter finds postgresqlOffsetDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfter(
                OffsetDateTime.of(2018, 11, 3, 12, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlOffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfter(
                OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfterOrEq finds postgresqlOffsetDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfterOrEq(
                OffsetDateTime.of(2018, 11, 3, 12, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlOffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfterOrEq finds postgresqlOffsetDateTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfterOrEq(
                OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlOffsetDateTimeWithNullable)
    }
}


class OffsetDateTimeRepositoryPostgresqlSelect(client: JdbcOperations) : Repository {

    private val sqlClient = client.sqlClient(postgresqlTables)

    override fun init() {
        createTables()
        insertOffsetDateTimes()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() =
            sqlClient.createTable<PostgresqlOffsetDateTime>()

    private fun insertOffsetDateTimes() = sqlClient.insert(postgresqlOffsetDateTimeWithNullable, postgresqlOffsetDateTimeWithoutNullable)

    private fun deleteAll() = sqlClient.deleteAllFromTable<PostgresqlOffsetDateTime>()

    fun selectAllByLocalDateTimeNotNull(offsetDateTime: OffsetDateTime) = sqlClient.select<PostgresqlOffsetDateTime>()
            .where { it[PostgresqlOffsetDateTime::offsetDateTimeNotNull] eq offsetDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNotNullNotEq(offsetDateTime: OffsetDateTime) = sqlClient.select<PostgresqlOffsetDateTime>()
            .where { it[PostgresqlOffsetDateTime::offsetDateTimeNotNull] notEq offsetDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNotNullBefore(offsetDateTime: OffsetDateTime) = sqlClient.select<PostgresqlOffsetDateTime>()
            .where { it[PostgresqlOffsetDateTime::offsetDateTimeNotNull] before offsetDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNotNullBeforeOrEq(offsetDateTime: OffsetDateTime) = sqlClient.select<PostgresqlOffsetDateTime>()
            .where { it[PostgresqlOffsetDateTime::offsetDateTimeNotNull] beforeOrEq offsetDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNotNullAfter(offsetDateTime: OffsetDateTime) = sqlClient.select<PostgresqlOffsetDateTime>()
            .where { it[PostgresqlOffsetDateTime::offsetDateTimeNotNull] after offsetDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNotNullAfterOrEq(offsetDateTime: OffsetDateTime) = sqlClient.select<PostgresqlOffsetDateTime>()
            .where { it[PostgresqlOffsetDateTime::offsetDateTimeNotNull] afterOrEq offsetDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNullable(offsetDateTime: OffsetDateTime?) = sqlClient.select<PostgresqlOffsetDateTime>()
            .where { it[PostgresqlOffsetDateTime::offsetDateTimeNullable] eq offsetDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNullableNotEq(offsetDateTime: OffsetDateTime?) = sqlClient.select<PostgresqlOffsetDateTime>()
            .where { it[PostgresqlOffsetDateTime::offsetDateTimeNullable] notEq offsetDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNullableBefore(offsetDateTime: OffsetDateTime) = sqlClient.select<PostgresqlOffsetDateTime>()
            .where { it[PostgresqlOffsetDateTime::offsetDateTimeNullable] before offsetDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNullableBeforeOrEq(offsetDateTime: OffsetDateTime) = sqlClient.select<PostgresqlOffsetDateTime>()
            .where { it[PostgresqlOffsetDateTime::offsetDateTimeNullable] beforeOrEq offsetDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNullableAfter(offsetDateTime: OffsetDateTime) = sqlClient.select<PostgresqlOffsetDateTime>()
            .where { it[PostgresqlOffsetDateTime::offsetDateTimeNullable] after offsetDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNullableAfterOrEq(offsetDateTime: OffsetDateTime) = sqlClient.select<PostgresqlOffsetDateTime>()
            .where { it[PostgresqlOffsetDateTime::offsetDateTimeNullable] afterOrEq offsetDateTime }
            .fetchAll()
}
