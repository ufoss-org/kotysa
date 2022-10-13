/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import org.ufoss.kotysa.SqlClient
import org.ufoss.kotysa.test.*
import java.time.LocalTime
import java.util.*

abstract class SelectUuidRepository<T : Uuids>(
    private val sqlClient: SqlClient,
    private val table: T,
) : Repository {

    override fun init() {
        createTables()
        insertUuids()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() {
        sqlClient createTable table
    }

    private fun insertUuids() {
        sqlClient.insert(uuidWithNullable, uuidWithoutNullable)
    }

    private fun deleteAll() = sqlClient deleteAllFrom table

    fun selectAllByUuidIdNotNull(uuid: UUID) =
        (sqlClient selectFrom table
                where table.uuidNotNull eq uuid
                ).fetchAll()

    fun selectAllByUuidNotNullNotEq(uuid: UUID) =
        (sqlClient selectFrom table
                where table.uuidNotNull notEq uuid
                ).fetchAll()

    fun selectAllByUuidNotNullIn(uuids: Sequence<UUID>) =
        (sqlClient selectFrom table
                where table.id `in` uuids
                ).fetchAll()

    fun selectAllByUuidNullable(uuid: UUID?) =
        (sqlClient selectFrom table
                where table.uuidNullable eq uuid
                ).fetchAll()

    fun selectAllByUuidNullableNotEq(uuid: UUID?) =
        (sqlClient selectFrom table
                where table.uuidNullable notEq uuid
                ).fetchAll()
}
