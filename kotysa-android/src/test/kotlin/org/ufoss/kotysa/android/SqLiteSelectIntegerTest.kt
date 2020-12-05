/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.ufoss.kotysa.Tables
import org.ufoss.kotysa.test.*

class SqLiteSelectIntegerTest : AbstractSqLiteTest<IntegerRepositorySelect>() {

    private val intWithNullable = IntEntity(
            org.ufoss.kotysa.test.intWithNullable.intNotNull,
            org.ufoss.kotysa.test.intWithNullable.intNullable,
            1
    )

    private val intWithoutNullable = IntEntity(
            org.ufoss.kotysa.test.intWithoutNullable.intNotNull,
            org.ufoss.kotysa.test.intWithoutNullable.intNullable,
            2
    )

    override fun getRepository(sqLiteTables: Tables) = IntegerRepositorySelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectAllByIntegerNotNull finds sqLiteIntegerWithNullable`() {
        assertThat(repository.selectAllByIntegerNotNull(10))
                .hasSize(1)
                .containsExactlyInAnyOrder(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntegerNotNullNotEq finds sqLiteIntegerWithoutNullable`() {
        assertThat(repository.selectAllByIntegerNotNullNotEq(10))
                .hasSize(1)
                .containsExactlyInAnyOrder(intWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntegerNotNullIn finds both`() {
        val seq = sequenceOf(intWithNullable.intNotNull, intWithoutNullable.intNotNull)
        assertThat(repository.selectAllByIntegerNotNullIn(seq))
                .hasSize(2)
                .containsExactlyInAnyOrder(intWithNullable, intWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntegerNotNullInf finds sqLiteIntegerWithNullable`() {
        assertThat(repository.selectAllByIntegerNotNullInf(11))
                .hasSize(1)
                .containsExactlyInAnyOrder(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntegerNotNullInf finds no results when equals`() {
        assertThat(repository.selectAllByIntegerNotNullInf(10))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntegerNotNullInfOrEq finds sqLiteIntegerWithNullable`() {
        assertThat(repository.selectAllByIntegerNotNullInfOrEq(11))
                .hasSize(1)
                .containsExactlyInAnyOrder(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntegerNotNullInfOrEq finds sqLiteIntegerWithNullable when equals`() {
        assertThat(repository.selectAllByIntegerNotNullInfOrEq(10))
                .hasSize(1)
                .containsExactlyInAnyOrder(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntegerNotNullSup finds sqLiteIntegerWithoutNullable`() {
        assertThat(repository.selectAllByIntegerNotNullSup(11))
                .hasSize(1)
                .containsExactlyInAnyOrder(intWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntegerNotNullSup finds no results when equals`() {
        assertThat(repository.selectAllByIntegerNotNullSup(12))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntegerNotNullSupOrEq finds sqLiteIntegerWithoutNullable`() {
        assertThat(repository.selectAllByIntegerNotNullSupOrEq(11))
                .hasSize(1)
                .containsExactlyInAnyOrder(intWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntegerNotNullSupOrEq finds sqLiteIntegerWithoutNullable when equals`() {
        assertThat(repository.selectAllByIntegerNotNullSupOrEq(12))
                .hasSize(1)
                .containsExactlyInAnyOrder(intWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntegerNullable finds h2UuidWithNullable`() {
        assertThat(repository.selectAllByIntegerNullable(6))
                .hasSize(1)
                .containsExactlyInAnyOrder(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntegerNullable finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByIntegerNullable(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(intWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntegerNullableNotEq finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByIntegerNullableNotEq(6))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntegerNullableNotEq finds no results`() {
        assertThat(repository.selectAllByIntegerNullableNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntegerNullableInf finds sqLiteIntegerWithNullable`() {
        assertThat(repository.selectAllByIntegerNullableInf(7))
                .hasSize(1)
                .containsExactlyInAnyOrder(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntegerNullableInf finds no results when equals`() {
        assertThat(repository.selectAllByIntegerNullableInf(6))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntegerNullableInfOrEq finds sqLiteIntegerWithNullable`() {
        assertThat(repository.selectAllByIntegerNullableInfOrEq(7))
                .hasSize(1)
                .containsExactlyInAnyOrder(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntegerNullableInfOrEq finds sqLiteIntegerWithNullable when equals`() {
        assertThat(repository.selectAllByIntegerNullableInfOrEq(6))
                .hasSize(1)
                .containsExactlyInAnyOrder(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntegerNullableSup finds sqLiteIntegerWithoutNullable`() {
        assertThat(repository.selectAllByIntegerNullableSup(5))
                .hasSize(1)
                .containsExactlyInAnyOrder(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntegerNullableSup finds no results when equals`() {
        assertThat(repository.selectAllByIntegerNullableSup(6))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntegerNullableSupOrEq finds sqLiteIntegerWithoutNullable`() {
        assertThat(repository.selectAllByIntegerNullableSupOrEq(5))
                .hasSize(1)
                .containsExactlyInAnyOrder(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntegerNullableSupOrEq finds sqLiteIntegerWithoutNullable when equals`() {
        assertThat(repository.selectAllByIntegerNullableSupOrEq(6))
                .hasSize(1)
                .containsExactlyInAnyOrder(intWithNullable)
    }
}

class IntegerRepositorySelect(sqLiteOpenHelper: SQLiteOpenHelper, tables: Tables) : Repository {

    private val sqlClient = sqLiteOpenHelper.sqlClient(tables)

    override fun init() {
        createTables()
        insertIntegers()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() {
        sqlClient createTable SQLITE_INT
    }

    private fun insertIntegers() {
        sqlClient.insert(intWithNullable, intWithoutNullable)
    }

    private fun deleteAll() = sqlClient deleteAllFrom SQLITE_INT

    fun selectAllByIntegerNotNull(int: Int) =
            (sqlClient selectFrom SQLITE_INT
                    where SQLITE_INT.intNotNull eq int
                    ).fetchAll()

    fun selectAllByIntegerNotNullNotEq(int: Int) =
            (sqlClient selectFrom SQLITE_INT
                    where SQLITE_INT.intNotNull notEq int
                    ).fetchAll()

    fun selectAllByIntegerNotNullIn(values: Sequence<Int>) =
            (sqlClient selectFrom SQLITE_INT
                    where SQLITE_INT.intNotNull `in` values
                    ).fetchAll()

    fun selectAllByIntegerNotNullInf(int: Int) =
            (sqlClient selectFrom SQLITE_INT
                    where SQLITE_INT.intNotNull inf int
                    ).fetchAll()

    fun selectAllByIntegerNotNullInfOrEq(int: Int) =
            (sqlClient selectFrom SQLITE_INT
                    where SQLITE_INT.intNotNull infOrEq int
                    ).fetchAll()

    fun selectAllByIntegerNotNullSup(int: Int) =
            (sqlClient selectFrom SQLITE_INT
                    where SQLITE_INT.intNotNull sup int
                    ).fetchAll()

    fun selectAllByIntegerNotNullSupOrEq(int: Int) =
            (sqlClient selectFrom SQLITE_INT
                    where SQLITE_INT.intNotNull supOrEq int
                    ).fetchAll()

    fun selectAllByIntegerNullable(int: Int?) =
            (sqlClient selectFrom SQLITE_INT
                    where SQLITE_INT.intNullable eq int
                    ).fetchAll()

    fun selectAllByIntegerNullableNotEq(int: Int?) =
            (sqlClient selectFrom SQLITE_INT
                    where SQLITE_INT.intNullable notEq int
                    ).fetchAll()

    fun selectAllByIntegerNullableInf(int: Int) =
            (sqlClient selectFrom SQLITE_INT
                    where SQLITE_INT.intNullable inf int
                    ).fetchAll()

    fun selectAllByIntegerNullableInfOrEq(int: Int) =
            (sqlClient selectFrom SQLITE_INT
                    where SQLITE_INT.intNullable infOrEq int
                    ).fetchAll()

    fun selectAllByIntegerNullableSup(int: Int) =
            (sqlClient selectFrom SQLITE_INT
                    where SQLITE_INT.intNullable sup int
                    ).fetchAll()

    fun selectAllByIntegerNullableSupOrEq(int: Int) =
            (sqlClient selectFrom SQLITE_INT
                    where SQLITE_INT.intNullable supOrEq int
                    ).fetchAll()
}
