/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.ufoss.kotysa.Tables
import org.ufoss.kotysa.test.*
import java.time.OffsetDateTime
import java.time.ZoneOffset

class SqLiteSelectOffsetDateTimeTest : AbstractSqLiteTest<OffsetDateTimeRepositorySelect>() {

    override fun getRepository(sqLiteTables: Tables) = OffsetDateTimeRepositorySelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNull finds offsetDateTimeWithNullable`() {
        assertThat(
                repository.selectAllByOffsetDateTimeNotNull(
                        OffsetDateTime.of(
                                2019,
                                11,
                                4,
                                0,
                                0,
                                0,
                                0,
                                ZoneOffset.UTC
                        )
                )
        )
                .hasSize(1)
                .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNull finds offsetDateTimeWithoutNullable`() {
        assertThat(
                repository.selectAllByOffsetDateTimeNotNull(
                        OffsetDateTime.of(
                                2019,
                                11,
                                6,
                                0,
                                0,
                                0,
                                0,
                                ZoneOffset.UTC
                        )
                )
        )
                .hasSize(1)
                .containsExactlyInAnyOrder(offsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullNotEq finds offsetDateTimeWithoutNullable`() {
        assertThat(
                repository.selectAllByOffsetDateTimeNotNullNotEq(
                        OffsetDateTime.of(
                                2019,
                                11,
                                4,
                                0,
                                0,
                                0,
                                0,
                                ZoneOffset.UTC
                        )
                )
        )
                .hasSize(1)
                .containsExactlyInAnyOrder(offsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullIn finds both`() {
        val seq = sequenceOf(
                OffsetDateTime.of(
                        2019,
                        11,
                        4,
                        0,
                        0,
                        0,
                        0,
                        ZoneOffset.UTC
                ),
                OffsetDateTime.of(
                        2019,
                        11,
                        6,
                        0,
                        0,
                        0,
                        0,
                        ZoneOffset.UTC
                )
        )
        assertThat(repository.selectAllByOffsetDateTimeNotNullIn(seq))
                .hasSize(2)
                .containsExactlyInAnyOrder(offsetDateTimeWithNullable, offsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullBefore finds offsetDateTimeWithNullable`() {
        assertThat(
                repository.selectAllByOffsetDateTimeNotNullBefore(
                        OffsetDateTime.of(
                                2019,
                                11,
                                4,
                                12,
                                0,
                                0,
                                0,
                                ZoneOffset.UTC
                        )
                )
        )
                .hasSize(1)
                .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullBefore finds no results when equals`() {
        assertThat(
                repository.selectAllByOffsetDateTimeNotNullBefore(
                        OffsetDateTime.of(
                                2019,
                                11,
                                4,
                                0,
                                0,
                                0,
                                0,
                                ZoneOffset.UTC
                        )
                )
        )
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullBeforeOrEq finds offsetDateTimeWithNullable`() {
        assertThat(
                repository.selectAllByOffsetDateTimeNotNullBeforeOrEq(
                        OffsetDateTime.of(
                                2019,
                                11,
                                4,
                                12,
                                0,
                                0,
                                0,
                                ZoneOffset.UTC
                        )
                )
        )
                .hasSize(1)
                .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullBeforeOrEq finds offsetDateTimeWithNullable when equals`() {
        assertThat(
                repository.selectAllByOffsetDateTimeNotNullBeforeOrEq(
                        OffsetDateTime.of(
                                2019,
                                11,
                                4,
                                0,
                                0,
                                0,
                                0,
                                ZoneOffset.UTC
                        )
                )
        )
                .hasSize(1)
                .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullAfter finds offsetDateTimeWithoutNullable`() {
        assertThat(
                repository.selectAllByOffsetDateTimeNotNullAfter(
                        OffsetDateTime.of(
                                2019,
                                11,
                                5,
                                12,
                                0,
                                0,
                                0,
                                ZoneOffset.UTC
                        )
                )
        )
                .hasSize(1)
                .containsExactlyInAnyOrder(offsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullAfter finds no results when equals`() {
        assertThat(
                repository.selectAllByOffsetDateTimeNotNullAfter(
                        OffsetDateTime.of(
                                2019,
                                11,
                                6,
                                0,
                                0,
                                0,
                                0,
                                ZoneOffset.UTC
                        )
                )
        )
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullAfterOrEq finds offsetDateTimeWithoutNullable`() {
        assertThat(
                repository.selectAllByOffsetDateTimeNotNullAfterOrEq(
                        OffsetDateTime.of(
                                2019,
                                11,
                                5,
                                12,
                                0,
                                0,
                                0,
                                ZoneOffset.UTC
                        )
                )
        )
                .hasSize(1)
                .containsExactlyInAnyOrder(offsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullAfterOrEq finds offsetDateTimeWithoutNullable when equals`() {
        assertThat(
                repository.selectAllByOffsetDateTimeNotNullAfterOrEq(
                        OffsetDateTime.of(
                                2019,
                                11,
                                6,
                                0,
                                0,
                                0,
                                0,
                                ZoneOffset.UTC
                        )
                )
        )
                .hasSize(1)
                .containsExactlyInAnyOrder(offsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullable finds offsetDateTimeWithNullable`() {
        assertThat(
                repository.selectAllByOffsetDateTimeNullable(
                        OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0,
                                ZoneOffset.ofHoursMinutesSeconds(1, 2, 3))
                )
        )
                .hasSize(1)
                .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullable finds offsetDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByOffsetDateTimeNullable(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(offsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableNotEq finds no result`() {
        assertThat(
                repository.selectAllByOffsetDateTimeNullableNotEq(
                        OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0,
                                ZoneOffset.ofHoursMinutesSeconds(1, 2, 3))
                )
        )
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableNotEq finds no results`() {
        assertThat(repository.selectAllByOffsetDateTimeNullableNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableBefore finds offsetDateTimeWithNullable`() {
        assertThat(
                repository.selectAllByOffsetDateTimeNullableBefore(
                        OffsetDateTime.of(2018, 11, 4, 12, 0, 0, 0,
                                ZoneOffset.ofHoursMinutesSeconds(1, 2, 3))
                )
        )
                .hasSize(1)
                .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableBefore finds no results when equals`() {
        assertThat(
                repository.selectAllByOffsetDateTimeNullableBefore(
                        OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0,
                                ZoneOffset.ofHoursMinutesSeconds(1, 2, 3))
                )
        )
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableBeforeOrEq finds offsetDateTimeWithNullable`() {
        assertThat(
                repository.selectAllByOffsetDateTimeNullableBeforeOrEq(
                        OffsetDateTime.of(
                                2018,
                                11,
                                5,
                                12,
                                0,
                                0,
                                0,
                                ZoneOffset.UTC
                        )
                )
        )
                .hasSize(1)
                .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableBeforeOrEq finds offsetDateTimeWithNullable when equals`() {
        assertThat(
                repository.selectAllByOffsetDateTimeNullableBeforeOrEq(
                        OffsetDateTime.of(
                                2018,
                                11,
                                4,
                                0,
                                0,
                                0,
                                0,
                                ZoneOffset.UTC
                        )
                )
        )
                .hasSize(1)
                .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableAfter finds offsetDateTimeWithNullable`() {
        assertThat(
                repository.selectAllByOffsetDateTimeNullableAfter(
                        OffsetDateTime.of(
                                2018,
                                11,
                                3,
                                12,
                                0,
                                0,
                                0,
                                ZoneOffset.UTC
                        )
                )
        )
                .hasSize(1)
                .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableAfter finds no results when equals`() {
        assertThat(
                repository.selectAllByOffsetDateTimeNullableAfter(
                        OffsetDateTime.of(
                                2018,
                                11,
                                4,
                                0,
                                0,
                                0,
                                0,
                                ZoneOffset.UTC
                        )
                )
        )
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableAfterOrEq finds offsetDateTimeWithNullable`() {
        assertThat(
                repository.selectAllByOffsetDateTimeNullableAfterOrEq(
                        OffsetDateTime.of(
                                2018,
                                11,
                                3,
                                12,
                                0,
                                0,
                                0,
                                ZoneOffset.UTC
                        )
                )
        )
                .hasSize(1)
                .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableAfterOrEq finds offsetDateTimeWithNullable when equals`() {
        assertThat(
                repository.selectAllByOffsetDateTimeNullableAfterOrEq(
                        OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0,
                                ZoneOffset.ofHoursMinutesSeconds(1, 2, 3))
                )
        )
                .hasSize(1)
                .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }
}

class OffsetDateTimeRepositorySelect(sqLiteOpenHelper: SQLiteOpenHelper, tables: Tables) : Repository {

    private val sqlClient = sqLiteOpenHelper.sqlClient(tables)

    override fun init() {
        createTables()
        insertOffsetDateTimes()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() {
        sqlClient createTable SQLITE_OFFSET_DATE_TIME
    }

    private fun insertOffsetDateTimes() {
        sqlClient.insert(offsetDateTimeWithNullable, offsetDateTimeWithoutNullable)
    }

    private fun deleteAll() = sqlClient deleteAllFrom SQLITE_OFFSET_DATE_TIME

    fun selectAllByOffsetDateTimeNotNull(offsetDateTime: OffsetDateTime) =
            (sqlClient selectFrom SQLITE_OFFSET_DATE_TIME
                    where SQLITE_OFFSET_DATE_TIME.offsetDateTimeNotNull eq offsetDateTime
                    ).fetchAll()

    fun selectAllByOffsetDateTimeNotNullNotEq(offsetDateTime: OffsetDateTime) =
            (sqlClient selectFrom SQLITE_OFFSET_DATE_TIME
                    where SQLITE_OFFSET_DATE_TIME.offsetDateTimeNotNull notEq offsetDateTime
                    ).fetchAll()

    fun selectAllByOffsetDateTimeNotNullIn(values: Sequence<OffsetDateTime>) =
            (sqlClient selectFrom SQLITE_OFFSET_DATE_TIME
                    where SQLITE_OFFSET_DATE_TIME.offsetDateTimeNotNull `in` values
                    ).fetchAll()

    fun selectAllByOffsetDateTimeNotNullBefore(offsetDateTime: OffsetDateTime) =
            (sqlClient selectFrom SQLITE_OFFSET_DATE_TIME
                    where SQLITE_OFFSET_DATE_TIME.offsetDateTimeNotNull before offsetDateTime
                    ).fetchAll()

    fun selectAllByOffsetDateTimeNotNullBeforeOrEq(offsetDateTime: OffsetDateTime) =
            (sqlClient selectFrom SQLITE_OFFSET_DATE_TIME
                    where SQLITE_OFFSET_DATE_TIME.offsetDateTimeNotNull beforeOrEq offsetDateTime
                    ).fetchAll()

    fun selectAllByOffsetDateTimeNotNullAfter(offsetDateTime: OffsetDateTime) =
            (sqlClient selectFrom SQLITE_OFFSET_DATE_TIME
                    where SQLITE_OFFSET_DATE_TIME.offsetDateTimeNotNull after offsetDateTime
                    ).fetchAll()

    fun selectAllByOffsetDateTimeNotNullAfterOrEq(offsetDateTime: OffsetDateTime) =
            (sqlClient selectFrom SQLITE_OFFSET_DATE_TIME
                    where SQLITE_OFFSET_DATE_TIME.offsetDateTimeNotNull afterOrEq offsetDateTime
                    ).fetchAll()

    fun selectAllByOffsetDateTimeNullable(offsetDateTime: OffsetDateTime?) =
            (sqlClient selectFrom SQLITE_OFFSET_DATE_TIME
                    where SQLITE_OFFSET_DATE_TIME.offsetDateTimeNullable eq offsetDateTime
                    ).fetchAll()

    fun selectAllByOffsetDateTimeNullableNotEq(offsetDateTime: OffsetDateTime?) =
            (sqlClient selectFrom SQLITE_OFFSET_DATE_TIME
                    where SQLITE_OFFSET_DATE_TIME.offsetDateTimeNullable notEq offsetDateTime
                    ).fetchAll()

    fun selectAllByOffsetDateTimeNullableBefore(offsetDateTime: OffsetDateTime) =
            (sqlClient selectFrom SQLITE_OFFSET_DATE_TIME
                    where SQLITE_OFFSET_DATE_TIME.offsetDateTimeNullable before offsetDateTime
                    ).fetchAll()

    fun selectAllByOffsetDateTimeNullableBeforeOrEq(offsetDateTime: OffsetDateTime) =
            (sqlClient selectFrom SQLITE_OFFSET_DATE_TIME
                    where SQLITE_OFFSET_DATE_TIME.offsetDateTimeNullable beforeOrEq offsetDateTime
                    ).fetchAll()

    fun selectAllByOffsetDateTimeNullableAfter(offsetDateTime: OffsetDateTime) =
            (sqlClient selectFrom SQLITE_OFFSET_DATE_TIME
                    where SQLITE_OFFSET_DATE_TIME.offsetDateTimeNullable after offsetDateTime
                    ).fetchAll()

    fun selectAllByOffsetDateTimeNullableAfterOrEq(offsetDateTime: OffsetDateTime) =
            (sqlClient selectFrom SQLITE_OFFSET_DATE_TIME
                    where SQLITE_OFFSET_DATE_TIME.offsetDateTimeNullable afterOrEq offsetDateTime
                    ).fetchAll()
}
