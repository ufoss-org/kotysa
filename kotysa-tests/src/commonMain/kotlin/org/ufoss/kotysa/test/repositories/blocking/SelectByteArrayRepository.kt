/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import org.ufoss.kotysa.SqlClient
import org.ufoss.kotysa.test.*

abstract class SelectByteArrayRepository<T : ByteArrays>(
        private val sqlClient: SqlClient,
        private val table: T,
) : Repository {

    override fun init() {
        createTables()
        insertByteArrays()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() {
        sqlClient createTable table
    }

    private fun insertByteArrays() {
        sqlClient.insert(byteArrayWithNullable, byteArrayWithoutNullable)
    }

    private fun deleteAll() = sqlClient deleteAllFrom table

    fun selectAllByByteArrayNotNull(byteArray: ByteArray) =
        (sqlClient selectFrom table
                where table.byteArrayNotNull eq byteArray
                ).fetchAll()

    fun selectAllByByteArrayNotNullNotEq(byteArray: ByteArray) =
        (sqlClient selectFrom table
                where table.byteArrayNotNull notEq byteArray
                ).fetchAll()

    fun selectAllByByteArrayNotNullIn(values: Sequence<ByteArray>) =
        (sqlClient selectFrom table
                where table.byteArrayNotNull `in` values
                ).fetchAll()

    fun selectAllByByteArrayNullable(byteArray: ByteArray?) =
        (sqlClient selectFrom table
                where table.byteArrayNullable eq byteArray
                ).fetchAll()

    fun selectAllByByteArrayNullableNotEq(byteArray: ByteArray?) =
        (sqlClient selectFrom table
                where table.byteArrayNullable notEq byteArray
                ).fetchAll()
}
