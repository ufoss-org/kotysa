/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.H2LocalDateTime
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.h2LocalDateTimeWithNullable
import org.ufoss.kotysa.test.h2LocalDateTimeWithoutNullable
import java.time.LocalDateTime


class R2DbcSelectLocalDateTimeAsTimestampH2Test : AbstractR2dbcH2Test<LocalDateTimeAsTimestampRepositoryH2Select>() {
    override val context = startContext<LocalDateTimeAsTimestampRepositoryH2Select>()

    override val repository = getContextRepository<LocalDateTimeAsTimestampRepositoryH2Select>()

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNull finds h2LocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNull(LocalDateTime.of(2019, 11, 4, 0, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullNotEq finds h2LocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullNotEq(LocalDateTime.of(2019, 11, 4, 0, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullBefore finds h2LocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullBefore(LocalDateTime.of(2019, 11, 4, 12, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullBefore(LocalDateTime.of(2019, 11, 4, 0, 0)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullBeforeOrEq finds h2LocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullBeforeOrEq(LocalDateTime.of(2019, 11, 4, 12, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullBeforeOrEq finds h2LocalDateTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullBeforeOrEq(LocalDateTime.of(2019, 11, 4, 0, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullAfter finds h2LocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullAfter(LocalDateTime.of(2019, 11, 5, 12, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullAfter(LocalDateTime.of(2019, 11, 6, 0, 0)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullAfterOrEq finds h2LocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullAfterOrEq(LocalDateTime.of(2019, 11, 5, 12, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullAfterOrEq finds h2LocalDateTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullAfterOrEq(LocalDateTime.of(2019, 11, 6, 0, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullable finds h2UuidWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullable(LocalDateTime.of(2018, 11, 4, 0, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullable finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullable(null).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableNotEq finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableNotEq(LocalDateTime.of(2018, 11, 4, 0, 0)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableNotEq(null).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableBefore finds h2LocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableBefore(LocalDateTime.of(2018, 11, 4, 12, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableBefore(LocalDateTime.of(2018, 11, 4, 0, 0)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableBeforeOrEq finds h2LocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableBeforeOrEq(LocalDateTime.of(2018, 11, 5, 12, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableBeforeOrEq finds h2LocalDateTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableBeforeOrEq(LocalDateTime.of(2018, 11, 4, 0, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableAfter finds h2LocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableAfter(LocalDateTime.of(2018, 11, 3, 12, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableAfter(LocalDateTime.of(2018, 11, 4, 0, 0)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableAfterOrEq finds h2LocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableAfterOrEq(LocalDateTime.of(2018, 11, 3, 12, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableAfterOrEq finds h2LocalDateTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableAfterOrEq(LocalDateTime.of(2018, 11, 4, 0, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithNullable)
    }
}


class LocalDateTimeAsTimestampRepositoryH2Select(private val sqlClient: ReactorSqlClient) : Repository {

    override fun init() {
        createTables()
                .then(insertLocalDateTimes())
                .block()
    }

    override fun delete() {
        deleteAll()
                .block()
    }

    private fun createTables() =
            sqlClient.createTable<H2LocalDateTime>()

    private fun insertLocalDateTimes() = sqlClient.insert(h2LocalDateTimeWithNullable, h2LocalDateTimeWithoutNullable)

    private fun deleteAll() = sqlClient.deleteAllFromTable<H2LocalDateTime>()

    fun selectAllByLocalDateTimeAsTimestampNotNull(localDateTime: LocalDateTime) = sqlClient.select<H2LocalDateTime>()
            .where { it[H2LocalDateTime::localDateTimeAsTimestampNotNull] eq localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNotNullNotEq(localDateTime: LocalDateTime) = sqlClient.select<H2LocalDateTime>()
            .where { it[H2LocalDateTime::localDateTimeAsTimestampNotNull] notEq localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNotNullBefore(localDateTime: LocalDateTime) = sqlClient.select<H2LocalDateTime>()
            .where { it[H2LocalDateTime::localDateTimeAsTimestampNotNull] before localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNotNullBeforeOrEq(localDateTime: LocalDateTime) = sqlClient.select<H2LocalDateTime>()
            .where { it[H2LocalDateTime::localDateTimeAsTimestampNotNull] beforeOrEq localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNotNullAfter(localDateTime: LocalDateTime) = sqlClient.select<H2LocalDateTime>()
            .where { it[H2LocalDateTime::localDateTimeAsTimestampNotNull] after localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNotNullAfterOrEq(localDateTime: LocalDateTime) = sqlClient.select<H2LocalDateTime>()
            .where { it[H2LocalDateTime::localDateTimeAsTimestampNotNull] afterOrEq localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNullable(localDateTime: LocalDateTime?) = sqlClient.select<H2LocalDateTime>()
            .where { it[H2LocalDateTime::localDateTimeAsTimestampNullable] eq localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNullableNotEq(localDateTime: LocalDateTime?) = sqlClient.select<H2LocalDateTime>()
            .where { it[H2LocalDateTime::localDateTimeAsTimestampNullable] notEq localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNullableBefore(localDateTime: LocalDateTime) = sqlClient.select<H2LocalDateTime>()
            .where { it[H2LocalDateTime::localDateTimeAsTimestampNullable] before localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNullableBeforeOrEq(localDateTime: LocalDateTime) = sqlClient.select<H2LocalDateTime>()
            .where { it[H2LocalDateTime::localDateTimeAsTimestampNullable] beforeOrEq localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNullableAfter(localDateTime: LocalDateTime) = sqlClient.select<H2LocalDateTime>()
            .where { it[H2LocalDateTime::localDateTimeAsTimestampNullable] after localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNullableAfterOrEq(localDateTime: LocalDateTime) = sqlClient.select<H2LocalDateTime>()
            .where { it[H2LocalDateTime::localDateTimeAsTimestampNullable] afterOrEq localDateTime }
            .fetchAll()
}
