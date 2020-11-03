/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.ufoss.kotysa.Tables
import org.ufoss.kotysa.test.*
import java.time.LocalTime

class SqLiteSelectLocalTimeTest : AbstractSqLiteTest<LocalTimeRepositorySelect>() {

    override fun getRepository(sqLiteTables: Tables) = LocalTimeRepositorySelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectAllByLocalTimeNotNull finds SqLiteLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNull(LocalTime.of(12, 4)))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullNotEq finds SqLiteLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullNotEq(LocalTime.of(12, 4)))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullIn finds both`() {
        val seq = sequenceOf(
                sqLiteLocalTimeWithNullable.localTimeNotNull,
                sqLiteLocalTimeWithoutNullable.localTimeNotNull)
        assertThat(repository.selectAllByLocalTimeNotNullIn(seq))
                .hasSize(2)
                .containsExactlyInAnyOrder(sqLiteLocalTimeWithNullable, sqLiteLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBefore finds SqLiteLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullBefore(LocalTime.of(12, 5)))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalTimeNotNullBefore(LocalTime.of(12, 4)))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBeforeOrEq finds SqLiteLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullBeforeOrEq(LocalTime.of(12, 5)))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBeforeOrEq finds SqLiteLocalDateWithNullable when equals`() {
        assertThat(repository.selectAllByLocalTimeNotNullBeforeOrEq(LocalTime.of(12, 4)))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfter finds SqLiteLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullAfter(LocalTime.of(12, 5)))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalTimeNotNullAfter(LocalTime.of(12, 6)))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfterOrEq finds SqLiteLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullAfterOrEq(LocalTime.of(12, 5)))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfterOrEq finds SqLiteLocalDateWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalTimeNotNullAfterOrEq(LocalTime.of(12, 6)))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullable finds h2UuidWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNullable(LocalTime.of(11, 4)))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullable finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNullable(null))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableNotEq finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableNotEq(LocalTime.of(11, 4)))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLocalTimeNullableNotEq(null))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBefore finds SqLiteLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableBefore(LocalTime.of(11, 5)))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalTimeNullableBefore(LocalTime.of(11, 4)))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBeforeOrEq finds SqLiteLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableBeforeOrEq(LocalTime.of(11, 5)))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBeforeOrEq finds SqLiteLocalDateWithNullable when equals`() {
        assertThat(repository.selectAllByLocalTimeNullableBeforeOrEq(LocalTime.of(11, 4)))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfter finds SqLiteLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableAfter(LocalTime.of(11, 3)))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalTimeNullableAfter(LocalTime.of(11, 4)))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfterOrEq finds SqLiteLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableAfterOrEq(LocalTime.of(11, 3)))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfterOrEq finds SqLiteLocalDateWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalTimeNullableAfterOrEq(LocalTime.of(11, 4)))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteLocalTimeWithNullable)
    }
}

class LocalTimeRepositorySelect(sqLiteOpenHelper: SQLiteOpenHelper, tables: Tables) : Repository {

    private val sqlClient = sqLiteOpenHelper.sqlClient(tables)

    override fun init() {
        createTables()
        insertLocalTimes()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() {
        sqlClient.createTable<SqLiteLocalTime>()
    }

    private fun insertLocalTimes() {
        sqlClient.insert(sqLiteLocalTimeWithNullable, sqLiteLocalTimeWithoutNullable)
    }

    private fun deleteAll() = sqlClient.deleteAllFromTable<SqLiteLocalTime>()

    fun selectAllByLocalTimeNotNull(localTime: LocalTime) =
        sqlClient.select<SqLiteLocalTime>()
            .where { it[SqLiteLocalTime::localTimeNotNull] eq localTime }
            .fetchAll()

    fun selectAllByLocalTimeNotNullNotEq(localTime: LocalTime) =
        sqlClient.select<SqLiteLocalTime>()
            .where { it[SqLiteLocalTime::localTimeNotNull] notEq localTime }
            .fetchAll()

    fun selectAllByLocalTimeNotNullIn(values: Sequence<LocalTime>) =
            sqlClient.select<SqLiteLocalTime>()
                    .where { it[SqLiteLocalTime::localTimeNotNull] `in` values }
                    .fetchAll()

    fun selectAllByLocalTimeNotNullBefore(localTime: LocalTime) =
        sqlClient.select<SqLiteLocalTime>()
            .where { it[SqLiteLocalTime::localTimeNotNull] before localTime }
            .fetchAll()

    fun selectAllByLocalTimeNotNullBeforeOrEq(localTime: LocalTime) =
        sqlClient.select<SqLiteLocalTime>()
            .where { it[SqLiteLocalTime::localTimeNotNull] beforeOrEq localTime }
            .fetchAll()

    fun selectAllByLocalTimeNotNullAfter(localTime: LocalTime) =
        sqlClient.select<SqLiteLocalTime>()
            .where { it[SqLiteLocalTime::localTimeNotNull] after localTime }
            .fetchAll()

    fun selectAllByLocalTimeNotNullAfterOrEq(localTime: LocalTime) =
        sqlClient.select<SqLiteLocalTime>()
            .where { it[SqLiteLocalTime::localTimeNotNull] afterOrEq localTime }
            .fetchAll()

    fun selectAllByLocalTimeNullable(localTime: LocalTime?) =
        sqlClient.select<SqLiteLocalTime>()
            .where { it[SqLiteLocalTime::localTimeNullable] eq localTime }
            .fetchAll()

    fun selectAllByLocalTimeNullableNotEq(localTime: LocalTime?) =
        sqlClient.select<SqLiteLocalTime>()
            .where { it[SqLiteLocalTime::localTimeNullable] notEq localTime }
            .fetchAll()

    fun selectAllByLocalTimeNullableBefore(localTime: LocalTime) =
        sqlClient.select<SqLiteLocalTime>()
            .where { it[SqLiteLocalTime::localTimeNullable] before localTime }
            .fetchAll()

    fun selectAllByLocalTimeNullableBeforeOrEq(localTime: LocalTime) =
        sqlClient.select<SqLiteLocalTime>()
            .where { it[SqLiteLocalTime::localTimeNullable] beforeOrEq localTime }
            .fetchAll()

    fun selectAllByLocalTimeNullableAfter(localTime: LocalTime) =
        sqlClient.select<SqLiteLocalTime>()
            .where { it[SqLiteLocalTime::localTimeNullable] after localTime }
            .fetchAll()

    fun selectAllByLocalTimeNullableAfterOrEq(localTime: LocalTime) =
        sqlClient.select<SqLiteLocalTime>()
            .where { it[SqLiteLocalTime::localTimeNullable] afterOrEq localTime }
            .fetchAll()
}
