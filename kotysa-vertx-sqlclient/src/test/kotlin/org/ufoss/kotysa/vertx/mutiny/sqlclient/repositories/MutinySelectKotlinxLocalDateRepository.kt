/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories

import kotlinx.datetime.LocalDate
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySqlClient
import org.ufoss.kotysa.test.KotlinxLocalDates
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.kotlinxLocalDateWithNullable
import org.ufoss.kotysa.test.kotlinxLocalDateWithoutNullable

abstract class MutinySelectKotlinxLocalDateRepository<T : KotlinxLocalDates>(
    private val sqlClient: MutinySqlClient,
    private val table: T,
) : Repository {

    override fun init() {
        createTables()
            .chain { -> insertLocalDates() }
            .await().indefinitely()
    }

    override fun delete() {
        deleteAll()
            .await().indefinitely()
    }

    private fun createTables() = sqlClient createTable table

    private fun insertLocalDates() = sqlClient.insert(kotlinxLocalDateWithNullable, kotlinxLocalDateWithoutNullable)

    private fun deleteAll() = sqlClient deleteAllFrom table

    fun selectAllByLocalDateNotNull(localDate: LocalDate) =
        (sqlClient selectFrom table
                where table.localDateNotNull eq localDate
                ).fetchAll()

    fun selectAllByLocalDateNotNullNotEq(localDate: LocalDate) =
        (sqlClient selectFrom table
                where table.localDateNotNull notEq localDate
                ).fetchAll()

    fun selectAllByLocalDateNotNullIn(values: Sequence<LocalDate>) =
        (sqlClient selectFrom table
                where table.localDateNotNull `in` values
                ).fetchAll()

    fun selectAllByLocalDateNotNullBefore(localDate: LocalDate) =
        (sqlClient selectFrom table
                where table.localDateNotNull before localDate
                ).fetchAll()

    fun selectAllByLocalDateNotNullBeforeOrEq(localDate: LocalDate) =
        (sqlClient selectFrom table
                where table.localDateNotNull beforeOrEq localDate
                ).fetchAll()

    fun selectAllByLocalDateNotNullAfter(localDate: LocalDate) =
        (sqlClient selectFrom table
                where table.localDateNotNull after localDate
                ).fetchAll()

    fun selectAllByLocalDateNotNullAfterOrEq(localDate: LocalDate) =
        (sqlClient selectFrom table
                where table.localDateNotNull afterOrEq localDate
                ).fetchAll()

    fun selectAllByLocalDateNullable(localDate: LocalDate?) =
        (sqlClient selectFrom table
                where table.localDateNullable eq localDate
                ).fetchAll()

    fun selectAllByLocalDateNullableNotEq(localDate: LocalDate?) =
        (sqlClient selectFrom table
                where table.localDateNullable notEq localDate
                ).fetchAll()

    fun selectAllByLocalDateNullableBefore(localDate: LocalDate) =
        (sqlClient selectFrom table
                where table.localDateNullable before localDate
                ).fetchAll()

    fun selectAllByLocalDateNullableBeforeOrEq(localDate: LocalDate) =
        (sqlClient selectFrom table
                where table.localDateNullable beforeOrEq localDate
                ).fetchAll()

    fun selectAllByLocalDateNullableAfter(localDate: LocalDate) =
        (sqlClient selectFrom table
                where table.localDateNullable after localDate
                ).fetchAll()

    fun selectAllByLocalDateNullableAfterOrEq(localDate: LocalDate) =
        (sqlClient selectFrom table
                where table.localDateNullable afterOrEq localDate
                ).fetchAll()
}
