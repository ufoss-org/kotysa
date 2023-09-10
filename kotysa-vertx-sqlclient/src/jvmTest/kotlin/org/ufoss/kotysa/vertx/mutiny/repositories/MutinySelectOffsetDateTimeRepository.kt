/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.repositories

import org.ufoss.kotysa.vertx.MutinySqlClient
import org.ufoss.kotysa.test.OffsetDateTimes
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.offsetDateTimeWithNullable
import org.ufoss.kotysa.test.offsetDateTimeWithoutNullable
import java.time.OffsetDateTime

abstract class MutinySelectOffsetDateTimeRepository<T : OffsetDateTimes>(
    private val sqlClient: MutinySqlClient,
    private val table: T,
) : Repository {

    override fun init() {
        createTables()
            .chain { -> insertOffsetDateTimes() }
            .await().indefinitely()
    }

    override fun delete() {
        deleteAll()
            .await().indefinitely()
    }

    private fun createTables() = sqlClient createTableIfNotExists table

    private fun insertOffsetDateTimes() = sqlClient.insert(offsetDateTimeWithNullable, offsetDateTimeWithoutNullable)

    private fun deleteAll() = sqlClient deleteAllFrom table

    fun selectAllByOffsetDateTimeNotNull(offsetDateTime: OffsetDateTime) =
        (sqlClient selectFrom table
                where table.offsetDateTimeNotNull eq offsetDateTime
                ).fetchAll()

    fun selectAllByOffsetDateTimeNotNullNotEq(offsetDateTime: OffsetDateTime) =
        (sqlClient selectFrom table
                where table.offsetDateTimeNotNull notEq offsetDateTime
                ).fetchAll()

    fun selectAllByOffsetDateTimeNotNullIn(values: Sequence<OffsetDateTime>) =
        (sqlClient selectFrom table
                where table.offsetDateTimeNotNull `in` values
                ).fetchAll()

    fun selectAllByOffsetDateTimeNotNullBefore(offsetDateTime: OffsetDateTime) =
        (sqlClient selectFrom table
                where table.offsetDateTimeNotNull before offsetDateTime
                ).fetchAll()

    fun selectAllByOffsetDateTimeNotNullBeforeOrEq(offsetDateTime: OffsetDateTime) =
        (sqlClient selectFrom table
                where table.offsetDateTimeNotNull beforeOrEq offsetDateTime
                ).fetchAll()

    fun selectAllByOffsetDateTimeNotNullAfter(offsetDateTime: OffsetDateTime) =
        (sqlClient selectFrom table
                where table.offsetDateTimeNotNull after offsetDateTime
                ).fetchAll()

    fun selectAllByOffsetDateTimeNotNullAfterOrEq(offsetDateTime: OffsetDateTime) =
        (sqlClient selectFrom table
                where table.offsetDateTimeNotNull afterOrEq offsetDateTime
                ).fetchAll()

    fun selectAllByOffsetDateTimeNullable(offsetDateTime: OffsetDateTime?) =
        (sqlClient selectFrom table
                where table.offsetDateTimeNullable eq offsetDateTime
                ).fetchAll()

    fun selectAllByOffsetDateTimeNullableNotEq(offsetDateTime: OffsetDateTime?) =
        (sqlClient selectFrom table
                where table.offsetDateTimeNullable notEq offsetDateTime
                ).fetchAll()

    fun selectAllByOffsetDateTimeNullableBefore(offsetDateTime: OffsetDateTime) =
        (sqlClient selectFrom table
                where table.offsetDateTimeNullable before offsetDateTime
                ).fetchAll()

    fun selectAllByOffsetDateTimeNullableBeforeOrEq(offsetDateTime: OffsetDateTime) =
        (sqlClient selectFrom table
                where table.offsetDateTimeNullable beforeOrEq offsetDateTime
                ).fetchAll()

    fun selectAllByOffsetDateTimeNullableAfter(offsetDateTime: OffsetDateTime) =
        (sqlClient selectFrom table
                where table.offsetDateTimeNullable after offsetDateTime
                ).fetchAll()

    fun selectAllByOffsetDateTimeNullableAfterOrEq(offsetDateTime: OffsetDateTime) =
        (sqlClient selectFrom table
                where table.offsetDateTimeNullable afterOrEq offsetDateTime
                ).fetchAll()
}
