/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import kotlinx.coroutines.runBlocking
import org.ufoss.kotysa.CoroutinesSqlClient
import org.ufoss.kotysa.test.*
import java.math.BigDecimal

abstract class CoroutinesSelectBigDecimalRepository<T : BigDecimals>(
        private val sqlClient: CoroutinesSqlClient,
        private val table: T,
) : Repository {

    override fun init() = runBlocking {
        createTable()
        insert()
    }

    override fun delete() = runBlocking<Unit> {
        deleteAll()
    }

    private suspend fun createTable() {
        sqlClient createTableIfNotExists table
    }

    suspend fun insert() {
        sqlClient.insert(bigDecimalWithNullable, bigDecimalWithoutNullable)
    }

    suspend fun deleteAll() = sqlClient deleteAllFrom table

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
