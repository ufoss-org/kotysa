/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.junit.Test
import org.ufoss.kotysa.SqLiteTables
import org.ufoss.kotysa.android.transaction.AndroidTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayTest

class SqliteSelectByteArrayAsBlobTest : AbstractSqLiteTest<ByteArrayRepositorySqliteSelect>(),
    SelectByteArrayTest<SqliteByteArrays, ByteArrayRepositorySqliteSelect, AndroidTransaction> {

    override fun getRepository(sqLiteTables: SqLiteTables) = ByteArrayRepositorySqliteSelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectAllByByteArrayNotNull finds byteArrayWithNullable - Android`() {
        `Verify selectAllByByteArrayNotNull finds byteArrayWithNullable`()
    }

    @Test
    fun `Verify selectAllByByteArrayNotNullNotEq finds byteArrayWithoutNullable - Android`() {
        `Verify selectAllByByteArrayNotNullNotEq finds byteArrayWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByByteArrayNotNullIn finds both - Android`() {
        `Verify selectAllByByteArrayNotNullIn finds both`()
    }

    @Test
    fun `Verify selectAllByByteArrayNullable finds byteArrayWithNullable - Android`() {
        `Verify selectAllByByteArrayNullable finds byteArrayWithNullable`()
    }

    @Test
    fun `Verify selectAllByByteArrayNullable finds byteArrayWithoutNullable - Android`() {
        `Verify selectAllByByteArrayNullable finds byteArrayWithoutNullable`()
    }

    @Test
    fun `Verify selectAllByByteArrayNullableNotEq finds no results - Android`() {
        `Verify selectAllByByteArrayNullableNotEq finds no results`()
    }

    @Test
    fun `Verify selectAllByByteArrayNullableNotEq finds byteArrayWithNullable - Android`() {
        `Verify selectAllByByteArrayNullableNotEq finds byteArrayWithNullable`()
    }
}

class ByteArrayRepositorySqliteSelect(sqLiteOpenHelper: SQLiteOpenHelper, tables: SqLiteTables) :
    SelectByteArrayRepository<SqliteByteArrays>(sqLiteOpenHelper.sqlClient(tables), SqliteByteArrays)
