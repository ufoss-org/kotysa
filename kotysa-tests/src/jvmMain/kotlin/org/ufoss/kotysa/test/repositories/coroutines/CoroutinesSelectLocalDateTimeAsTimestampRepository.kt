/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import kotlinx.coroutines.runBlocking
import org.ufoss.kotysa.CoroutinesSqlClient
import org.ufoss.kotysa.test.*
import java.time.LocalDateTime

abstract class CoroutinesSelectLocalDateTimeAsTimestampRepository<T : LocalDateTimeAsTimestamps>(
    private val sqlClient: CoroutinesSqlClient,
    private val table: T,
) : Repository {

    override fun init() = runBlocking {
        createTables()
        insertLocalDateTimes()
    }

    override fun delete() = runBlocking<Unit> {
        deleteAll()
    }

    private suspend fun createTables() {
        sqlClient createTableIfNotExists table
    }

    private suspend fun insertLocalDateTimes() {
        sqlClient.insert(localDateTimeAsTimestampWithNullable, localDateTimeAsTimestampWithoutNullable)
    }

    private suspend fun deleteAll() = sqlClient deleteAllFrom table

    fun selectAllByLocalDateTimeNotNull(localDateTime: LocalDateTime) =
        (sqlClient selectFrom table
                where table.localDateTimeNotNull eq localDateTime
                ).fetchAll()

    fun selectAllByLocalDateTimeNotNullNotEq(localDateTime: LocalDateTime) =
        (sqlClient selectFrom table
                where table.localDateTimeNotNull notEq localDateTime
                ).fetchAll()

    fun selectAllByLocalDateTimeNotNullIn(values: Sequence<LocalDateTime>) =
        (sqlClient selectFrom table
                where table.localDateTimeNotNull `in` values
                ).fetchAll()

    fun selectAllByLocalDateTimeNotNullBefore(localDateTime: LocalDateTime) =
        (sqlClient selectFrom table
                where table.localDateTimeNotNull before localDateTime
                ).fetchAll()

    fun selectAllByLocalDateTimeNotNullBeforeOrEq(localDateTime: LocalDateTime) =
        (sqlClient selectFrom table
                where table.localDateTimeNotNull beforeOrEq localDateTime
                ).fetchAll()

    fun selectAllByLocalDateTimeNotNullAfter(localDateTime: LocalDateTime) =
        (sqlClient selectFrom table
                where table.localDateTimeNotNull after localDateTime
                ).fetchAll()

    fun selectAllByLocalDateTimeNotNullAfterOrEq(localDateTime: LocalDateTime) =
        (sqlClient selectFrom table
                where table.localDateTimeNotNull afterOrEq localDateTime
                ).fetchAll()

    fun selectAllByLocalDateTimeNullable(localDateTime: LocalDateTime?) =
        (sqlClient selectFrom table
                where table.localDateTimeNullable eq localDateTime
                ).fetchAll()

    fun selectAllByLocalDateTimeNullableNotEq(localDateTime: LocalDateTime?) =
        (sqlClient selectFrom table
                where table.localDateTimeNullable notEq localDateTime
                ).fetchAll()

    fun selectAllByLocalDateTimeNullableBefore(localDateTime: LocalDateTime) =
        (sqlClient selectFrom table
                where table.localDateTimeNullable before localDateTime
                ).fetchAll()

    fun selectAllByLocalDateTimeNullableBeforeOrEq(localDateTime: LocalDateTime) =
        (sqlClient selectFrom table
                where table.localDateTimeNullable beforeOrEq localDateTime
                ).fetchAll()

    fun selectAllByLocalDateTimeNullableAfter(localDateTime: LocalDateTime) =
        (sqlClient selectFrom table
                where table.localDateTimeNullable after localDateTime
                ).fetchAll()

    fun selectAllByLocalDateTimeNullableAfterOrEq(localDateTime: LocalDateTime) =
        (sqlClient selectFrom table
                where table.localDateTimeNullable afterOrEq localDateTime
                ).fetchAll()
}
