/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import kotlinx.datetime.LocalDateTime
import org.ufoss.kotysa.Tables
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.ufoss.kotysa.test.*

class SqLiteSelectKotlinxLocalDateTimeTest : AbstractSqLiteTest<KotlinxLocalDateTimeRepositorySelect>() {

    override fun getRepository(dbHelper: DbHelper, sqLiteTables: Tables) =
            KotlinxLocalDateTimeRepositorySelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectAllByLocalDateTimeNotNull finds sqLiteKotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNull(LocalDateTime(2019, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteKotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullNotEq finds sqLiteKotlinxLocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullNotEq(LocalDateTime(2019, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteKotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBefore finds sqLiteKotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBefore(LocalDateTime(2019, 11, 4, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteKotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBefore(LocalDateTime(2019, 11, 4, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds sqLiteKotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBeforeOrEq(LocalDateTime(2019, 11, 4, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteKotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds sqLiteKotlinxLocalDateTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBeforeOrEq(LocalDateTime(2019, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteKotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfter finds sqLiteKotlinxLocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfter(LocalDateTime(2019, 11, 5, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteKotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfter(LocalDateTime(2019, 11, 6, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds sqLiteKotlinxLocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfterOrEq(LocalDateTime(2019, 11, 5, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteKotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds sqLiteKotlinxLocalDateTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfterOrEq(LocalDateTime(2019, 11, 6, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteKotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullable finds h2UuidWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullable(LocalDateTime(2018, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteKotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullable finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullable(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteKotlinxLocalDateTimeWithoutNullable)
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
                .containsExactlyInAnyOrder(sqLiteKotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBefore finds sqLiteKotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBefore(LocalDateTime(2018, 11, 4, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteKotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBefore(LocalDateTime(2018, 11, 4, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds sqLiteKotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBeforeOrEq(LocalDateTime(2018, 11, 5, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteKotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds sqLiteKotlinxLocalDateTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBeforeOrEq(LocalDateTime(2018, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteKotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfter finds sqLiteKotlinxLocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfter(LocalDateTime(2018, 11, 3, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteKotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfter(LocalDateTime(2018, 11, 4, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfterOrEq finds sqLiteKotlinxLocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfterOrEq(LocalDateTime(2018, 11, 3, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteKotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfterOrEq finds sqLiteKotlinxLocalDateTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfterOrEq(LocalDateTime(2018, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteKotlinxLocalDateTimeWithNullable)
    }
}

class KotlinxLocalDateTimeRepositorySelect(sqLiteOpenHelper: SQLiteOpenHelper, tables: Tables) : Repository {

    private val sqlClient = sqLiteOpenHelper.sqlClient(tables)

    override fun init() {
        createTables()
        insertLocalDateTimes()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() =
            sqlClient.createTable<SqLiteKotlinxLocalDateTime>()

    private fun insertLocalDateTimes() = sqlClient.insert(sqLiteKotlinxLocalDateTimeWithNullable, sqLiteKotlinxLocalDateTimeWithoutNullable)

    private fun deleteAll() = sqlClient.deleteAllFromTable<SqLiteKotlinxLocalDateTime>()

    fun selectAllByLocalDateTimeNotNull(localDateTime: LocalDateTime) = sqlClient.select<SqLiteKotlinxLocalDateTime>()
            .where { it[SqLiteKotlinxLocalDateTime::localDateTimeNotNull] eq localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNotNullNotEq(localDateTime: LocalDateTime) = sqlClient.select<SqLiteKotlinxLocalDateTime>()
            .where { it[SqLiteKotlinxLocalDateTime::localDateTimeNotNull] notEq localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNotNullBefore(localDateTime: LocalDateTime) = sqlClient.select<SqLiteKotlinxLocalDateTime>()
            .where { it[SqLiteKotlinxLocalDateTime::localDateTimeNotNull] before localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNotNullBeforeOrEq(localDateTime: LocalDateTime) = sqlClient.select<SqLiteKotlinxLocalDateTime>()
            .where { it[SqLiteKotlinxLocalDateTime::localDateTimeNotNull] beforeOrEq localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNotNullAfter(localDateTime: LocalDateTime) = sqlClient.select<SqLiteKotlinxLocalDateTime>()
            .where { it[SqLiteKotlinxLocalDateTime::localDateTimeNotNull] after localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNotNullAfterOrEq(localDateTime: LocalDateTime) = sqlClient.select<SqLiteKotlinxLocalDateTime>()
            .where { it[SqLiteKotlinxLocalDateTime::localDateTimeNotNull] afterOrEq localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNullable(localDateTime: LocalDateTime?) = sqlClient.select<SqLiteKotlinxLocalDateTime>()
            .where { it[SqLiteKotlinxLocalDateTime::localDateTimeNullable] eq localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNullableNotEq(localDateTime: LocalDateTime?) = sqlClient.select<SqLiteKotlinxLocalDateTime>()
            .where { it[SqLiteKotlinxLocalDateTime::localDateTimeNullable] notEq localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNullableBefore(localDateTime: LocalDateTime) = sqlClient.select<SqLiteKotlinxLocalDateTime>()
            .where { it[SqLiteKotlinxLocalDateTime::localDateTimeNullable] before localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNullableBeforeOrEq(localDateTime: LocalDateTime) = sqlClient.select<SqLiteKotlinxLocalDateTime>()
            .where { it[SqLiteKotlinxLocalDateTime::localDateTimeNullable] beforeOrEq localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNullableAfter(localDateTime: LocalDateTime) = sqlClient.select<SqLiteKotlinxLocalDateTime>()
            .where { it[SqLiteKotlinxLocalDateTime::localDateTimeNullable] after localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNullableAfterOrEq(localDateTime: LocalDateTime) = sqlClient.select<SqLiteKotlinxLocalDateTime>()
            .where { it[SqLiteKotlinxLocalDateTime::localDateTimeNullable] afterOrEq localDateTime }
            .fetchAll()
}
