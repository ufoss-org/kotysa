/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import kotlinx.coroutines.runBlocking
import org.ufoss.kotysa.CoroutinesSqlClient
import org.ufoss.kotysa.test.*
import java.util.*

abstract class CoroutinesSelectUuidRepository<T : Uuids>(
    private val sqlClient: CoroutinesSqlClient,
    private val table: T,
) : Repository {

    override fun init() = runBlocking {
        createTables()
        insertUuids()
    }

    override fun delete() = runBlocking<Unit> {
        deleteAll()
    }

    private suspend fun createTables() {
        sqlClient createTable table
    }

    private suspend fun insertUuids() {
        sqlClient.insert(uuidWithNullable, uuidWithoutNullable)
    }

    private suspend fun deleteAll() = sqlClient deleteAllFrom table

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
