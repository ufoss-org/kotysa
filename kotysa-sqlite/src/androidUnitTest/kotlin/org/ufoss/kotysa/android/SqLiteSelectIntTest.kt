/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.junit.Test
import org.ufoss.kotysa.SqLiteTables
import org.ufoss.kotysa.android.transaction.AndroidTransaction
import org.ufoss.kotysa.test.SqliteInts
import org.ufoss.kotysa.test.repositories.blocking.SelectIntRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectIntTest

class SqLiteSelectIntTest : AbstractSqLiteTest<SelectIntRepositorySelect>(),
    SelectIntTest<SqliteInts, SelectIntRepositorySelect, AndroidTransaction> {

    override fun getRepository(sqLiteTables: SqLiteTables) = SelectIntRepositorySelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectAllByIntNotNull finds intWithNullable - Android`() {
        `Verify selectAllByIntNotNull finds intWithNullable`()
    }

    @Test
    fun `Verify selectAllByIntNotNullNotEq finds intWithoutNullable - Android`() {
        `Verify selectAllByIntNotNullNotEq finds intWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByIntNotNullIn finds both - Android`() {
        `Verify selectAllByIntNotNullIn finds both`()
    }

    @Test
    fun `Verify selectAllByIntNotNullInf finds intWithNullable - Android`() {
        `Verify selectAllByIntNotNullInf finds intWithNullable`()
    }

    @Test
    fun `Verify selectAllByIntNotNullInf finds no results when equals - Android`() {
        `Verify selectAllByIntNotNullInf finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByIntNotNullInfOrEq finds intWithNullable - Android`() {
        `Verify selectAllByIntNotNullInfOrEq finds intWithNullable`()
    }

    @Test
    fun `Verify selectAllByIntNotNullInfOrEq finds intWithNullable when equals - Android`() {
        `Verify selectAllByIntNotNullInfOrEq finds intWithNullable when equals`()
    }

    @Test
    fun `Verify selectAllByIntNotNullSup finds intWithoutNullable - Android`() {
        `Verify selectAllByIntNotNullSup finds intWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByIntNotNullSup finds no results when equals - Android`() {
        `Verify selectAllByIntNotNullSup finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByIntNotNullSupOrEq finds intWithoutNullable - Android`() {
        `Verify selectAllByIntNotNullSupOrEq finds intWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByIntNotNullSupOrEq finds intWithoutNullable when equals - Android`() {
        `Verify selectAllByIntNotNullSupOrEq finds intWithoutNullable when equals`()
    }

    @Test
    fun `Verify selectAllByIntNullable finds intWithNullable - Android`() {
        `Verify selectAllByIntNullable finds intWithNullable`()
    }

    @Test
    fun `Verify selectAllByIntNullable finds intWithoutNullable - Android`() {
        `Verify selectAllByIntNullable finds intWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByIntNullableNotEq finds nothing - Android`() {
        `Verify selectAllByIntNullableNotEq finds nothing`()
    }

    @Test
    fun `Verify selectAllByIntNullableNotEq finds no results - Android`() {
        `Verify selectAllByIntNullableNotEq finds no results`()
    }

    @Test
    fun `Verify selectAllByIntNullableInf finds intWithNullable - Android`() {
        `Verify selectAllByIntNullableInf finds intWithNullable`()
    }

    @Test
    fun `Verify selectAllByIntNullableInf finds no results when equals - Android`() {
        `Verify selectAllByIntNullableInf finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByIntNullableInfOrEq finds intWithNullable - Android`() {
        `Verify selectAllByIntNullableInfOrEq finds intWithNullable`()
    }

    @Test
    fun `Verify selectAllByIntNullableInfOrEq finds intWithNullable when equals - Android`() {
        `Verify selectAllByIntNullableInfOrEq finds intWithNullable when equals`()
    }

    @Test
    fun `Verify selectAllByIntNullableSup finds intWithoutNullable - Android`() {
        `Verify selectAllByIntNullableSup finds intWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByIntNullableSup finds no results when equals - Android`() {
        `Verify selectAllByIntNullableSup finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByIntNullableSupOrEq finds intWithoutNullable - Android`() {
        `Verify selectAllByIntNullableSupOrEq finds intWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByIntNullableSupOrEq finds intWithoutNullable when equals - Android`() {
        `Verify selectAllByIntNullableSupOrEq finds intWithoutNullable when equals`()
    }
}

class SelectIntRepositorySelect(sqLiteOpenHelper: SQLiteOpenHelper, tables: SqLiteTables) :
    SelectIntRepository<SqliteInts>(sqLiteOpenHelper.sqlClient(tables), SqliteInts)
