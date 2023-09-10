/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import kotlinx.coroutines.runBlocking
import org.ufoss.kotysa.CoroutinesSqlClient
import org.ufoss.kotysa.test.*

abstract class CoroutinesSelectLongRepository<T : Longs>(
    private val sqlClient: CoroutinesSqlClient,
    private val table: T,
) : Repository {

    internal lateinit var generatedLongWithNullable: LongEntity
    internal lateinit var generatedLongWithoutNullable: LongEntity

    override fun init() = runBlocking {
        createTable()
        insert()
    }

    override fun delete() = runBlocking<Unit> {
        deleteAll()
    }

    private suspend fun createTable() {
        sqlClient createTableIfNotExists table
    }

    suspend fun insert() {
        generatedLongWithNullable = sqlClient insertAndReturn longWithNullable
        generatedLongWithoutNullable = sqlClient insertAndReturn longWithoutNullable
    }

    suspend fun deleteAll() = sqlClient deleteAllFrom table

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
