/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.junit.Test
import org.ufoss.kotysa.SqLiteTables
import org.ufoss.kotysa.android.transaction.AndroidTransaction
import org.ufoss.kotysa.test.SqliteLocalTimes
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalTimeTest

class SqLiteSelectLocalTimeTest : AbstractSqLiteTest<LocalTimeRepositorySelect>(),
    SelectLocalTimeTest<SqliteLocalTimes, LocalTimeRepositorySelect, AndroidTransaction> {

    override fun getRepository(sqLiteTables: SqLiteTables) = LocalTimeRepositorySelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectAllByLocalTimeNotNull finds localTimeWithNullable - Android`() {
        `Verify selectAllByLocalTimeNotNull finds localTimeWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullNotEq finds localTimeWithoutNullable - Android`() {
        `Verify selectAllByLocalTimeNotNullNotEq finds localTimeWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullIn finds both - Android`() {
        `Verify selectAllByLocalTimeNotNullIn finds both`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBefore finds localTimeWithNullable - Android`() {
        `Verify selectAllByLocalTimeNotNullBefore finds localTimeWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBefore finds no results when equals - Android`() {
        `Verify selectAllByLocalTimeNotNullBefore finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBeforeOrEq finds localTimeWithNullable - Android`() {
        `Verify selectAllByLocalTimeNotNullBeforeOrEq finds localTimeWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBeforeOrEq finds localTimeWithNullable when equals - Android`() {
        `Verify selectAllByLocalTimeNotNullBeforeOrEq finds localTimeWithNullable when equals`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfter finds localTimeWithoutNullable - Android`() {
        `Verify selectAllByLocalTimeNotNullAfter finds localTimeWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfter finds no results when equals - Android`() {
        `Verify selectAllByLocalTimeNotNullAfter finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfterOrEq finds localTimeWithoutNullable - Android`() {
        `Verify selectAllByLocalTimeNotNullAfterOrEq finds localTimeWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfterOrEq finds localTimeWithoutNullable when equals - Android`() {
        `Verify selectAllByLocalTimeNotNullAfterOrEq finds localTimeWithoutNullable when equals`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullable finds h2UuidWithNullable - Android`() {
        `Verify selectAllByLocalTimeNullable finds h2UuidWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullable finds h2UuidWithoutNullable - Android`() {
        `Verify selectAllByLocalTimeNullable finds h2UuidWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableNotEq finds h2UuidWithoutNullable - Android`() {
        `Verify selectAllByLocalTimeNullableNotEq finds h2UuidWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableNotEq finds no results - Android`() {
        `Verify selectAllByLocalTimeNullableNotEq finds no results`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBefore finds localTimeWithNullable - Android`() {
        `Verify selectAllByLocalTimeNullableBefore finds localTimeWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBefore finds no results when equals - Android`() {
        `Verify selectAllByLocalTimeNullableBefore finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBeforeOrEq finds localTimeWithNullable - Android`() {
        `Verify selectAllByLocalTimeNullableBeforeOrEq finds localTimeWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBeforeOrEq finds localTimeWithNullable when equals - Android`() {
        `Verify selectAllByLocalTimeNullableBeforeOrEq finds localTimeWithNullable when equals`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfter finds localTimeWithoutNullable - Android`() {
        `Verify selectAllByLocalTimeNullableAfter finds localTimeWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfter finds no results when equals - Android`() {
        `Verify selectAllByLocalTimeNullableAfter finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfterOrEq finds localTimeWithoutNullable - Android`() {
        `Verify selectAllByLocalTimeNullableAfterOrEq finds localTimeWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfterOrEq finds localTimeWithoutNullable when equals - Android`() {
        `Verify selectAllByLocalTimeNullableAfterOrEq finds localTimeWithoutNullable when equals`()
    }
}

class LocalTimeRepositorySelect(sqLiteOpenHelper: SQLiteOpenHelper, tables: SqLiteTables) :
    SelectLocalTimeRepository<SqliteLocalTimes>(sqLiteOpenHelper.sqlClient(tables), SqliteLocalTimes)
