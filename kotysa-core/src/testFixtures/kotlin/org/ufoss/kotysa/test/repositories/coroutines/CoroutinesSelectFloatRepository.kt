/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import kotlinx.coroutines.runBlocking
import org.ufoss.kotysa.CoroutinesSqlClient
import org.ufoss.kotysa.test.*

abstract class CoroutinesSelectFloatRepository<T : Floats>(
    private val sqlClient: CoroutinesSqlClient,
    private val table: T,
) : Repository {

    override fun init() = runBlocking {
        createTable()
        insert()
    }

    override fun delete() = runBlocking<Unit> {
        deleteAll()
    }

    private suspend fun createTable() {
        sqlClient createTable table
    }

    suspend fun insert() {
        sqlClient.insert(floatWithNullable, floatWithoutNullable)
    }

    suspend fun deleteAll() = sqlClient deleteAllFrom table

    fun selectAll() = sqlClient selectAllFrom table

    fun selectAllByFloatNotNull(float: Float) =
        (sqlClient selectFrom table
                where table.floatNotNull eq float
                ).fetchAll()

    fun selectAllByFloatNotNullNotEq(float: Float) =
        (sqlClient selectFrom table
                where table.floatNotNull notEq float
                ).fetchAll()

    fun selectAllByFloatNotNullIn(values: Sequence<Float>) =
        (sqlClient selectFrom table
                where table.floatNotNull `in` values
                ).fetchAll()

    fun selectAllByFloatNotNullInf(float: Float) =
        (sqlClient selectFrom table
                where table.floatNotNull inf float
                ).fetchAll()

    fun selectAllByFloatNotNullInfOrEq(float: Float) =
        (sqlClient selectFrom table
                where table.floatNotNull infOrEq float
                ).fetchAll()

    fun selectAllByFloatNotNullSup(float: Float) =
        (sqlClient selectFrom table
                where table.floatNotNull sup float
                ).fetchAll()

    fun selectAllByFloatNotNullSupOrEq(float: Float) =
        (sqlClient selectFrom table
                where table.floatNotNull supOrEq float
                ).fetchAll()

    fun selectAllByFloatNullable(float: Float?) =
        (sqlClient selectFrom table
                where table.floatNullable eq float
                ).fetchAll()

    fun selectAllByFloatNullableNotEq(float: Float?) =
        (sqlClient selectFrom table
                where table.floatNullable notEq float
                ).fetchAll()

    fun selectAllByFloatNullableInf(float: Float) =
        (sqlClient selectFrom table
                where table.floatNullable inf float
                ).fetchAll()

    fun selectAllByFloatNullableInfOrEq(float: Float) =
        (sqlClient selectFrom table
                where table.floatNullable infOrEq float
                ).fetchAll()

    fun selectAllByFloatNullableSup(float: Float) =
        (sqlClient selectFrom table
                where table.floatNullable sup float
                ).fetchAll()

    fun selectAllByFloatNullableSupOrEq(float: Float) =
        (sqlClient selectFrom table
                where table.floatNullable supOrEq float
                ).fetchAll()
}
