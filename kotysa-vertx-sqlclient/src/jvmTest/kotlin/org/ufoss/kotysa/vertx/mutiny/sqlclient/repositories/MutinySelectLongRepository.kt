/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories

import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySqlClient
import org.ufoss.kotysa.test.Longs
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.longWithNullable
import org.ufoss.kotysa.test.longWithoutNullable

abstract class MutinySelectLongRepository<T : Longs>(
    private val sqlClient: MutinySqlClient,
    private val table: T,
) : Repository {

    override fun init() {
        createTable()
            .chain { -> insert() }
            .await().indefinitely()
    }

    override fun delete() {
        deleteAll()
            .await().indefinitely()
    }

    private fun createTable() = sqlClient createTable table

    fun insert() = sqlClient.insert(longWithNullable, longWithoutNullable)

    fun deleteAll() = sqlClient deleteAllFrom table

    fun selectAllByLongNotNull(long: Long) =
        (sqlClient selectFrom table
                where table.longNotNull eq long
                ).fetchAll()

    fun selectAllByLongNotNullNotEq(long: Long) =
        (sqlClient selectFrom table
                where table.longNotNull notEq long
                ).fetchAll()

    fun selectAllByLongNotNullIn(values: Sequence<Long>) =
        (sqlClient selectFrom table
                where table.longNotNull `in` values
                ).fetchAll()

    fun selectAllByLongNotNullInf(long: Long) =
        (sqlClient selectFrom table
                where table.longNotNull inf long
                ).fetchAll()

    fun selectAllByLongNotNullInfOrEq(long: Long) =
        (sqlClient selectFrom table
                where table.longNotNull infOrEq long
                ).fetchAll()

    fun selectAllByLongNotNullSup(long: Long) =
        (sqlClient selectFrom table
                where table.longNotNull sup long
                ).fetchAll()

    fun selectAllByLongNotNullSupOrEq(long: Long) =
        (sqlClient selectFrom table
                where table.longNotNull supOrEq long
                ).fetchAll()

    fun selectAllByLongNullable(long: Long?) =
        (sqlClient selectFrom table
                where table.longNullable eq long
                ).fetchAll()

    fun selectAllByLongNullableNotEq(long: Long?) =
        (sqlClient selectFrom table
                where table.longNullable notEq long
                ).fetchAll()

    fun selectAllByLongNullableInf(long: Long) =
        (sqlClient selectFrom table
                where table.longNullable inf long
                ).fetchAll()

    fun selectAllByLongNullableInfOrEq(long: Long) =
        (sqlClient selectFrom table
                where table.longNullable infOrEq long
                ).fetchAll()

    fun selectAllByLongNullableSup(long: Long) =
        (sqlClient selectFrom table
                where table.longNullable sup long
                ).fetchAll()

    fun selectAllByLongNullableSupOrEq(long: Long) =
        (sqlClient selectFrom table
                where table.longNullable supOrEq long
                ).fetchAll()
}
