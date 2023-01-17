/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories

import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySqlClient
import org.ufoss.kotysa.test.*

abstract class MutinySelectByteArrayAsBinaryRepository<T : ByteArrayAsBinaries>(
        private val sqlClient: MutinySqlClient,
        private val table: T,
) : Repository {

    override fun init() {
        createTables()
            .chain { -> insertByteArrays() }
            .await().indefinitely()
    }

    override fun delete() {
        deleteAll()
            .await().indefinitely()
    }

    private fun createTables() = sqlClient createTable table

    private fun insertByteArrays() = sqlClient.insert(byteArrayBinaryWithNullable, byteArrayBinaryWithoutNullable)

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
