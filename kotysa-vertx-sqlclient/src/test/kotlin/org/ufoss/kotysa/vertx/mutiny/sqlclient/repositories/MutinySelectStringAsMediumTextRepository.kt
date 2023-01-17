/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories

import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySqlClient
import org.ufoss.kotysa.test.MediumTexts
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.stringAsMediumTextNotNull
import org.ufoss.kotysa.test.stringAsMediumTextNullable

abstract class MutinySelectStringAsMediumTextRepository<T : MediumTexts>(
    private val sqlClient: MutinySqlClient,
    private val table: T,
) : Repository {

    override fun init() {
        createTables()
            .chain { -> insertTexts() }
            .await().indefinitely()
    }

    override fun delete() {
        deleteAll()
            .await().indefinitely()
    }

    private fun createTables() = sqlClient createTable table

    private fun insertTexts() = sqlClient.insert(stringAsMediumTextNotNull, stringAsMediumTextNullable)

    private fun deleteAll() = sqlClient deleteAllFrom table

    fun selectFirstOrNullByStringNotNull(value: String) =
        (sqlClient selectFrom table
                where table.stringNotNull eq value
                ).fetchFirst()

    fun selectFirstByStringNotNull(value: String) =
        (sqlClient selectFrom table
                where table.stringNotNull eq value
                ).fetchFirst()

    fun selectAllByStringNotNullNotEq(value: String) =
        (sqlClient selectFrom table
                where table.stringNotNull notEq value
                ).fetchAll()

    fun selectAllByStringNotNullIn(values: Sequence<String>) =
        (sqlClient selectFrom table
                where table.stringNotNull `in` values
                ).fetchAll()

    fun selectAllByStringNotNotNullContains(value: String) =
        (sqlClient selectFrom table
                where table.stringNotNull contains value
                ).fetchAll()

    fun selectAllByStringNotNotNullStartsWith(value: String) =
        (sqlClient selectFrom table
                where table.stringNotNull startsWith value
                ).fetchAll()

    fun selectAllByStringNotNotNullEndsWith(value: String) =
        (sqlClient selectFrom table
                where table.stringNotNull endsWith value
                ).fetchAll()

    fun selectAllByStringNotNullable(value: String?) =
        (sqlClient selectFrom table
                where table.stringNullable eq value
                ).fetchAll()

    fun selectAllByStringNotNullableNotEq(value: String?) =
        (sqlClient selectFrom table
                where table.stringNullable notEq value
                ).fetchAll()

    fun selectAllByStringNotNullableContains(value: String) =
        (sqlClient selectFrom table
                where table.stringNullable contains value
                ).fetchAll()

    fun selectAllByStringNotNullableStartsWith(value: String) =
        (sqlClient selectFrom table
                where table.stringNullable startsWith value
                ).fetchAll()

    fun selectAllByStringNotNullableEndsWith(value: String) =
        (sqlClient selectFrom table
                where table.stringNullable endsWith value
                ).fetchAll()
}
