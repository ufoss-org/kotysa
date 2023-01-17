/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.reactor

import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.Ints
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.intWithNullable
import org.ufoss.kotysa.test.intWithoutNullable

abstract class ReactorSelectIntRepository<T : Ints>(
    private val sqlClient: ReactorSqlClient,
    private val table: T,
) : Repository {

    override fun init() {
        createTable()
            .then(insert())
            .block()
    }

    override fun delete() {
        deleteAll()
            .block()
    }

    private fun createTable() = sqlClient createTable table

    fun insert() = sqlClient.insert(intWithNullable, intWithoutNullable)

    fun deleteAll() = sqlClient deleteAllFrom table

    fun selectAll() = sqlClient selectAllFrom table

    fun selectAllByIntNotNull(int: Int) =
        (sqlClient selectFrom table
                where table.intNotNull eq int
                ).fetchAll()

    fun selectAllByIntNotNullNotEq(int: Int) =
        (sqlClient selectFrom table
                where table.intNotNull notEq int
                ).fetchAll()

    fun selectAllByIntNotNullIn(values: Sequence<Int>) =
        (sqlClient selectFrom table
                where table.intNotNull `in` values
                ).fetchAll()

    fun selectAllByIntNotNullInf(int: Int) =
        (sqlClient selectFrom table
                where table.intNotNull inf int
                ).fetchAll()

    fun selectAllByIntNotNullInfOrEq(int: Int) =
        (sqlClient selectFrom table
                where table.intNotNull infOrEq int
                ).fetchAll()

    fun selectAllByIntNotNullSup(int: Int) =
        (sqlClient selectFrom table
                where table.intNotNull sup int
                ).fetchAll()

    fun selectAllByIntNotNullSupOrEq(int: Int) =
        (sqlClient selectFrom table
                where table.intNotNull supOrEq int
                ).fetchAll()

    fun selectAllByIntNullable(int: Int?) =
        (sqlClient selectFrom table
                where table.intNullable eq int
                ).fetchAll()

    fun selectAllByIntNullableNotEq(int: Int?) =
        (sqlClient selectFrom table
                where table.intNullable notEq int
                ).fetchAll()

    fun selectAllByIntNullableInf(int: Int) =
        (sqlClient selectFrom table
                where table.intNullable inf int
                ).fetchAll()

    fun selectAllByIntNullableInfOrEq(int: Int) =
        (sqlClient selectFrom table
                where table.intNullable infOrEq int
                ).fetchAll()

    fun selectAllByIntNullableSup(int: Int) =
        (sqlClient selectFrom table
                where table.intNullable sup int
                ).fetchAll()

    fun selectAllByIntNullableSupOrEq(int: Int) =
        (sqlClient selectFrom table
                where table.intNullable supOrEq int
                ).fetchAll()
}
