/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.junit.Test
import org.ufoss.kotysa.SqLiteTables
import org.ufoss.kotysa.android.transaction.AndroidTransaction
import org.ufoss.kotysa.test.SqliteLocalDateTimes
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTimeTest

class SqLiteSelectLocalDateTimeTest : AbstractSqLiteTest<LocalDateTimeRepositorySelect>(),
    SelectLocalDateTimeTest<SqliteLocalDateTimes, LocalDateTimeRepositorySelect, AndroidTransaction> {

    override fun getRepository(sqLiteTables: SqLiteTables) = LocalDateTimeRepositorySelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectAllByLocalDateTimeNotNull finds localDateTimeWithNullable - Android`() {
        `Verify selectAllByLocalDateTimeNotNull finds localDateTimeWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullNotEq finds localDateTimeWithoutNullable - Android`() {
        `Verify selectAllByLocalDateTimeNotNullNotEq finds localDateTimeWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullIn finds both - Android`() {
        `Verify selectAllByLocalDateTimeNotNullIn finds both`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBefore finds localDateTimeWithNullable - Android`() {
        `Verify selectAllByLocalDateTimeNotNullBefore finds localDateTimeWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBefore finds no results when equals - Android`() {
        `Verify selectAllByLocalDateTimeNotNullBefore finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds localDateTimeWithNullable - Android`() {
        `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds localDateTimeWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds localDateTimeWithNullable when equals - Android`() {
        `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds localDateTimeWithNullable when equals`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfter finds localDateTimeWithoutNullable - Android`() {
        `Verify selectAllByLocalDateTimeNotNullAfter finds localDateTimeWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfter finds no results when equals - Android`() {
        `Verify selectAllByLocalDateTimeNotNullAfter finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds localDateTimeWithoutNullable - Android`() {
        `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds localDateTimeWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds localDateTimeWithoutNullable when equals - Android`() {
        `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds localDateTimeWithoutNullable when equals`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullable finds h2UuidWithNullable - Android`() {
        `Verify selectAllByLocalDateTimeNullable finds h2UuidWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullable finds h2UuidWithoutNullable - Android`() {
        `Verify selectAllByLocalDateTimeNullable finds h2UuidWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableNotEq finds h2UuidWithoutNullable - Android`() {
        `Verify selectAllByLocalDateTimeNullableNotEq finds h2UuidWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableNotEq finds no results - Android`() {
        `Verify selectAllByLocalDateTimeNullableNotEq finds no results`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBefore finds localDateTimeWithNullable - Android`() {
        `Verify selectAllByLocalDateTimeNullableBefore finds localDateTimeWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBefore finds no results when equals - Android`() {
        `Verify selectAllByLocalDateTimeNullableBefore finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds localDateTimeWithNullable - Android`() {
        `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds localDateTimeWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds localDateTimeWithNullable when equals - Android`() {
        `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds localDateTimeWithNullable when equals`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfter finds localDateTimeWithoutNullable - Android`() {
        `Verify selectAllByLocalDateTimeNullableAfter finds localDateTimeWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfter finds no results when equals - Android`() {
        `Verify selectAllByLocalDateTimeNullableAfter finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfterOrEq finds localDateTimeWithoutNullable - Android`() {
        `Verify selectAllByLocalDateTimeNullableAfterOrEq finds localDateTimeWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfterOrEq finds localDateTimeWithoutNullable when equals - Android`() {
        `Verify selectAllByLocalDateTimeNullableAfterOrEq finds localDateTimeWithoutNullable when equals`()
    }
}

class LocalDateTimeRepositorySelect(sqLiteOpenHelper: SQLiteOpenHelper, tables: SqLiteTables) :
    SelectLocalDateTimeRepository<SqliteLocalDateTimes>(sqLiteOpenHelper.sqlClient(tables), SqliteLocalDateTimes)
