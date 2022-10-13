/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.junit.Test
import org.ufoss.kotysa.SqLiteTables
import org.ufoss.kotysa.android.transaction.AndroidTransaction
import org.ufoss.kotysa.test.SqliteKotlinxLocalTimes
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalTimeTest

class SqLiteSelectKotlinxLocalTimeTest : AbstractSqLiteTest<KotlinxLocalTimeRepositorySelect>(),
    SelectKotlinxLocalTimeTest<SqliteKotlinxLocalTimes, KotlinxLocalTimeRepositorySelect, AndroidTransaction> {

    override fun getRepository(sqLiteTables: SqLiteTables) = KotlinxLocalTimeRepositorySelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectAllByLocalTimeNotNull finds kotlinxLocalTimeWithNullable - Android`() {
        `Verify selectAllByLocalTimeNotNull finds kotlinxLocalTimeWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullNotEq finds kotlinxLocalTimeWithoutNullable - Android`() {
        `Verify selectAllByLocalTimeNotNullNotEq finds kotlinxLocalTimeWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullIn finds both - Android`() {
        `Verify selectAllByLocalTimeNotNullIn finds both`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBefore finds kotlinxLocalTimeWithNullable - Android`() {
        `Verify selectAllByLocalTimeNotNullBefore finds kotlinxLocalTimeWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBefore finds no results when equals - Android`() {
        `Verify selectAllByLocalTimeNotNullBefore finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBeforeOrEq finds kotlinxLocalTimeWithNullable - Android`() {
        `Verify selectAllByLocalTimeNotNullBeforeOrEq finds kotlinxLocalTimeWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBeforeOrEq finds kotlinxLocalTimeWithNullable when equals - Android`() {
        `Verify selectAllByLocalTimeNotNullBeforeOrEq finds kotlinxLocalTimeWithNullable when equals`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfter finds kotlinxLocalTimeWithoutNullable - Android`() {
        `Verify selectAllByLocalTimeNotNullAfter finds kotlinxLocalTimeWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfter finds no results when equals - Android`() {
        `Verify selectAllByLocalTimeNotNullAfter finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfterOrEq finds kotlinxLocalTimeWithoutNullable - Android`() {
        `Verify selectAllByLocalTimeNotNullAfterOrEq finds kotlinxLocalTimeWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfterOrEq finds kotlinxLocalTimeWithoutNullable when equals - Android`() {
        `Verify selectAllByLocalTimeNotNullAfterOrEq finds kotlinxLocalTimeWithoutNullable when equals`()
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
    fun `Verify selectAllByLocalTimeNullableBefore finds kotlinxLocalTimeWithNullable - Android`() {
        `Verify selectAllByLocalTimeNullableBefore finds kotlinxLocalTimeWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBefore finds no results when equals - Android`() {
        `Verify selectAllByLocalTimeNullableBefore finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBeforeOrEq finds kotlinxLocalTimeWithNullable - Android`() {
        `Verify selectAllByLocalTimeNullableBeforeOrEq finds kotlinxLocalTimeWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBeforeOrEq finds kotlinxLocalTimeWithNullable when equals - Android`() {
        `Verify selectAllByLocalTimeNullableBeforeOrEq finds kotlinxLocalTimeWithNullable when equals`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfter finds kotlinxLocalTimeWithoutNullable - Android`() {
        `Verify selectAllByLocalTimeNullableAfter finds kotlinxLocalTimeWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfter finds no results when equals - Android`() {
        `Verify selectAllByLocalTimeNullableAfter finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfterOrEq finds kotlinxLocalTimeWithoutNullable - Android`() {
        `Verify selectAllByLocalTimeNullableAfterOrEq finds kotlinxLocalTimeWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfterOrEq finds kotlinxLocalTimeWithoutNullable when equals - Android`() {
        `Verify selectAllByLocalTimeNullableAfterOrEq finds kotlinxLocalTimeWithoutNullable when equals`()
    }
}

class KotlinxLocalTimeRepositorySelect(sqLiteOpenHelper: SQLiteOpenHelper, tables: SqLiteTables) :
    SelectKotlinxLocalTimeRepository<SqliteKotlinxLocalTimes>(
        sqLiteOpenHelper.sqlClient(tables),
        SqliteKotlinxLocalTimes
    )
