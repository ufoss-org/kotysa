/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.junit.Test
import org.ufoss.kotysa.SqLiteTables
import org.ufoss.kotysa.android.transaction.AndroidTransaction
import org.ufoss.kotysa.test.SqliteDoubles
import org.ufoss.kotysa.test.repositories.blocking.SelectDoubleRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectDoubleTest

class SqLiteSelectDoubleTest : AbstractSqLiteTest<SelectDoubleRepositorySqliteSelect>(),
    SelectDoubleTest<SqliteDoubles, SelectDoubleRepositorySqliteSelect, AndroidTransaction> {
    override fun getRepository(sqLiteTables: SqLiteTables) = SelectDoubleRepositorySqliteSelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectAllByDoubleNotNull finds doubleWithNullable - Android`() {
        `Verify selectAllByDoubleNotNull finds doubleWithNullable`()
    }

    @Test
    fun `Verify selectAllByDoubleNotNullNotEq finds doubleWithoutNullable - Android`() {
        `Verify selectAllByDoubleNotNullNotEq finds doubleWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByDoubleNotNullIn finds both - Android`() {
        `Verify selectAllByDoubleNotNullIn finds both`()
    }

    @Test
    fun `Verify selectAllByDoubleNotNullInf finds doubleWithNullable - Android`() {
        `Verify selectAllByDoubleNotNullInf finds doubleWithNullable`()
    }

    @Test
    fun `Verify selectAllByDoubleNotNullInf finds no results when equals - Android`() {
        `Verify selectAllByDoubleNotNullInf finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByDoubleNotNullInfOrEq finds doubleWithNullable - Android`() {
        `Verify selectAllByDoubleNotNullInfOrEq finds doubleWithNullable`()
    }

    @Test
    fun `Verify selectAllByDoubleNotNullInfOrEq finds doubleWithNullable when equals - Android`() {
        `Verify selectAllByDoubleNotNullInfOrEq finds doubleWithNullable when equals`()
    }

    @Test
    fun `Verify selectAllByDoubleNotNullSup finds doubleWithoutNullable - Android`() {
        `Verify selectAllByDoubleNotNullSup finds doubleWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByDoubleNotNullSup finds no results when equals - Android`() {
        `Verify selectAllByDoubleNotNullSup finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByDoubleNotNullSupOrEq finds doubleWithoutNullable - Android`() {
        `Verify selectAllByDoubleNotNullSupOrEq finds doubleWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByDoubleNotNullSupOrEq finds doubleWithoutNullable when equals - Android`() {
        `Verify selectAllByDoubleNotNullSupOrEq finds doubleWithoutNullable when equals`()
    }

    @Test
    fun `Verify selectAllByDoubleNullable finds doubleWithNullable - Android`() {
        `Verify selectAllByDoubleNullable finds doubleWithNullable`()
    }

    @Test
    fun `Verify selectAllByDoubleNullable finds doubleWithoutNullable - Android`() {
        `Verify selectAllByDoubleNullable finds doubleWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByDoubleNullableNotEq finds nothing - Android`() {
        `Verify selectAllByDoubleNullableNotEq finds nothing`()
    }

    @Test
    fun `Verify selectAllByDoubleNullableNotEq finds no results - Android`() {
        `Verify selectAllByDoubleNullableNotEq finds no results`()
    }

    @Test
    fun `Verify selectAllByDoubleNullableInf finds doubleWithNullable - Android`() {
        `Verify selectAllByDoubleNullableInf finds doubleWithNullable`()
    }

    @Test
    fun `Verify selectAllByDoubleNullableInf finds no results when equals - Android`() {
        `Verify selectAllByDoubleNullableInf finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByDoubleNullableInfOrEq finds doubleWithNullable - Android`() {
        `Verify selectAllByDoubleNullableInfOrEq finds doubleWithNullable`()
    }

    @Test
    fun `Verify selectAllByDoubleNullableInfOrEq finds doubleWithNullable when equals - Android`() {
        `Verify selectAllByDoubleNullableInfOrEq finds doubleWithNullable when equals`()
    }

    @Test
    fun `Verify selectAllByDoubleNullableSup finds doubleWithoutNullable - Android`() {
        `Verify selectAllByDoubleNullableSup finds doubleWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByDoubleNullableSup finds no results when equals - Android`() {
        `Verify selectAllByDoubleNullableSup finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByDoubleNullableSupOrEq finds doubleWithoutNullable - Android`() {
        `Verify selectAllByDoubleNullableSupOrEq finds doubleWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByDoubleNullableSupOrEq finds doubleWithoutNullable when equals - Android`() {
        `Verify selectAllByDoubleNullableSupOrEq finds doubleWithoutNullable when equals`()
    }
}

class SelectDoubleRepositorySqliteSelect(
    sqLiteOpenHelper: SQLiteOpenHelper,
    tables: SqLiteTables,
) : SelectDoubleRepository<SqliteDoubles>(sqLiteOpenHelper.sqlClient(tables), SqliteDoubles)
