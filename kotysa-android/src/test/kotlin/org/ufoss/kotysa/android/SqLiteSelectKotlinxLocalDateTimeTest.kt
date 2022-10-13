/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.junit.Test
import org.ufoss.kotysa.SqLiteTables
import org.ufoss.kotysa.android.transaction.AndroidTransaction
import org.ufoss.kotysa.test.SqliteKotlinxLocalDateTimes
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTimeTest

class SqLiteSelectKotlinxLocalDateTimeTest : AbstractSqLiteTest<KotlinxLocalDateTimeRepositorySelect>(),
    SelectKotlinxLocalDateTimeTest<SqliteKotlinxLocalDateTimes, KotlinxLocalDateTimeRepositorySelect,
            AndroidTransaction> {

    override fun getRepository(sqLiteTables: SqLiteTables) =
        KotlinxLocalDateTimeRepositorySelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectAllByLocalDateTimeNotNull finds kotlinxLocalDateTimeWithNullable - Android`() {
        `Verify selectAllByLocalDateTimeNotNull finds kotlinxLocalDateTimeWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullNotEq finds kotlinxLocalDateTimeWithoutNullable - Android`() {
        `Verify selectAllByLocalDateTimeNotNullNotEq finds kotlinxLocalDateTimeWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullIn finds both - Android`() {
        `Verify selectAllByLocalDateTimeNotNullIn finds both`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBefore finds kotlinxLocalDateTimeWithNullable - Android`() {
        `Verify selectAllByLocalDateTimeNotNullBefore finds kotlinxLocalDateTimeWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBefore finds no results when equals - Android`() {
        `Verify selectAllByLocalDateTimeNotNullBefore finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds kotlinxLocalDateTimeWithNullable - Android`() {
        `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds kotlinxLocalDateTimeWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds kotlinxLocalDateTimeWithNullable when equals - Android`() {
        `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds kotlinxLocalDateTimeWithNullable when equals`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfter finds kotlinxLocalDateTimeWithoutNullable - Android`() {
        `Verify selectAllByLocalDateTimeNotNullAfter finds kotlinxLocalDateTimeWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfter finds no results when equals - Android`() {
        `Verify selectAllByLocalDateTimeNotNullAfter finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds kotlinxLocalDateTimeWithoutNullable - Android`() {
        `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds kotlinxLocalDateTimeWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds kotlinxLocalDateTimeWithoutNullable when equals - Android`() {
        `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds kotlinxLocalDateTimeWithoutNullable when equals`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullable finds kotlinxLocalDateTimeWithNullable - Android`() {
        `Verify selectAllByLocalDateTimeNullable finds kotlinxLocalDateTimeWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullable finds kotlinxLocalDateTimeWithoutNullable - Android`() {
        `Verify selectAllByLocalDateTimeNullable finds kotlinxLocalDateTimeWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableNotEq finds no result - Android`() {
        `Verify selectAllByLocalDateTimeNullableNotEq finds no result`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableNotEq finds kotlinxLocalDateTimeWithNullable - Android`() {
        `Verify selectAllByLocalDateTimeNullableNotEq finds kotlinxLocalDateTimeWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBefore finds kotlinxLocalDateTimeWithNullable - Android`() {
        `Verify selectAllByLocalDateTimeNullableBefore finds kotlinxLocalDateTimeWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBefore finds no results when equals - Android`() {
        `Verify selectAllByLocalDateTimeNullableBefore finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds kotlinxLocalDateTimeWithNullable - Android`() {
        `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds kotlinxLocalDateTimeWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds kotlinxLocalDateTimeWithNullable when equals - Android`() {
        `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds kotlinxLocalDateTimeWithNullable when equals`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfter finds kotlinxLocalDateTimeWithoutNullable - Android`() {
        `Verify selectAllByLocalDateTimeNullableAfter finds kotlinxLocalDateTimeWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfter finds no results when equals - Android`() {
        `Verify selectAllByLocalDateTimeNullableAfter finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfterOrEq finds kotlinxLocalDateTimeWithoutNullable - Android`() {
        `Verify selectAllByLocalDateTimeNullableAfterOrEq finds kotlinxLocalDateTimeWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfterOrEq finds kotlinxLocalDateTimeWithoutNullable when equals - Android`() {
        `Verify selectAllByLocalDateTimeNullableAfterOrEq finds kotlinxLocalDateTimeWithoutNullable when equals`()
    }
}

class KotlinxLocalDateTimeRepositorySelect(sqLiteOpenHelper: SQLiteOpenHelper, tables: SqLiteTables) :
    SelectKotlinxLocalDateTimeRepository<SqliteKotlinxLocalDateTimes>(
        sqLiteOpenHelper.sqlClient(tables),
        SqliteKotlinxLocalDateTimes
    )
