/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.ufoss.kotysa.Tables
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.SqLiteInteger
import org.ufoss.kotysa.test.sqLiteIntegerWithNullable
import org.ufoss.kotysa.test.sqLiteIntegerWithoutNullable

class SqLiteSelectIntegerTest : AbstractSqLiteTest<IntegerRepositorySelect>() {

    private val sqLiteIntegerWithNullable = SqLiteInteger(
        org.ufoss.kotysa.test.sqLiteIntegerWithNullable.integerNotNull,
        org.ufoss.kotysa.test.sqLiteIntegerWithNullable.integerNullable,
        1
    )

    private val sqLiteIntegerWithoutNullable = SqLiteInteger(
        org.ufoss.kotysa.test.sqLiteIntegerWithoutNullable.integerNotNull,
        org.ufoss.kotysa.test.sqLiteIntegerWithoutNullable.integerNullable,
        2
    )

    override fun getRepository(sqLiteTables: Tables) = IntegerRepositorySelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectAllByIntegerNotNull finds sqLiteIntegerWithNullable`() {
        assertThat(repository.selectAllByIntegerNotNull(10))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteIntegerWithNullable)
    }

    @Test
    fun `Verify selectAllByIntegerNotNullNotEq finds sqLiteIntegerWithoutNullable`() {
        assertThat(repository.selectAllByIntegerNotNullNotEq(10))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteIntegerWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntegerNotNullInf finds sqLiteIntegerWithNullable`() {
        assertThat(repository.selectAllByIntegerNotNullInf(11))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteIntegerWithNullable)
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
            .containsExactlyInAnyOrder(sqLiteIntegerWithNullable)
    }

    @Test
    fun `Verify selectAllByIntegerNotNullInfOrEq finds sqLiteIntegerWithNullable when equals`() {
        assertThat(repository.selectAllByIntegerNotNullInfOrEq(10))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteIntegerWithNullable)
    }

    @Test
    fun `Verify selectAllByIntegerNotNullSup finds sqLiteIntegerWithoutNullable`() {
        assertThat(repository.selectAllByIntegerNotNullSup(11))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteIntegerWithoutNullable)
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
            .containsExactlyInAnyOrder(sqLiteIntegerWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntegerNotNullSupOrEq finds sqLiteIntegerWithoutNullable when equals`() {
        assertThat(repository.selectAllByIntegerNotNullSupOrEq(12))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteIntegerWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntegerNullable finds h2UuidWithNullable`() {
        assertThat(repository.selectAllByIntegerNullable(6))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteIntegerWithNullable)
    }

    @Test
    fun `Verify selectAllByIntegerNullable finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByIntegerNullable(null))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteIntegerWithoutNullable)
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
            .containsExactlyInAnyOrder(sqLiteIntegerWithNullable)
    }

    @Test
    fun `Verify selectAllByIntegerNullableInf finds sqLiteIntegerWithNullable`() {
        assertThat(repository.selectAllByIntegerNullableInf(7))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteIntegerWithNullable)
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
            .containsExactlyInAnyOrder(sqLiteIntegerWithNullable)
    }

    @Test
    fun `Verify selectAllByIntegerNullableInfOrEq finds sqLiteIntegerWithNullable when equals`() {
        assertThat(repository.selectAllByIntegerNullableInfOrEq(6))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteIntegerWithNullable)
    }

    @Test
    fun `Verify selectAllByIntegerNullableSup finds sqLiteIntegerWithoutNullable`() {
        assertThat(repository.selectAllByIntegerNullableSup(5))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteIntegerWithNullable)
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
            .containsExactlyInAnyOrder(sqLiteIntegerWithNullable)
    }

    @Test
    fun `Verify selectAllByIntegerNullableSupOrEq finds sqLiteIntegerWithoutNullable when equals`() {
        assertThat(repository.selectAllByIntegerNullableSupOrEq(6))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteIntegerWithNullable)
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
        sqlClient.createTable<SqLiteInteger>()
    }

    private fun insertIntegers() {
        sqlClient.insert(sqLiteIntegerWithNullable, sqLiteIntegerWithoutNullable)
    }

    private fun deleteAll() = sqlClient.deleteAllFromTable<SqLiteInteger>()

    fun selectAllByIntegerNotNull(int: Int) = sqlClient.select<SqLiteInteger>()
        .where { it[SqLiteInteger::integerNotNull] eq int }
        .fetchAll()

    fun selectAllByIntegerNotNullNotEq(int: Int) =
        sqlClient.select<SqLiteInteger>()
            .where { it[SqLiteInteger::integerNotNull] notEq int }
            .fetchAll()

    fun selectAllByIntegerNotNullInf(int: Int) =
        sqlClient.select<SqLiteInteger>()
            .where { it[SqLiteInteger::integerNotNull] inf int }
            .fetchAll()

    fun selectAllByIntegerNotNullInfOrEq(int: Int) =
        sqlClient.select<SqLiteInteger>()
            .where { it[SqLiteInteger::integerNotNull] infOrEq int }
            .fetchAll()

    fun selectAllByIntegerNotNullSup(int: Int) =
        sqlClient.select<SqLiteInteger>()
            .where { it[SqLiteInteger::integerNotNull] sup int }
            .fetchAll()

    fun selectAllByIntegerNotNullSupOrEq(int: Int) =
        sqlClient.select<SqLiteInteger>()
            .where { it[SqLiteInteger::integerNotNull] supOrEq int }
            .fetchAll()

    fun selectAllByIntegerNullable(int: Int?) =
        sqlClient.select<SqLiteInteger>()
            .where { it[SqLiteInteger::integerNullable] eq int }
            .fetchAll()

    fun selectAllByIntegerNullableNotEq(int: Int?) =
        sqlClient.select<SqLiteInteger>()
            .where { it[SqLiteInteger::integerNullable] notEq int }
            .fetchAll()

    fun selectAllByIntegerNullableInf(int: Int) =
        sqlClient.select<SqLiteInteger>()
            .where { it[SqLiteInteger::integerNullable] inf int }
            .fetchAll()

    fun selectAllByIntegerNullableInfOrEq(int: Int) =
        sqlClient.select<SqLiteInteger>()
            .where { it[SqLiteInteger::integerNullable] infOrEq int }
            .fetchAll()

    fun selectAllByIntegerNullableSup(int: Int) =
        sqlClient.select<SqLiteInteger>()
            .where { it[SqLiteInteger::integerNullable] sup int }
            .fetchAll()

    fun selectAllByIntegerNullableSupOrEq(int: Int) =
        sqlClient.select<SqLiteInteger>()
            .where { it[SqLiteInteger::integerNullable] supOrEq int }
            .fetchAll()
}
