/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import java.time.LocalTime


class SpringJdbcSelectLocalTimePostgresqlTest : AbstractSpringJdbcPostgresqlTest<LocalTimeRepositoryPostgresqlSelect>() {
    override val context = startContext<LocalTimeRepositoryPostgresqlSelect>()

    override val repository = getContextRepository<LocalTimeRepositoryPostgresqlSelect>()

    @Test
    fun `Verify selectAllByLocalTimeNotNull finds postgresqlLocalTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNull(LocalTime.of(12, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullNotEq finds postgresqlLocalTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullNotEq(LocalTime.of(12, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBefore finds postgresqlLocalTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullBefore(LocalTime.of(12, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalTimeNotNullBefore(LocalTime.of(12, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBeforeOrEq finds postgresqlLocalTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullBeforeOrEq(LocalTime.of(12, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBeforeOrEq finds postgresqlLocalTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalTimeNotNullBeforeOrEq(LocalTime.of(12, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfter finds postgresqlLocalTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullAfter(LocalTime.of(12, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalTimeNotNullAfter(LocalTime.of(12, 6)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfterOrEq finds postgresqlLocalTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullAfterOrEq(LocalTime.of(12, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfterOrEq finds postgresqlLocalTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalTimeNotNullAfterOrEq(LocalTime.of(12, 6)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullable finds postgresqlLocalTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNullable(LocalTime.of(11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullable finds postgresqlLocalTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNullable(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLocalTimeNullableNotEq(LocalTime.of(11, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableNotEq finds postgresqlLocalTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBefore finds postgresqlLocalTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableBefore(LocalTime.of(11, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalTimeNullableBefore(LocalTime.of(11, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBeforeOrEq finds postgresqlLocalTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableBeforeOrEq(LocalTime.of(11, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBeforeOrEq finds postgresqlLocalTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalTimeNullableBeforeOrEq(LocalTime.of(11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfter finds postgresqlLocalTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableAfter(LocalTime.of(11, 3)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalTimeNullableAfter(LocalTime.of(11, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfterOrEq finds postgresqlLocalTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableAfterOrEq(LocalTime.of(11, 3)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfterOrEq finds postgresqlLocalTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalTimeNullableAfterOrEq(LocalTime.of(11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlLocalTimeWithNullable)
    }
}


class LocalTimeRepositoryPostgresqlSelect(client: JdbcOperations) : Repository {

    private val sqlClient = client.sqlClient(postgresqlTables)

    override fun init() {
        createTables()
        insertLocalTimes()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() =
            sqlClient.createTable<PostgresqlLocalTime>()

    private fun insertLocalTimes() = sqlClient.insert(postgresqlLocalTimeWithNullable, postgresqlLocalTimeWithoutNullable)

    private fun deleteAll() = sqlClient.deleteAllFromTable<PostgresqlLocalTime>()

    fun selectAllByLocalTimeNotNull(localTime: LocalTime) = sqlClient.select<PostgresqlLocalTime>()
            .where { it[PostgresqlLocalTime::localTimeNotNull] eq localTime }
            .fetchAll()

    fun selectAllByLocalTimeNotNullNotEq(localTime: LocalTime) = sqlClient.select<PostgresqlLocalTime>()
            .where { it[PostgresqlLocalTime::localTimeNotNull] notEq localTime }
            .fetchAll()

    fun selectAllByLocalTimeNotNullBefore(localTime: LocalTime) = sqlClient.select<PostgresqlLocalTime>()
            .where { it[PostgresqlLocalTime::localTimeNotNull] before localTime }
            .fetchAll()

    fun selectAllByLocalTimeNotNullBeforeOrEq(localTime: LocalTime) = sqlClient.select<PostgresqlLocalTime>()
            .where { it[PostgresqlLocalTime::localTimeNotNull] beforeOrEq localTime }
            .fetchAll()

    fun selectAllByLocalTimeNotNullAfter(localTime: LocalTime) = sqlClient.select<PostgresqlLocalTime>()
            .where { it[PostgresqlLocalTime::localTimeNotNull] after localTime }
            .fetchAll()

    fun selectAllByLocalTimeNotNullAfterOrEq(localTime: LocalTime) = sqlClient.select<PostgresqlLocalTime>()
            .where { it[PostgresqlLocalTime::localTimeNotNull] afterOrEq localTime }
            .fetchAll()

    fun selectAllByLocalTimeNullable(localTime: LocalTime?) = sqlClient.select<PostgresqlLocalTime>()
            .where { it[PostgresqlLocalTime::localTimeNullable] eq localTime }
            .fetchAll()

    fun selectAllByLocalTimeNullableNotEq(localTime: LocalTime?) = sqlClient.select<PostgresqlLocalTime>()
            .where { it[PostgresqlLocalTime::localTimeNullable] notEq localTime }
            .fetchAll()

    fun selectAllByLocalTimeNullableBefore(localTime: LocalTime) = sqlClient.select<PostgresqlLocalTime>()
            .where { it[PostgresqlLocalTime::localTimeNullable] before localTime }
            .fetchAll()

    fun selectAllByLocalTimeNullableBeforeOrEq(localTime: LocalTime) = sqlClient.select<PostgresqlLocalTime>()
            .where { it[PostgresqlLocalTime::localTimeNullable] beforeOrEq localTime }
            .fetchAll()

    fun selectAllByLocalTimeNullableAfter(localTime: LocalTime) = sqlClient.select<PostgresqlLocalTime>()
            .where { it[PostgresqlLocalTime::localTimeNullable] after localTime }
            .fetchAll()

    fun selectAllByLocalTimeNullableAfterOrEq(localTime: LocalTime) = sqlClient.select<PostgresqlLocalTime>()
            .where { it[PostgresqlLocalTime::localTimeNullable] afterOrEq localTime }
            .fetchAll()
}
