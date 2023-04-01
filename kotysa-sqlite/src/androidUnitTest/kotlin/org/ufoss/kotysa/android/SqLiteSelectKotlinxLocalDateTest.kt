/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.junit.Test
import org.ufoss.kotysa.SqLiteTables
import org.ufoss.kotysa.android.transaction.AndroidTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTest

class SqLiteSelectKotlinxLocalDateTest : AbstractSqLiteTest<KotlinxLocalDateRepositorySelect>(),
    SelectKotlinxLocalDateTest<SqliteKotlinxLocalDates, KotlinxLocalDateRepositorySelect, AndroidTransaction> {

    override fun getRepository(sqLiteTables: SqLiteTables) = KotlinxLocalDateRepositorySelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectAllByLocalDateNotNull finds kotlinxLocalDateWithNullable - Android`() {
        `Verify selectAllByLocalDateNotNull finds kotlinxLocalDateWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullNotEq finds kotlinxLocalDateWithoutNullable - Android`() {
        `Verify selectAllByLocalDateNotNullNotEq finds kotlinxLocalDateWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullIn finds both - Android`() {
        `Verify selectAllByLocalDateNotNullIn finds both`()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds kotlinxLocalDateWithNullable - Android`() {
        `Verify selectAllByLocalDateNotNullBefore finds kotlinxLocalDateWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds no results when equals - Android`() {
        `Verify selectAllByLocalDateNotNullBefore finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds kotlinxLocalDateWithNullable - Android`() {
        `Verify selectAllByLocalDateNotNullBeforeOrEq finds kotlinxLocalDateWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds kotlinxLocalDateWithNullable when equals - Android`() {
        `Verify selectAllByLocalDateNotNullBeforeOrEq finds kotlinxLocalDateWithNullable when equals`()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds kotlinxLocalDateWithoutNullable - Android`() {
        `Verify selectAllByLocalDateNotNullAfter finds kotlinxLocalDateWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds no results when equals - Android`() {
        `Verify selectAllByLocalDateNotNullAfter finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds kotlinxLocalDateWithoutNullable - Android`() {
        `Verify selectAllByLocalDateNotNullAfterOrEq finds kotlinxLocalDateWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds kotlinxLocalDateWithoutNullable when equals - Android`() {
        `Verify selectAllByLocalDateNotNullAfterOrEq finds kotlinxLocalDateWithoutNullable when equals`()
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds h2UuidWithNullable - Android`() {
        `Verify selectAllByLocalDateNullable finds h2UuidWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds h2UuidWithoutNullable - Android`() {
        `Verify selectAllByLocalDateNullable finds h2UuidWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds h2UuidWithoutNullable - Android`() {
        `Verify selectAllByLocalDateNullableNotEq finds h2UuidWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds no results - Android`() {
        `Verify selectAllByLocalDateNullableNotEq finds no results`()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds kotlinxLocalDateWithNullable - Android`() {
        `Verify selectAllByLocalDateNullableBefore finds kotlinxLocalDateWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds no results when equals - Android`() {
        `Verify selectAllByLocalDateNullableBefore finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds kotlinxLocalDateWithNullable - Android`() {
        `Verify selectAllByLocalDateNullableBeforeOrEq finds kotlinxLocalDateWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds kotlinxLocalDateWithNullable when equals - Android`() {
        `Verify selectAllByLocalDateNullableBeforeOrEq finds kotlinxLocalDateWithNullable when equals`()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds kotlinxLocalDateWithoutNullable - Android`() {
        `Verify selectAllByLocalDateNullableAfter finds kotlinxLocalDateWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds no results when equals - Android`() {
        `Verify selectAllByLocalDateNullableAfter finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds kotlinxLocalDateWithoutNullable - Android`() {
        `Verify selectAllByLocalDateNullableAfterOrEq finds kotlinxLocalDateWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds kotlinxLocalDateWithoutNullable when equals - Android`() {
        `Verify selectAllByLocalDateNullableAfterOrEq finds kotlinxLocalDateWithoutNullable when equals`()
    }
}

class KotlinxLocalDateRepositorySelect(sqLiteOpenHelper: SQLiteOpenHelper, tables: SqLiteTables) :
    SelectKotlinxLocalDateRepository<SqliteKotlinxLocalDates>(sqLiteOpenHelper.sqlClient(tables),
        SqliteKotlinxLocalDates)
