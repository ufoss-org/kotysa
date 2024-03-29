/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.repositories

import org.ufoss.kotysa.vertx.MutinySqlClient
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.Uuids
import org.ufoss.kotysa.test.uuidWithNullable
import org.ufoss.kotysa.test.uuidWithoutNullable
import java.util.*

abstract class MutinySelectUuidRepository<T : Uuids>(
    private val sqlClient: MutinySqlClient,
    private val table: T,
) : Repository {

    override fun init() {
        createTables()
            .chain { -> insertUuids() }
            .await().indefinitely()
    }

    override fun delete() {
        deleteAll()
            .await().indefinitely()
    }

    private fun createTables() = sqlClient createTableIfNotExists table

    private fun insertUuids() = sqlClient.insert(uuidWithNullable, uuidWithoutNullable)

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
