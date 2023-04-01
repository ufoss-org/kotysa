/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.junit.Test
import org.ufoss.kotysa.SqLiteTables
import org.ufoss.kotysa.android.transaction.AndroidTransaction
import org.ufoss.kotysa.test.SqliteOffsetDateTimes
import org.ufoss.kotysa.test.repositories.blocking.SelectOffsetDateTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectOffsetDateTimeTest

class SqLiteSelectOffsetDateTimeTest : AbstractSqLiteTest<OffsetDateTimeRepositorySelect>(),
    SelectOffsetDateTimeTest<SqliteOffsetDateTimes, OffsetDateTimeRepositorySelect, AndroidTransaction> {

    override fun getRepository(sqLiteTables: SqLiteTables) = OffsetDateTimeRepositorySelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNull finds offsetDateTimeWithNullable - Android`() {
        `Verify selectAllByOffsetDateTimeNotNull finds offsetDateTimeWithNullable`()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullNotEq finds offsetDateTimeWithoutNullable - Android`() {
        `Verify selectAllByOffsetDateTimeNotNullNotEq finds offsetDateTimeWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullIn finds both - Android`() {
        `Verify selectAllByOffsetDateTimeNotNullIn finds both`()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullBefore finds offsetDateTimeWithNullable - Android`() {
        `Verify selectAllByOffsetDateTimeNotNullBefore finds offsetDateTimeWithNullable`()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullBefore finds no results when equals - Android`() {
        `Verify selectAllByOffsetDateTimeNotNullBefore finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullBeforeOrEq finds offsetDateTimeWithNullable - Android`() {
        `Verify selectAllByOffsetDateTimeNotNullBeforeOrEq finds offsetDateTimeWithNullable`()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullBeforeOrEq finds offsetDateTimeWithNullable when equals - Android`() {
        `Verify selectAllByOffsetDateTimeNotNullBeforeOrEq finds offsetDateTimeWithNullable when equals`()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullAfter finds offsetDateTimeWithoutNullable - Android`() {
        `Verify selectAllByOffsetDateTimeNotNullAfter finds offsetDateTimeWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullAfter finds no results when equals - Android`() {
        `Verify selectAllByOffsetDateTimeNotNullAfter finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullAfterOrEq finds offsetDateTimeWithoutNullable - Android`() {
        `Verify selectAllByOffsetDateTimeNotNullAfterOrEq finds offsetDateTimeWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullAfterOrEq finds offsetDateTimeWithoutNullable when equals - Android`() {
        `Verify selectAllByOffsetDateTimeNotNullAfterOrEq finds offsetDateTimeWithoutNullable when equals`()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullable finds offsetDateTimeWithNullable - Android`() {
        `Verify selectAllByOffsetDateTimeNullable finds offsetDateTimeWithNullable`()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullable finds offsetDateTimeWithoutNullable - Android`() {
        `Verify selectAllByOffsetDateTimeNullable finds offsetDateTimeWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableNotEq finds no result - Android`() {
        `Verify selectAllByOffsetDateTimeNullableNotEq finds no result`()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableNotEq finds offsetDateTimeWithNullable - Android`() {
        `Verify selectAllByOffsetDateTimeNullableNotEq finds offsetDateTimeWithNullable`()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableBefore finds offsetDateTimeWithNullable - Android`() {
        `Verify selectAllByOffsetDateTimeNullableBefore finds offsetDateTimeWithNullable`()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableBefore finds no results when equals - Android`() {
        `Verify selectAllByOffsetDateTimeNullableBefore finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableBeforeOrEq finds offsetDateTimeWithNullable - Android`() {
        `Verify selectAllByOffsetDateTimeNullableBeforeOrEq finds offsetDateTimeWithNullable`()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableBeforeOrEq finds offsetDateTimeWithNullable when equals - Android`() {
        `Verify selectAllByOffsetDateTimeNullableBeforeOrEq finds offsetDateTimeWithNullable when equals`()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableAfter finds offsetDateTimeWithNullable - Android`() {
        `Verify selectAllByOffsetDateTimeNullableAfter finds offsetDateTimeWithNullable`()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableAfter finds no results when equals - Android`() {
        `Verify selectAllByOffsetDateTimeNullableAfter finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableAfterOrEq finds offsetDateTimeWithNullable - Android`() {
        `Verify selectAllByOffsetDateTimeNullableAfterOrEq finds offsetDateTimeWithNullable`()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableAfterOrEq finds offsetDateTimeWithNullable when equals - Android`() {
        `Verify selectAllByOffsetDateTimeNullableAfterOrEq finds offsetDateTimeWithNullable when equals`()
    }
}

class OffsetDateTimeRepositorySelect(sqLiteOpenHelper: SQLiteOpenHelper, tables: SqLiteTables) :
    SelectOffsetDateTimeRepository<SqliteOffsetDateTimes>(sqLiteOpenHelper.sqlClient(tables), SqliteOffsetDateTimes)
