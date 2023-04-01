/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.junit.Test
import org.ufoss.kotysa.SqLiteTables
import org.ufoss.kotysa.android.transaction.AndroidTransaction
import org.ufoss.kotysa.test.SqliteLocalDates
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTest

class SqLiteSelectLocalDateTest : AbstractSqLiteTest<LocalDateRepositorySelect>(),
    SelectLocalDateTest<SqliteLocalDates, LocalDateRepositorySelect, AndroidTransaction> {

    override fun getRepository(sqLiteTables: SqLiteTables) = LocalDateRepositorySelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectAllByLocalDateNotNull finds localDateWithNullable - Android`() {
        `Verify selectAllByLocalDateNotNull finds localDateWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullNotEq finds localDateWithoutNullable - Android`() {
        `Verify selectAllByLocalDateNotNullNotEq finds localDateWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullIn finds both - Android`() {
        `Verify selectAllByLocalDateNotNullIn finds both`()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds localDateWithNullable - Android`() {
        `Verify selectAllByLocalDateNotNullBefore finds localDateWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds no results when equals - Android`() {
        `Verify selectAllByLocalDateNotNullBefore finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds localDateWithNullable - Android`() {
        `Verify selectAllByLocalDateNotNullBeforeOrEq finds localDateWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds localDateWithNullable when equals - Android`() {
        `Verify selectAllByLocalDateNotNullBeforeOrEq finds localDateWithNullable when equals`()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds localDateWithoutNullable - Android`() {
        `Verify selectAllByLocalDateNotNullAfter finds localDateWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds no results when equals - Android`() {
        `Verify selectAllByLocalDateNotNullAfter finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds localDateWithoutNullable - Android`() {
        `Verify selectAllByLocalDateNotNullAfterOrEq finds localDateWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds localDateWithoutNullable when equals - Android`() {
        `Verify selectAllByLocalDateNotNullAfterOrEq finds localDateWithoutNullable when equals`()
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
    fun `Verify selectAllByLocalDateNullableBefore finds localDateWithNullable - Android`() {
        `Verify selectAllByLocalDateNullableBefore finds localDateWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds no results when equals - Android`() {
        `Verify selectAllByLocalDateNullableBefore finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds localDateWithNullable - Android`() {
        `Verify selectAllByLocalDateNullableBeforeOrEq finds localDateWithNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds localDateWithNullable when equals - Android`() {
        `Verify selectAllByLocalDateNullableBeforeOrEq finds localDateWithNullable when equals`()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds localDateWithoutNullable - Android`() {
        `Verify selectAllByLocalDateNullableAfter finds localDateWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds no results when equals - Android`() {
        `Verify selectAllByLocalDateNullableAfter finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds localDateWithoutNullable - Android`() {
        `Verify selectAllByLocalDateNullableAfterOrEq finds localDateWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds localDateWithoutNullable when equals - Android`() {
        `Verify selectAllByLocalDateNullableAfterOrEq finds localDateWithoutNullable when equals`()
    }
}

class LocalDateRepositorySelect(sqLiteOpenHelper: SQLiteOpenHelper, tables: SqLiteTables) :
    SelectLocalDateRepository<SqliteLocalDates>(sqLiteOpenHelper.sqlClient(tables), SqliteLocalDates)
