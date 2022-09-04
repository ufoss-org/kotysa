/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.ufoss.kotysa.SqLiteTables
import org.ufoss.kotysa.test.*

class SqliteSelectByteArrayAsBlobTest : AbstractSqLiteTest<ByteArrayRepositorySqliteSelect>() {

    override fun getRepository(sqLiteTables: SqLiteTables) = ByteArrayRepositorySqliteSelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectAllByByteArrayNotNull finds byteArrayWithNullable`() {
        assertThat(repository.selectAllByByteArrayNotNull(byteArrayWithNullable.byteArrayNotNull))
                .hasSize(1)
                .containsExactlyInAnyOrder(byteArrayWithNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNotNullNotEq finds byteArrayWithoutNullable`() {
        assertThat(repository.selectAllByByteArrayNotNullNotEq(byteArrayWithNullable.byteArrayNotNull))
                .hasSize(1)
                .containsExactlyInAnyOrder(byteArrayWithoutNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNotNullIn finds both`() {
        val seq = sequenceOf(byteArrayWithNullable.byteArrayNotNull, byteArrayWithoutNullable.byteArrayNotNull)
        assertThat(repository.selectAllByByteArrayNotNullIn(seq))
                .hasSize(2)
                .containsExactlyInAnyOrder(byteArrayWithNullable, byteArrayWithoutNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNullable finds byteArrayWithNullable`() {
        assertThat(repository.selectAllByByteArrayNullable(byteArrayWithNullable.byteArrayNullable))
                .hasSize(1)
                .containsExactlyInAnyOrder(byteArrayWithNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNullable finds byteArrayWithoutNullable`() {
        assertThat(repository.selectAllByByteArrayNullable(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(byteArrayWithoutNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNullableNotEq finds no results`() {
        assertThat(repository.selectAllByByteArrayNullableNotEq(byteArrayWithNullable.byteArrayNullable))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByByteArrayNullableNotEq finds byteArrayWithNullable`() {
        assertThat(repository.selectAllByByteArrayNullableNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(byteArrayWithNullable)
    }
}


class ByteArrayRepositorySqliteSelect(sqLiteOpenHelper: SQLiteOpenHelper, tables: SqLiteTables) : Repository {

    private val sqlClient = sqLiteOpenHelper.sqlClient(tables)

    override fun init() {
        createTables()
        insertByteArrays()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() {
        sqlClient createTable SqliteByteArrays
    }

    private fun insertByteArrays() {
        sqlClient.insert(byteArrayWithNullable, byteArrayWithoutNullable)
    }

    private fun deleteAll() = sqlClient deleteAllFrom SqliteByteArrays

    fun selectAllByByteArrayNotNull(byteArray: ByteArray) =
            (sqlClient selectFrom SqliteByteArrays
                    where SqliteByteArrays.byteArrayNotNull eq byteArray
                    ).fetchAll()

    fun selectAllByByteArrayNotNullNotEq(byteArray: ByteArray) =
            (sqlClient selectFrom SqliteByteArrays
                    where SqliteByteArrays.byteArrayNotNull notEq byteArray
                    ).fetchAll()

    fun selectAllByByteArrayNotNullIn(values: Sequence<ByteArray>) =
            (sqlClient selectFrom SqliteByteArrays
                    where SqliteByteArrays.byteArrayNotNull `in` values
                    ).fetchAll()

    fun selectAllByByteArrayNullable(byteArray: ByteArray?) =
            (sqlClient selectFrom SqliteByteArrays
                    where SqliteByteArrays.byteArrayNullable eq byteArray
                    ).fetchAll()

    fun selectAllByByteArrayNullableNotEq(byteArray: ByteArray?) =
            (sqlClient selectFrom SqliteByteArrays
                    where SqliteByteArrays.byteArrayNullable notEq byteArray
                    ).fetchAll()
}
