/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import kotlinx.coroutines.runBlocking
import org.ufoss.kotysa.CoroutinesSqlClient
import org.ufoss.kotysa.test.*

abstract class CoroutinesSelectDoubleRepository<T : Doubles>(
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
        sqlClient createTableIfNotExists table
    }

    suspend fun insert() {
        sqlClient.insert(doubleWithNullable, doubleWithoutNullable)
    }

    suspend fun deleteAll() = sqlClient deleteAllFrom table

    fun selectAll() = sqlClient selectAllFrom table

    fun selectAllByDoubleNotNull(double: Double) =
        (sqlClient selectFrom table
                where table.doubleNotNull eq double
                ).fetchAll()

    fun selectAllByDoubleNotNullNotEq(double: Double) =
        (sqlClient selectFrom table
                where table.doubleNotNull notEq double
                ).fetchAll()

    fun selectAllByDoubleNotNullIn(values: Sequence<Double>) =
        (sqlClient selectFrom table
                where table.doubleNotNull `in` values
                ).fetchAll()

    fun selectAllByDoubleNotNullInf(double: Double) =
        (sqlClient selectFrom table
                where table.doubleNotNull inf double
                ).fetchAll()

    fun selectAllByDoubleNotNullInfOrEq(double: Double) =
        (sqlClient selectFrom table
                where table.doubleNotNull infOrEq double
                ).fetchAll()

    fun selectAllByDoubleNotNullSup(double: Double) =
        (sqlClient selectFrom table
                where table.doubleNotNull sup double
                ).fetchAll()

    fun selectAllByDoubleNotNullSupOrEq(double: Double) =
        (sqlClient selectFrom table
                where table.doubleNotNull supOrEq double
                ).fetchAll()

    fun selectAllByDoubleNullable(double: Double?) =
        (sqlClient selectFrom table
                where table.doubleNullable eq double
                ).fetchAll()

    fun selectAllByDoubleNullableNotEq(double: Double?) =
        (sqlClient selectFrom table
                where table.doubleNullable notEq double
                ).fetchAll()

    fun selectAllByDoubleNullableInf(double: Double) =
        (sqlClient selectFrom table
                where table.doubleNullable inf double
                ).fetchAll()

    fun selectAllByDoubleNullableInfOrEq(double: Double) =
        (sqlClient selectFrom table
                where table.doubleNullable infOrEq double
                ).fetchAll()

    fun selectAllByDoubleNullableSup(double: Double) =
        (sqlClient selectFrom table
                where table.doubleNullable sup double
                ).fetchAll()

    fun selectAllByDoubleNullableSupOrEq(double: Double) =
        (sqlClient selectFrom table
                where table.doubleNullable supOrEq double
                ).fetchAll()
}
