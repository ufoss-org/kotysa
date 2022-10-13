/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import org.ufoss.kotysa.SqlClient
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.TinyTexts
import org.ufoss.kotysa.test.stringAsTinyTextNotNull
import org.ufoss.kotysa.test.stringAsTinyTextNullable

abstract class SelectStringAsTinyTextRepository<T : TinyTexts>(
    private val sqlClient: SqlClient,
    private val table: T,
) : Repository {

    override fun init() {
        createTables()
        insertTexts()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() {
        sqlClient createTable table
    }

    private fun insertTexts() {
        sqlClient.insert(stringAsTinyTextNotNull, stringAsTinyTextNullable)
    }

    private fun deleteAll() = sqlClient deleteAllFrom table

    fun selectFirstOrNullByStringNotNull(value: String) =
        (sqlClient selectFrom table
                where table.stringNotNull eq value
                ).fetchFirstOrNull()

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
