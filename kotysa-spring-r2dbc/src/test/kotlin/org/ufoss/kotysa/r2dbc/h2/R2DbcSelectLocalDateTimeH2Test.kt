/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2
/*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.H2LocalDateTime
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.h2LocalDateTimeWithNullable
import org.ufoss.kotysa.test.h2LocalDateTimeWithoutNullable
import java.time.LocalDateTime


class R2DbcSelectLocalDateTimeH2Test : AbstractR2dbcH2Test<LocalDateTimeRepositoryH2Select>() {

    @BeforeAll
    fun beforeAll() {
        context = startContext<LocalDateTimeRepositoryH2Select>()
        repository = getContextRepository()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNull finds h2LocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNull(LocalDateTime.of(2019, 11, 4, 0, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullNotEq finds h2LocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullNotEq(LocalDateTime.of(2019, 11, 4, 0, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullIn finds both`() {
        val seq = sequenceOf(
                h2LocalDateTimeWithNullable.localDateTimeNotNull,
                h2LocalDateTimeWithoutNullable.localDateTimeNotNull)
        assertThat(repository.selectAllByLocalDateTimeNotNullIn(seq).toIterable())
                .hasSize(2)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithNullable, h2LocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBefore finds h2LocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBefore(LocalDateTime.of(2019, 11, 4, 12, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBefore(LocalDateTime.of(2019, 11, 4, 0, 0)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds h2LocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBeforeOrEq(LocalDateTime.of(2019, 11, 4, 12, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds h2LocalDateTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBeforeOrEq(LocalDateTime.of(2019, 11, 4, 0, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfter finds h2LocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfter(LocalDateTime.of(2019, 11, 5, 12, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfter(LocalDateTime.of(2019, 11, 6, 0, 0)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds h2LocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfterOrEq(LocalDateTime.of(2019, 11, 5, 12, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds h2LocalDateTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfterOrEq(LocalDateTime.of(2019, 11, 6, 0, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullable finds h2UuidWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullable(LocalDateTime.of(2018, 11, 4, 0, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullable finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullable(null).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableNotEq finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableNotEq(LocalDateTime.of(2018, 11, 4, 0, 0)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLocalDateTimeNullableNotEq(null).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBefore finds h2LocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBefore(LocalDateTime.of(2018, 11, 4, 12, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBefore(LocalDateTime.of(2018, 11, 4, 0, 0)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds h2LocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBeforeOrEq(LocalDateTime.of(2018, 11, 5, 12, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds h2LocalDateTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBeforeOrEq(LocalDateTime.of(2018, 11, 4, 0, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfter finds h2LocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfter(LocalDateTime.of(2018, 11, 3, 12, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfter(LocalDateTime.of(2018, 11, 4, 0, 0)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfterOrEq finds h2LocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfterOrEq(LocalDateTime.of(2018, 11, 3, 12, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfterOrEq finds h2LocalDateTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfterOrEq(LocalDateTime.of(2018, 11, 4, 0, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateTimeWithNullable)
    }
}


class LocalDateTimeRepositoryH2Select(private val sqlClient: ReactorSqlClient) : Repository {

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

    fun selectAllByLocalDateTimeNotNull(localDateTime: LocalDateTime) = sqlClient.select<H2LocalDateTime>()
            .where { it[H2LocalDateTime::localDateTimeNotNull] eq localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNotNullNotEq(localDateTime: LocalDateTime) = sqlClient.select<H2LocalDateTime>()
            .where { it[H2LocalDateTime::localDateTimeNotNull] notEq localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNotNullIn(values: Sequence<LocalDateTime>) =
            sqlClient.select<H2LocalDateTime>()
                    .where { it[H2LocalDateTime::localDateTimeNotNull] `in` values }
                    .fetchAll()

    fun selectAllByLocalDateTimeNotNullBefore(localDateTime: LocalDateTime) = sqlClient.select<H2LocalDateTime>()
            .where { it[H2LocalDateTime::localDateTimeNotNull] before localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNotNullBeforeOrEq(localDateTime: LocalDateTime) = sqlClient.select<H2LocalDateTime>()
            .where { it[H2LocalDateTime::localDateTimeNotNull] beforeOrEq localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNotNullAfter(localDateTime: LocalDateTime) = sqlClient.select<H2LocalDateTime>()
            .where { it[H2LocalDateTime::localDateTimeNotNull] after localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNotNullAfterOrEq(localDateTime: LocalDateTime) = sqlClient.select<H2LocalDateTime>()
            .where { it[H2LocalDateTime::localDateTimeNotNull] afterOrEq localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNullable(localDateTime: LocalDateTime?) = sqlClient.select<H2LocalDateTime>()
            .where { it[H2LocalDateTime::localDateTimeNullable] eq localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNullableNotEq(localDateTime: LocalDateTime?) = sqlClient.select<H2LocalDateTime>()
            .where { it[H2LocalDateTime::localDateTimeNullable] notEq localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNullableBefore(localDateTime: LocalDateTime) = sqlClient.select<H2LocalDateTime>()
            .where { it[H2LocalDateTime::localDateTimeNullable] before localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNullableBeforeOrEq(localDateTime: LocalDateTime) = sqlClient.select<H2LocalDateTime>()
            .where { it[H2LocalDateTime::localDateTimeNullable] beforeOrEq localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNullableAfter(localDateTime: LocalDateTime) = sqlClient.select<H2LocalDateTime>()
            .where { it[H2LocalDateTime::localDateTimeNullable] after localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNullableAfterOrEq(localDateTime: LocalDateTime) = sqlClient.select<H2LocalDateTime>()
            .where { it[H2LocalDateTime::localDateTimeNullable] afterOrEq localDateTime }
            .fetchAll()
}
*/