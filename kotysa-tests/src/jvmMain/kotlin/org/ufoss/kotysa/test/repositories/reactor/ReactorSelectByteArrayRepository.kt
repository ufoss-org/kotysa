/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.reactor

import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.ByteArrays
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.byteArrayWithNullable
import org.ufoss.kotysa.test.byteArrayWithoutNullable

abstract class ReactorSelectByteArrayRepository<T : ByteArrays>(
    private val sqlClient: ReactorSqlClient,
    private val table: T,
) : Repository {

    override fun init() {
        createTables()
            .then(insertByteArrays())
            .block()
    }

    override fun delete() {
        deleteAll()
            .block()
    }

    private fun createTables() = sqlClient createTable table

    private fun insertByteArrays() = sqlClient.insert(byteArrayWithNullable, byteArrayWithoutNullable)

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
