/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import kotlinx.datetime.LocalDateTime
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*


class SpringJdbcSelectKotlinxLocalDateTimePostgresqlTest : AbstractSpringJdbcPostgresqlTest<KotlinxLocalDateTimeRepositoryPostgresqlSelect>() {
    override val context = startContext<KotlinxLocalDateTimeRepositoryPostgresqlSelect>()

    override val repository = getContextRepository<KotlinxLocalDateTimeRepositoryPostgresqlSelect>()

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNull finds postgresqlKotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNull(LocalDateTime(2019, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlKotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullNotEq finds postgresqlKotlinxLocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullNotEq(LocalDateTime(2019, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlKotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullBefore finds postgresqlKotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullBefore(LocalDateTime(2019, 11, 4, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlKotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullBefore(LocalDateTime(2019, 11, 4, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullBeforeOrEq finds postgresqlKotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullBeforeOrEq(LocalDateTime(2019, 11, 4, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlKotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullBeforeOrEq finds postgresqlKotlinxLocalDateTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullBeforeOrEq(LocalDateTime(2019, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlKotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullAfter finds postgresqlKotlinxLocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullAfter(LocalDateTime(2019, 11, 5, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlKotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullAfter(LocalDateTime(2019, 11, 6, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullAfterOrEq finds postgresqlKotlinxLocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullAfterOrEq(LocalDateTime(2019, 11, 5, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlKotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullAfterOrEq finds postgresqlKotlinxLocalDateTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullAfterOrEq(LocalDateTime(2019, 11, 6, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlKotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullable finds postgresqlKotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullable(LocalDateTime(2018, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlKotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullable finds postgresqlKotlinxLocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullable(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlKotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableNotEq finds postgresqlKotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableNotEq(LocalDateTime(2018, 11, 4, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlKotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableBefore finds postgresqlKotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableBefore(LocalDateTime(2018, 11, 4, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlKotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableBefore(LocalDateTime(2018, 11, 4, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableBeforeOrEq finds postgresqlKotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableBeforeOrEq(LocalDateTime(2018, 11, 5, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlKotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableBeforeOrEq finds postgresqlKotlinxLocalDateTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableBeforeOrEq(LocalDateTime(2018, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlKotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableAfter finds postgresqlKotlinxLocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableAfter(LocalDateTime(2018, 11, 3, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlKotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableAfter(LocalDateTime(2018, 11, 4, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableAfterOrEq finds postgresqlKotlinxLocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableAfterOrEq(LocalDateTime(2018, 11, 3, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlKotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableAfterOrEq finds postgresqlKotlinxLocalDateTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableAfterOrEq(LocalDateTime(2018, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlKotlinxLocalDateTimeWithNullable)
    }
}


class KotlinxLocalDateTimeRepositoryPostgresqlSelect(client: JdbcOperations) : Repository {

    private val sqlClient = client.sqlClient(postgresqlTables)

    override fun init() {
        createTables()
        insertLocalDateTimes()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() =
            sqlClient.createTable<PostgresqlKotlinxLocalDateTime>()

    private fun insertLocalDateTimes() =
            sqlClient.insert(postgresqlKotlinxLocalDateTimeWithNullable, postgresqlKotlinxLocalDateTimeWithoutNullable)

    private fun deleteAll() = sqlClient.deleteAllFromTable<PostgresqlKotlinxLocalDateTime>()

    fun selectAllByLocalDateTimeAsTimestampNotNull(localDateTime: LocalDateTime) =
            sqlClient.select<PostgresqlKotlinxLocalDateTime>()
                    .where { it[PostgresqlKotlinxLocalDateTime::localDateTimeAsTimestampNotNull] eq localDateTime }
                    .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNotNullNotEq(localDateTime: LocalDateTime) =
            sqlClient.select<PostgresqlKotlinxLocalDateTime>()
                    .where { it[PostgresqlKotlinxLocalDateTime::localDateTimeAsTimestampNotNull] notEq localDateTime }
                    .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNotNullBefore(localDateTime: LocalDateTime) =
            sqlClient.select<PostgresqlKotlinxLocalDateTime>()
                    .where { it[PostgresqlKotlinxLocalDateTime::localDateTimeAsTimestampNotNull] before localDateTime }
                    .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNotNullBeforeOrEq(localDateTime: LocalDateTime) =
            sqlClient.select<PostgresqlKotlinxLocalDateTime>()
                    .where { it[PostgresqlKotlinxLocalDateTime::localDateTimeAsTimestampNotNull] beforeOrEq localDateTime }
                    .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNotNullAfter(localDateTime: LocalDateTime) =
            sqlClient.select<PostgresqlKotlinxLocalDateTime>()
                    .where { it[PostgresqlKotlinxLocalDateTime::localDateTimeAsTimestampNotNull] after localDateTime }
                    .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNotNullAfterOrEq(localDateTime: LocalDateTime) =
            sqlClient.select<PostgresqlKotlinxLocalDateTime>()
                    .where { it[PostgresqlKotlinxLocalDateTime::localDateTimeAsTimestampNotNull] afterOrEq localDateTime }
                    .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNullable(localDateTime: LocalDateTime?) =
            sqlClient.select<PostgresqlKotlinxLocalDateTime>()
                    .where { it[PostgresqlKotlinxLocalDateTime::localDateTimeAsTimestampNullable] eq localDateTime }
                    .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNullableNotEq(localDateTime: LocalDateTime?) =
            sqlClient.select<PostgresqlKotlinxLocalDateTime>()
                    .where { it[PostgresqlKotlinxLocalDateTime::localDateTimeAsTimestampNullable] notEq localDateTime }
                    .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNullableBefore(localDateTime: LocalDateTime) =
            sqlClient.select<PostgresqlKotlinxLocalDateTime>()
                    .where { it[PostgresqlKotlinxLocalDateTime::localDateTimeAsTimestampNullable] before localDateTime }
                    .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNullableBeforeOrEq(localDateTime: LocalDateTime) =
            sqlClient.select<PostgresqlKotlinxLocalDateTime>()
                    .where { it[PostgresqlKotlinxLocalDateTime::localDateTimeAsTimestampNullable] beforeOrEq localDateTime }
                    .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNullableAfter(localDateTime: LocalDateTime) =
            sqlClient.select<PostgresqlKotlinxLocalDateTime>()
                    .where { it[PostgresqlKotlinxLocalDateTime::localDateTimeAsTimestampNullable] after localDateTime }
                    .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNullableAfterOrEq(localDateTime: LocalDateTime) =
            sqlClient.select<PostgresqlKotlinxLocalDateTime>()
                    .where { it[PostgresqlKotlinxLocalDateTime::localDateTimeAsTimestampNullable] afterOrEq localDateTime }
                    .fetchAll()
}
