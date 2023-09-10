/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.repositories

import org.ufoss.kotysa.vertx.MutinySqlClient
import org.ufoss.kotysa.test.ByteArrays
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.byteArrayWithNullable
import org.ufoss.kotysa.test.byteArrayWithoutNullable

abstract class MutinySelectByteArrayRepository<T : ByteArrays>(
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

    private fun createTables() = sqlClient createTableIfNotExists table

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
