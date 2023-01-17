/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import kotlinx.coroutines.runBlocking
import org.ufoss.kotysa.CoroutinesSqlClient
import org.ufoss.kotysa.test.*

abstract class CoroutinesSelectIntRepository<T : Ints>(
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
        sqlClient.insert(intWithNullable, intWithoutNullable)
    }

    suspend fun deleteAll() = sqlClient deleteAllFrom table

    fun selectAll() = sqlClient selectAllFrom table

    fun selectAllByIntNotNull(int: Int) =
        (sqlClient selectFrom table
                where table.intNotNull eq int
                ).fetchAll()

    fun selectAllByIntNotNullNotEq(int: Int) =
        (sqlClient selectFrom table
                where table.intNotNull notEq int
                ).fetchAll()

    fun selectAllByIntNotNullIn(values: Sequence<Int>) =
        (sqlClient selectFrom table
                where table.intNotNull `in` values
                ).fetchAll()

    fun selectAllByIntNotNullInf(int: Int) =
        (sqlClient selectFrom table
                where table.intNotNull inf int
                ).fetchAll()

    fun selectAllByIntNotNullInfOrEq(int: Int) =
        (sqlClient selectFrom table
                where table.intNotNull infOrEq int
                ).fetchAll()

    fun selectAllByIntNotNullSup(int: Int) =
        (sqlClient selectFrom table
                where table.intNotNull sup int
                ).fetchAll()

    fun selectAllByIntNotNullSupOrEq(int: Int) =
        (sqlClient selectFrom table
                where table.intNotNull supOrEq int
                ).fetchAll()

    fun selectAllByIntNullable(int: Int?) =
        (sqlClient selectFrom table
                where table.intNullable eq int
                ).fetchAll()

    fun selectAllByIntNullableNotEq(int: Int?) =
        (sqlClient selectFrom table
                where table.intNullable notEq int
                ).fetchAll()

    fun selectAllByIntNullableInf(int: Int) =
        (sqlClient selectFrom table
                where table.intNullable inf int
                ).fetchAll()

    fun selectAllByIntNullableInfOrEq(int: Int) =
        (sqlClient selectFrom table
                where table.intNullable infOrEq int
                ).fetchAll()

    fun selectAllByIntNullableSup(int: Int) =
        (sqlClient selectFrom table
                where table.intNullable sup int
                ).fetchAll()

    fun selectAllByIntNullableSupOrEq(int: Int) =
        (sqlClient selectFrom table
                where table.intNullable supOrEq int
                ).fetchAll()
}
