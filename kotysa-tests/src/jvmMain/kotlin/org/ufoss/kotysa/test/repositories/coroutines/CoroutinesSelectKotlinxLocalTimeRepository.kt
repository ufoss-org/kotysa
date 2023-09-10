/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalTime
import org.ufoss.kotysa.CoroutinesSqlClient
import org.ufoss.kotysa.test.KotlinxLocalTimes
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.kotlinxLocalTimeWithNullable
import org.ufoss.kotysa.test.kotlinxLocalTimeWithoutNullable

abstract class CoroutinesSelectKotlinxLocalTimeRepository<T : KotlinxLocalTimes>(
    private val sqlClient: CoroutinesSqlClient,
    private val table: T,
) : Repository {

    override fun init() = runBlocking {
        createTables()
        insertLocalTimes()
    }

    override fun delete() = runBlocking<Unit> {
        deleteAll()
    }

    private suspend fun createTables() {
        sqlClient createTableIfNotExists table
    }

    private suspend fun insertLocalTimes() {
        sqlClient.insert(kotlinxLocalTimeWithNullable, kotlinxLocalTimeWithoutNullable)
    }

    private suspend fun deleteAll() = sqlClient deleteAllFrom table

    fun selectAllByLocalTimeNotNull(localTime: LocalTime) =
        (sqlClient selectFrom table
                where table.localTimeNotNull eq localTime
                ).fetchAll()

    fun selectAllByLocalTimeNotNullNotEq(localTime: LocalTime) =
        (sqlClient selectFrom table
                where table.localTimeNotNull notEq localTime
                ).fetchAll()

    fun selectAllByLocalTimeNotNullIn(values: Sequence<LocalTime>) =
        (sqlClient selectFrom table
                where table.localTimeNotNull `in` values
                ).fetchAll()

    fun selectAllByLocalTimeNotNullBefore(localTime: LocalTime) =
        (sqlClient selectFrom table
                where table.localTimeNotNull before localTime
                ).fetchAll()

    fun selectAllByLocalTimeNotNullBeforeOrEq(localTime: LocalTime) =
        (sqlClient selectFrom table
                where table.localTimeNotNull beforeOrEq localTime
                ).fetchAll()

    fun selectAllByLocalTimeNotNullAfter(localTime: LocalTime) =
        (sqlClient selectFrom table
                where table.localTimeNotNull after localTime
                ).fetchAll()

    fun selectAllByLocalTimeNotNullAfterOrEq(localTime: LocalTime) =
        (sqlClient selectFrom table
                where table.localTimeNotNull afterOrEq localTime
                ).fetchAll()

    fun selectAllByLocalTimeNullable(localTime: LocalTime?) =
        (sqlClient selectFrom table
                where table.localTimeNullable eq localTime
                ).fetchAll()

    fun selectAllByLocalTimeNullableNotEq(localTime: LocalTime?) =
        (sqlClient selectFrom table
                where table.localTimeNullable notEq localTime
                ).fetchAll()

    fun selectAllByLocalTimeNullableBefore(localTime: LocalTime) =
        (sqlClient selectFrom table
                where table.localTimeNullable before localTime
                ).fetchAll()

    fun selectAllByLocalTimeNullableBeforeOrEq(localTime: LocalTime) =
        (sqlClient selectFrom table
                where table.localTimeNullable beforeOrEq localTime
                ).fetchAll()

    fun selectAllByLocalTimeNullableAfter(localTime: LocalTime) =
        (sqlClient selectFrom table
                where table.localTimeNullable after localTime
                ).fetchAll()

    fun selectAllByLocalTimeNullableAfterOrEq(localTime: LocalTime) =
        (sqlClient selectFrom table
                where table.localTimeNullable afterOrEq localTime
                ).fetchAll()
}
