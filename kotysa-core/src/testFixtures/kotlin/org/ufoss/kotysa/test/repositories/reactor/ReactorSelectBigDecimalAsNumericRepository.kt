/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.reactor

import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.*
import java.math.BigDecimal

abstract class ReactorSelectBigDecimalAsNumericRepository<T : BigDecimalAsNumerics>(
        private val sqlClient: ReactorSqlClient,
        private val table: T,
) : Repository {

    override fun init() {
        createTable()
            .then(insert().then())
            .block()
    }

    override fun delete() {
        deleteAll()
            .block()
    }

    private fun createTable() = sqlClient createTable table

    fun insert() = sqlClient.insert(bigDecimalAsNumericWithNullable, bigDecimalAsNumericWithoutNullable)

    fun deleteAll() = sqlClient deleteAllFrom table

    fun selectAll() = sqlClient selectAllFrom table

    fun selectAllByBigDecimalNotNull(bigDecimal: BigDecimal) =
        (sqlClient selectFrom table
                where table.bigDecimalNotNull eq bigDecimal
                ).fetchAll()

    fun selectAllByBigDecimalNotNullNotEq(bigDecimal: BigDecimal) =
        (sqlClient selectFrom table
                where table.bigDecimalNotNull notEq bigDecimal
                ).fetchAll()

    fun selectAllByBigDecimalNotNullIn(values: Sequence<BigDecimal>) =
        (sqlClient selectFrom table
                where table.bigDecimalNotNull `in` values
                ).fetchAll()

    fun selectAllByBigDecimalNotNullInf(bigDecimal: BigDecimal) =
        (sqlClient selectFrom table
                where table.bigDecimalNotNull inf bigDecimal
                ).fetchAll()

    fun selectAllByBigDecimalNotNullInfOrEq(bigDecimal: BigDecimal) =
        (sqlClient selectFrom table
                where table.bigDecimalNotNull infOrEq bigDecimal
                ).fetchAll()

    fun selectAllByBigDecimalNotNullSup(bigDecimal: BigDecimal) =
        (sqlClient selectFrom table
                where table.bigDecimalNotNull sup bigDecimal
                ).fetchAll()

    fun selectAllByBigDecimalNotNullSupOrEq(bigDecimal: BigDecimal) =
        (sqlClient selectFrom table
                where table.bigDecimalNotNull supOrEq bigDecimal
                ).fetchAll()

    fun selectAllByBigDecimalNullable(bigDecimal: BigDecimal?) =
        (sqlClient selectFrom table
                where table.bigDecimalNullable eq bigDecimal
                ).fetchAll()

    fun selectAllByBigDecimalNullableNotEq(bigDecimal: BigDecimal?) =
        (sqlClient selectFrom table
                where table.bigDecimalNullable notEq bigDecimal
                ).fetchAll()

    fun selectAllByBigDecimalNullableInf(bigDecimal: BigDecimal) =
        (sqlClient selectFrom table
                where table.bigDecimalNullable inf bigDecimal
                ).fetchAll()

    fun selectAllByBigDecimalNullableInfOrEq(bigDecimal: BigDecimal) =
        (sqlClient selectFrom table
                where table.bigDecimalNullable infOrEq bigDecimal
                ).fetchAll()

    fun selectAllByBigDecimalNullableSup(bigDecimal: BigDecimal) =
        (sqlClient selectFrom table
                where table.bigDecimalNullable sup bigDecimal
                ).fetchAll()

    fun selectAllByBigDecimalNullableSupOrEq(bigDecimal: BigDecimal) =
        (sqlClient selectFrom table
                where table.bigDecimalNullable supOrEq bigDecimal
                ).fetchAll()
}
