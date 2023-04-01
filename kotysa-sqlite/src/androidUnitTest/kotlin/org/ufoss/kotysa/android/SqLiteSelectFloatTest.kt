/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.junit.Test
import org.ufoss.kotysa.SqLiteTables
import org.ufoss.kotysa.android.transaction.AndroidTransaction
import org.ufoss.kotysa.test.SqliteFloats
import org.ufoss.kotysa.test.repositories.blocking.SelectFloatRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectFloatTest

class SqLiteSelectFloatTest : AbstractSqLiteTest<SelectFloatRepositorySqliteSelect>(),
    SelectFloatTest<SqliteFloats, SelectFloatRepositorySqliteSelect, AndroidTransaction> {
    override fun getRepository(sqLiteTables: SqLiteTables) = SelectFloatRepositorySqliteSelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectAllByFloatNotNull finds floatWithNullable - Android`() {
        `Verify selectAllByFloatNotNull finds floatWithNullable`()
    }

    @Test
    fun `Verify selectAllByFloatNotNullNotEq finds floatWithoutNullable - Android`() {
        `Verify selectAllByFloatNotNullNotEq finds floatWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByFloatNotNullIn finds both - Android`() {
        `Verify selectAllByFloatNotNullIn finds both`()
    }

    @Test
    fun `Verify selectAllByFloatNotNullInf finds floatWithNullable - Android`() {
        `Verify selectAllByFloatNotNullInf finds floatWithNullable`()
    }

    @Test
    fun `Verify selectAllByFloatNotNullInf finds no results when equals - Android`() {
        `Verify selectAllByFloatNotNullInf finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByFloatNotNullInfOrEq finds floatWithNullable - Android`() {
        `Verify selectAllByFloatNotNullInfOrEq finds floatWithNullable`()
    }

    @Test
    fun `Verify selectAllByFloatNotNullInfOrEq finds floatWithNullable when equals - Android`() {
        `Verify selectAllByFloatNotNullInfOrEq finds floatWithNullable when equals`()
    }

    @Test
    fun `Verify selectAllByFloatNotNullSup finds floatWithoutNullable - Android`() {
        `Verify selectAllByFloatNotNullSup finds floatWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByFloatNotNullSup finds no results when equals - Android`() {
        `Verify selectAllByFloatNotNullSup finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByFloatNotNullSupOrEq finds floatWithoutNullable - Android`() {
        `Verify selectAllByFloatNotNullSupOrEq finds floatWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByFloatNotNullSupOrEq finds floatWithoutNullable when equals - Android`() {
        `Verify selectAllByFloatNotNullSupOrEq finds floatWithoutNullable when equals`()
    }

    @Test
    fun `Verify selectAllByFloatNullable finds floatWithNullable - Android`() {
        `Verify selectAllByFloatNullable finds floatWithNullable`()
    }

    @Test
    fun `Verify selectAllByFloatNullable finds floatWithoutNullable - Android`() {
        `Verify selectAllByFloatNullable finds floatWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByFloatNullableNotEq finds nothing - Android`() {
        `Verify selectAllByFloatNullableNotEq finds nothing`()
    }

    @Test
    fun `Verify selectAllByFloatNullableNotEq finds no results - Android`() {
        `Verify selectAllByFloatNullableNotEq finds no results`()
    }

    @Test
    fun `Verify selectAllByFloatNullableInf finds floatWithNullable - Android`() {
        `Verify selectAllByFloatNullableInf finds floatWithNullable`()
    }

    @Test
    fun `Verify selectAllByFloatNullableInf finds no results when equals - Android`() {
        `Verify selectAllByFloatNullableInf finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByFloatNullableInfOrEq finds floatWithNullable - Android`() {
        `Verify selectAllByFloatNullableInfOrEq finds floatWithNullable`()
    }

    @Test
    fun `Verify selectAllByFloatNullableInfOrEq finds floatWithNullable when equals - Android`() {
        `Verify selectAllByFloatNullableInfOrEq finds floatWithNullable when equals`()
    }

    @Test
    fun `Verify selectAllByFloatNullableSup finds floatWithoutNullable - Android`() {
        `Verify selectAllByFloatNullableSup finds floatWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByFloatNullableSup finds no results when equals - Android`() {
        `Verify selectAllByFloatNullableSup finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByFloatNullableSupOrEq finds floatWithoutNullable - Android`() {
        `Verify selectAllByFloatNullableSupOrEq finds floatWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByFloatNullableSupOrEq finds floatWithoutNullable when equals - Android`() {
        `Verify selectAllByFloatNullableSupOrEq finds floatWithoutNullable when equals`()
    }
}

class SelectFloatRepositorySqliteSelect(
    sqLiteOpenHelper: SQLiteOpenHelper,
    tables: SqLiteTables,
) : SelectFloatRepository<SqliteFloats>(sqLiteOpenHelper.sqlClient(tables), SqliteFloats)
