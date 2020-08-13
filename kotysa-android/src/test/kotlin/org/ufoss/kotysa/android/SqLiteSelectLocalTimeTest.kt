/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.ufoss.kotysa.Tables
import org.ufoss.kotysa.test.SqLiteLocalTime
import org.ufoss.kotysa.test.sqLiteLocalTimeWithNullable
import org.ufoss.kotysa.test.sqLiteLocalTimeWithoutNullable
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.ufoss.kotysa.test.Repository
import java.time.LocalTime

class SqLiteSelectLocalTimeTest : AbstractSqLiteTest<LocalTimeRepositorySelect>() {

    override fun getRepository(dbHelper: DbHelper, sqLiteTables: Tables) =
            LocalTimeRepositorySelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectAllByLocalDateNotNull finds SqLiteLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNull(LocalTime.of(12, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullNotEq finds SqLiteLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullNotEq(LocalTime.of(12, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds SqLiteLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullBefore(LocalTime.of(12, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullBefore(LocalTime.of(12, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds SqLiteLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullBeforeOrEq(LocalTime.of(12, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds SqLiteLocalDateWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullBeforeOrEq(LocalTime.of(12, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds SqLiteLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullAfter(LocalTime.of(12, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullAfter(LocalTime.of(12, 6)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds SqLiteLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullAfterOrEq(LocalTime.of(12, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds SqLiteLocalDateWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullAfterOrEq(LocalTime.of(12, 6)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds h2UuidWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullable(LocalTime.of(11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullable(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullableNotEq(LocalTime.of(11, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLocalDateNullableNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds SqLiteLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullableBefore(LocalTime.of(11, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNullableBefore(LocalTime.of(11, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds SqLiteLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullableBeforeOrEq(LocalTime.of(11, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds SqLiteLocalDateWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNullableBeforeOrEq(LocalTime.of(11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds SqLiteLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullableAfter(LocalTime.of(11, 3)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNullableAfter(LocalTime.of(11, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds SqLiteLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullableAfterOrEq(LocalTime.of(11, 3)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds SqLiteLocalDateWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNullableAfterOrEq(LocalTime.of(11, 4)))
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

    private fun createTables() =
            sqlClient.createTable<SqLiteLocalTime>()

    private fun insertLocalTimes() = sqlClient.insert(sqLiteLocalTimeWithNullable, sqLiteLocalTimeWithoutNullable)

    private fun deleteAll() = sqlClient.deleteAllFromTable<SqLiteLocalTime>()

    fun selectAllByLocalDateNotNull(localTime: LocalTime) = sqlClient.select<SqLiteLocalTime>()
            .where { it[SqLiteLocalTime::localTimeNotNull] eq localTime }
            .fetchAll()

    fun selectAllByLocalDateNotNullNotEq(localTime: LocalTime) = sqlClient.select<SqLiteLocalTime>()
            .where { it[SqLiteLocalTime::localTimeNotNull] notEq localTime }
            .fetchAll()

    fun selectAllByLocalDateNotNullBefore(localTime: LocalTime) = sqlClient.select<SqLiteLocalTime>()
            .where { it[SqLiteLocalTime::localTimeNotNull] before localTime }
            .fetchAll()

    fun selectAllByLocalDateNotNullBeforeOrEq(localTime: LocalTime) = sqlClient.select<SqLiteLocalTime>()
            .where { it[SqLiteLocalTime::localTimeNotNull] beforeOrEq localTime }
            .fetchAll()

    fun selectAllByLocalDateNotNullAfter(localTime: LocalTime) = sqlClient.select<SqLiteLocalTime>()
            .where { it[SqLiteLocalTime::localTimeNotNull] after localTime }
            .fetchAll()

    fun selectAllByLocalDateNotNullAfterOrEq(localTime: LocalTime) = sqlClient.select<SqLiteLocalTime>()
            .where { it[SqLiteLocalTime::localTimeNotNull] afterOrEq localTime }
            .fetchAll()

    fun selectAllByLocalDateNullable(localTime: LocalTime?) = sqlClient.select<SqLiteLocalTime>()
            .where { it[SqLiteLocalTime::localTimeNullable] eq localTime }
            .fetchAll()

    fun selectAllByLocalDateNullableNotEq(localTime: LocalTime?) = sqlClient.select<SqLiteLocalTime>()
            .where { it[SqLiteLocalTime::localTimeNullable] notEq localTime }
            .fetchAll()

    fun selectAllByLocalDateNullableBefore(localTime: LocalTime) = sqlClient.select<SqLiteLocalTime>()
            .where { it[SqLiteLocalTime::localTimeNullable] before localTime }
            .fetchAll()

    fun selectAllByLocalDateNullableBeforeOrEq(localTime: LocalTime) = sqlClient.select<SqLiteLocalTime>()
            .where { it[SqLiteLocalTime::localTimeNullable] beforeOrEq localTime }
            .fetchAll()

    fun selectAllByLocalDateNullableAfter(localTime: LocalTime) = sqlClient.select<SqLiteLocalTime>()
            .where { it[SqLiteLocalTime::localTimeNullable] after localTime }
            .fetchAll()

    fun selectAllByLocalDateNullableAfterOrEq(localTime: LocalTime) = sqlClient.select<SqLiteLocalTime>()
            .where { it[SqLiteLocalTime::localTimeNullable] afterOrEq localTime }
            .fetchAll()
}
