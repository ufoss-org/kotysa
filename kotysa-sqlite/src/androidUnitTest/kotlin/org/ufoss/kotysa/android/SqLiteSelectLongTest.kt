/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.junit.Test
import org.ufoss.kotysa.SqLiteTables
import org.ufoss.kotysa.android.transaction.AndroidTransaction
import org.ufoss.kotysa.test.SqliteLongs
import org.ufoss.kotysa.test.repositories.blocking.SelectLongRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLongTest

class SqLiteSelectLongTest : AbstractSqLiteTest<SelectLongRepositorySelect>(),
    SelectLongTest<SqliteLongs, SelectLongRepositorySelect, AndroidTransaction> {

    override fun getRepository(sqLiteTables: SqLiteTables) = SelectLongRepositorySelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectAllByLongNotNull finds longWithNullable - Android`() {
        `Verify selectAllByLongNotNull finds longWithNullable`()
    }

    @Test
    fun `Verify selectAllByLongNotNullNotEq finds longWithoutNullable - Android`() {
        `Verify selectAllByLongNotNullNotEq finds longWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLongNotNullIn finds both - Android`() {
        `Verify selectAllByLongNotNullIn finds both`()
    }

    @Test
    fun `Verify selectAllByLongNotNullInf finds longWithNullable - Android`() {
        `Verify selectAllByLongNotNullInf finds longWithNullable`()
    }

    @Test
    fun `Verify selectAllByLongNotNullInf finds no results when equals - Android`() {
        `Verify selectAllByLongNotNullInf finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByLongNotNullInfOrEq finds longWithNullable - Android`() {
        `Verify selectAllByLongNotNullInfOrEq finds longWithNullable`()
    }

    @Test
    fun `Verify selectAllByLongNotNullInfOrEq finds longWithNullable when equals - Android`() {
        `Verify selectAllByLongNotNullInfOrEq finds longWithNullable when equals`()
    }

    @Test
    fun `Verify selectAllByLongNotNullSup finds longWithoutNullable - Android`() {
        `Verify selectAllByLongNotNullSup finds longWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLongNotNullSup finds no results when equals - Android`() {
        `Verify selectAllByLongNotNullSup finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByLongNotNullSupOrEq finds longWithoutNullable - Android`() {
        `Verify selectAllByLongNotNullSupOrEq finds longWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLongNotNullSupOrEq finds longWithoutNullable when equals - Android`() {
        `Verify selectAllByLongNotNullSupOrEq finds longWithoutNullable when equals`()
    }

    @Test
    fun `Verify selectAllByLongNullable finds longWithNullable - Android`() {
        `Verify selectAllByLongNullable finds longWithNullable`()
    }

    @Test
    fun `Verify selectAllByLongNullable finds longWithoutNullable - Android`() {
        `Verify selectAllByLongNullable finds longWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByLongNullableNotEq finds no results - Android`() {
        `Verify selectAllByLongNullableNotEq finds no results`()
    }

    @Test
    fun `Verify selectAllByLongNullableNotEq finds longWithNullable - Android`() {
        `Verify selectAllByLongNullableNotEq finds longWithNullable`()
    }

    @Test
    fun `Verify selectAllByLongNullableInf finds longWithNullable - Android`() {
        `Verify selectAllByLongNullableInf finds longWithNullable`()
    }

    @Test
    fun `Verify selectAllByLongNullableInf finds no results when equals - Android`() {
        `Verify selectAllByLongNullableInf finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByLongNullableInfOrEq finds longWithNullable - Android`() {
        `Verify selectAllByLongNullableInfOrEq finds longWithNullable`()
    }

    @Test
    fun `Verify selectAllByLongNullableInfOrEq finds longWithNullable when equals - Android`() {
        `Verify selectAllByLongNullableInfOrEq finds longWithNullable when equals`()
    }

    @Test
    fun `Verify selectAllByLongNullableSup finds longWithNullable - Android`() {
        `Verify selectAllByLongNullableSup finds longWithNullable`()
    }

    @Test
    fun `Verify selectAllByLongNullableSup finds no results when equals - Android`() {
        `Verify selectAllByLongNullableSup finds no results when equals`()
    }

    @Test
    fun `Verify selectAllByLongNullableSupOrEq finds longWithNullable - Android`() {
        `Verify selectAllByLongNullableSupOrEq finds longWithNullable`()
    }

    @Test
    fun `Verify selectAllByLongNullableSupOrEq finds longWithNullable when equals - Android`() {
        `Verify selectAllByLongNullableSupOrEq finds longWithNullable when equals`()
    }
}

class SelectLongRepositorySelect(sqLiteOpenHelper: SQLiteOpenHelper, tables: SqLiteTables) :
    SelectLongRepository<SqliteLongs>(sqLiteOpenHelper.sqlClient(tables), SqliteLongs)
