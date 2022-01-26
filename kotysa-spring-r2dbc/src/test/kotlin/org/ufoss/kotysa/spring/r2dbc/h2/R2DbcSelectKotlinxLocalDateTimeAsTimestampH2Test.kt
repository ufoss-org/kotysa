/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import kotlinx.datetime.LocalDateTime
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.spring.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.*


class R2DbcSelectKotlinxLocalDateTimeAsTimestampH2Test : AbstractR2dbcH2Test<KotlinxLocalDateTimeAsTimestampRepositoryH2Select>() {
    override val context = startContext<KotlinxLocalDateTimeAsTimestampRepositoryH2Select>()
    override val repository = getContextRepository<KotlinxLocalDateTimeAsTimestampRepositoryH2Select>()

    @Test
    fun `Verify selectAllByLocalDateTimeNotNull finds kotlinxLocalDateTimeAsTimestampWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNull(LocalDateTime(2019, 11, 4, 0, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullNotEq finds kotlinxLocalDateTimeAsTimestampWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullNotEq(LocalDateTime(2019, 11, 4, 0, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullIn finds both`() {
        val seq = sequenceOf(
                kotlinxLocalDateTimeWithNullable.localDateTimeNotNull,
                kotlinxLocalDateTimeWithoutNullable.localDateTimeNotNull)
        assertThat(repository.selectAllByLocalDateTimeNotNullIn(seq).toIterable())
                .hasSize(2)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithNullable, kotlinxLocalDateTimeAsTimestampWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBefore finds kotlinxLocalDateTimeAsTimestampWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBefore(LocalDateTime(2019, 11, 4, 12, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBefore(LocalDateTime(2019, 11, 4, 0, 0)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds kotlinxLocalDateTimeAsTimestampWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBeforeOrEq(LocalDateTime(2019, 11, 4, 12, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds kotlinxLocalDateTimeAsTimestampWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBeforeOrEq(LocalDateTime(2019, 11, 4, 0, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfter finds kotlinxLocalDateTimeAsTimestampWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfter(LocalDateTime(2019, 11, 5, 12, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfter(LocalDateTime(2019, 11, 6, 0, 0)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds kotlinxLocalDateTimeAsTimestampWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfterOrEq(LocalDateTime(2019, 11, 5, 12, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds kotlinxLocalDateTimeAsTimestampWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfterOrEq(LocalDateTime(2019, 11, 6, 0, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullable finds h2UuidWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullable(LocalDateTime(2018, 11, 4, 0, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullable finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullable(null).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableNotEq finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableNotEq(LocalDateTime(2018, 11, 4, 0, 0)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLocalDateTimeNullableNotEq(null).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBefore finds kotlinxLocalDateTimeAsTimestampWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBefore(LocalDateTime(2018, 11, 4, 12, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBefore(LocalDateTime(2018, 11, 4, 0, 0)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds kotlinxLocalDateTimeAsTimestampWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBeforeOrEq(LocalDateTime(2018, 11, 5, 12, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds kotlinxLocalDateTimeAsTimestampWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBeforeOrEq(LocalDateTime(2018, 11, 4, 0, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfter finds kotlinxLocalDateTimeAsTimestampWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfter(LocalDateTime(2018, 11, 3, 12, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfter(LocalDateTime(2018, 11, 4, 0, 0)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfterOrEq finds kotlinxLocalDateTimeAsTimestampWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfterOrEq(LocalDateTime(2018, 11, 3, 12, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfterOrEq finds kotlinxLocalDateTimeAsTimestampWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfterOrEq(LocalDateTime(2018, 11, 4, 0, 0)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithNullable)
    }
}


class KotlinxLocalDateTimeAsTimestampRepositoryH2Select(private val sqlClient: ReactorSqlClient) : Repository {

    override fun init() {
        createTables()
                .then(insertLocalDateTimes().then())
                .block()
    }

    override fun delete() {
        deleteAll()
                .block()
    }

    private fun createTables() = sqlClient createTable H2_KOTLINX_LOCAL_DATE_TIME_AS_TIMESTAMP

    private fun insertLocalDateTimes() =
            sqlClient.insert(kotlinxLocalDateTimeAsTimestampWithNullable, kotlinxLocalDateTimeAsTimestampWithoutNullable)

    private fun deleteAll() = sqlClient deleteAllFrom H2_KOTLINX_LOCAL_DATE_TIME_AS_TIMESTAMP

    fun selectAllByLocalDateTimeNotNull(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2_KOTLINX_LOCAL_DATE_TIME_AS_TIMESTAMP
                    where H2_KOTLINX_LOCAL_DATE_TIME_AS_TIMESTAMP.localDateTimeNotNull eq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullNotEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2_KOTLINX_LOCAL_DATE_TIME_AS_TIMESTAMP
                    where H2_KOTLINX_LOCAL_DATE_TIME_AS_TIMESTAMP.localDateTimeNotNull notEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullIn(values: Sequence<LocalDateTime>) =
            (sqlClient selectFrom H2_KOTLINX_LOCAL_DATE_TIME_AS_TIMESTAMP
                    where H2_KOTLINX_LOCAL_DATE_TIME_AS_TIMESTAMP.localDateTimeNotNull `in` values
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullBefore(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2_KOTLINX_LOCAL_DATE_TIME_AS_TIMESTAMP
                    where H2_KOTLINX_LOCAL_DATE_TIME_AS_TIMESTAMP.localDateTimeNotNull before localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullBeforeOrEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2_KOTLINX_LOCAL_DATE_TIME_AS_TIMESTAMP
                    where H2_KOTLINX_LOCAL_DATE_TIME_AS_TIMESTAMP.localDateTimeNotNull beforeOrEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullAfter(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2_KOTLINX_LOCAL_DATE_TIME_AS_TIMESTAMP
                    where H2_KOTLINX_LOCAL_DATE_TIME_AS_TIMESTAMP.localDateTimeNotNull after localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullAfterOrEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2_KOTLINX_LOCAL_DATE_TIME_AS_TIMESTAMP
                    where H2_KOTLINX_LOCAL_DATE_TIME_AS_TIMESTAMP.localDateTimeNotNull afterOrEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullable(localDateTime: LocalDateTime?) =
            (sqlClient selectFrom H2_KOTLINX_LOCAL_DATE_TIME_AS_TIMESTAMP
                    where H2_KOTLINX_LOCAL_DATE_TIME_AS_TIMESTAMP.localDateTimeNullable eq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableNotEq(localDateTime: LocalDateTime?) =
            (sqlClient selectFrom H2_KOTLINX_LOCAL_DATE_TIME_AS_TIMESTAMP
                    where H2_KOTLINX_LOCAL_DATE_TIME_AS_TIMESTAMP.localDateTimeNullable notEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableBefore(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2_KOTLINX_LOCAL_DATE_TIME_AS_TIMESTAMP
                    where H2_KOTLINX_LOCAL_DATE_TIME_AS_TIMESTAMP.localDateTimeNullable before localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableBeforeOrEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2_KOTLINX_LOCAL_DATE_TIME_AS_TIMESTAMP
                    where H2_KOTLINX_LOCAL_DATE_TIME_AS_TIMESTAMP.localDateTimeNullable beforeOrEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableAfter(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2_KOTLINX_LOCAL_DATE_TIME_AS_TIMESTAMP
                    where H2_KOTLINX_LOCAL_DATE_TIME_AS_TIMESTAMP.localDateTimeNullable after localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableAfterOrEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2_KOTLINX_LOCAL_DATE_TIME_AS_TIMESTAMP
                    where H2_KOTLINX_LOCAL_DATE_TIME_AS_TIMESTAMP.localDateTimeNullable afterOrEq localDateTime
                    ).fetchAll()
}
