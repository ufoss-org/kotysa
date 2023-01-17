/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories

import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySqlClient

abstract class MutinyInheritanceRepository<T : Inheriteds>(
    private val sqlClient: MutinySqlClient,
    private val table: T,
) : Repository {

    override fun init() {
        createTable()
            .chain { -> insert() }
            .await().indefinitely()
    }

    override fun delete() {
        deleteAll()
            .await().indefinitely()
    }

    private fun createTable() = sqlClient createTable table

    fun insert() = sqlClient insert inherited

    private fun deleteAll() = sqlClient deleteAllFrom table

    fun selectAll() = sqlClient selectAllFrom table

    fun selectInheritedById(id: String) =
        (sqlClient selectFrom table
                where table.id eq id
                ).fetchOne()

    fun <T : Entities<U>, U : Entity<String>> selectById(table: T, id: String) =
        (sqlClient selectFrom table
                where table.id eq id
                ).fetchOne()

    fun <T : Nameables<U>, U : Nameable> selectFirstByName(table: T, name: String) =
        (sqlClient selectFrom table
                where table.name eq name
                ).fetchFirst()

    fun <T : Entities<U>, U : Entity<String>> deleteById(table: T, id: String) =
        (sqlClient deleteFrom table
                where table.id eq id
                ).execute()
}
