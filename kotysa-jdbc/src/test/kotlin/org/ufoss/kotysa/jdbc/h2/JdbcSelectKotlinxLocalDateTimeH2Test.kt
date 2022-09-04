/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import kotlinx.datetime.LocalDateTime
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.test.*

class JdbcSelectKotlinxLocalDateTimeH2Test : AbstractJdbcH2Test<KotlinxLocalDateTimeRepositoryH2Select>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = KotlinxLocalDateTimeRepositoryH2Select(sqlClient)

    @Test
    fun `Verify selectAllByLocalDateTimeNotNull finds kotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNull(LocalDateTime(2019, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullNotEq finds kotlinxLocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullNotEq(LocalDateTime(2019, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullIn finds both`() {
        val seq = sequenceOf(
                kotlinxLocalDateTimeWithNullable.localDateTimeNotNull,
                kotlinxLocalDateTimeWithoutNullable.localDateTimeNotNull)
        assertThat(repository.selectAllByLocalDateTimeNotNullIn(seq))
                .hasSize(2)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable, kotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBefore finds kotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBefore(LocalDateTime(2019, 11, 4, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBefore(LocalDateTime(2019, 11, 4, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds kotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBeforeOrEq(LocalDateTime(2019, 11, 4, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds kotlinxLocalDateTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBeforeOrEq(LocalDateTime(2019, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfter finds kotlinxLocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfter(LocalDateTime(2019, 11, 5, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfter(LocalDateTime(2019, 11, 6, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds kotlinxLocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfterOrEq(LocalDateTime(2019, 11, 5, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds kotlinxLocalDateTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfterOrEq(LocalDateTime(2019, 11, 6, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullable finds h2UuidWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullable(LocalDateTime(2018, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullable finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullable(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableNotEq finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableNotEq(LocalDateTime(2018, 11, 4, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLocalDateTimeNullableNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBefore finds kotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBefore(LocalDateTime(2018, 11, 4, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBefore(LocalDateTime(2018, 11, 4, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds kotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBeforeOrEq(LocalDateTime(2018, 11, 5, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds kotlinxLocalDateTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBeforeOrEq(LocalDateTime(2018, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfter finds kotlinxLocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfter(LocalDateTime(2018, 11, 3, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfter(LocalDateTime(2018, 11, 4, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfterOrEq finds kotlinxLocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfterOrEq(LocalDateTime(2018, 11, 3, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfterOrEq finds kotlinxLocalDateTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfterOrEq(LocalDateTime(2018, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }
}


class KotlinxLocalDateTimeRepositoryH2Select(private val sqlClient: JdbcSqlClient) : Repository {

    override fun init() {
        createTables()
        insertLocalDateTimes()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() {
        sqlClient createTable H2KotlinxLocalDateTimes
    }

    private fun insertLocalDateTimes() {
        sqlClient.insert(kotlinxLocalDateTimeWithNullable, kotlinxLocalDateTimeWithoutNullable)
    }

    private fun deleteAll() = sqlClient deleteAllFrom H2KotlinxLocalDateTimes

    fun selectAllByLocalDateTimeNotNull(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2KotlinxLocalDateTimes
                    where H2KotlinxLocalDateTimes.localDateTimeNotNull eq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullNotEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2KotlinxLocalDateTimes
                    where H2KotlinxLocalDateTimes.localDateTimeNotNull notEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullIn(values: Sequence<LocalDateTime>) =
            (sqlClient selectFrom H2KotlinxLocalDateTimes
                    where H2KotlinxLocalDateTimes.localDateTimeNotNull `in` values
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullBefore(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2KotlinxLocalDateTimes
                    where H2KotlinxLocalDateTimes.localDateTimeNotNull before localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullBeforeOrEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2KotlinxLocalDateTimes
                    where H2KotlinxLocalDateTimes.localDateTimeNotNull beforeOrEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullAfter(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2KotlinxLocalDateTimes
                    where H2KotlinxLocalDateTimes.localDateTimeNotNull after localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullAfterOrEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2KotlinxLocalDateTimes
                    where H2KotlinxLocalDateTimes.localDateTimeNotNull afterOrEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullable(localDateTime: LocalDateTime?) =
            (sqlClient selectFrom H2KotlinxLocalDateTimes
                    where H2KotlinxLocalDateTimes.localDateTimeNullable eq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableNotEq(localDateTime: LocalDateTime?) =
            (sqlClient selectFrom H2KotlinxLocalDateTimes
                    where H2KotlinxLocalDateTimes.localDateTimeNullable notEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableBefore(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2KotlinxLocalDateTimes
                    where H2KotlinxLocalDateTimes.localDateTimeNullable before localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableBeforeOrEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2KotlinxLocalDateTimes
                    where H2KotlinxLocalDateTimes.localDateTimeNullable beforeOrEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableAfter(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2KotlinxLocalDateTimes
                    where H2KotlinxLocalDateTimes.localDateTimeNullable after localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableAfterOrEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2KotlinxLocalDateTimes
                    where H2KotlinxLocalDateTimes.localDateTimeNullable afterOrEq localDateTime
                    ).fetchAll()
}
