/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import kotlinx.datetime.LocalDate
import org.ufoss.kotysa.Tables
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.ufoss.kotysa.test.*

class SqLiteSelectKotlinxLocalDateTest : AbstractSqLiteTest<KotlinxLocalDateRepositorySelect>() {

    override fun getRepository(sqLiteTables: Tables) =
            KotlinxLocalDateRepositorySelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectAllByLocalDateNotNull finds sqLiteKotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNull(LocalDate(2019, 11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteKotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullNotEq finds sqLiteKotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullNotEq(LocalDate(2019, 11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteKotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds sqLiteKotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullBefore(LocalDate(2019, 11, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteKotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullBefore(LocalDate(2019, 11, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds sqLiteKotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullBeforeOrEq(LocalDate(2019, 11, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteKotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds sqLiteKotlinxLocalDateWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullBeforeOrEq(LocalDate(2019, 11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteKotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds sqLiteKotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullAfter(LocalDate(2019, 11, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteKotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullAfter(LocalDate(2019, 11, 6)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds sqLiteKotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullAfterOrEq(LocalDate(2019, 11, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteKotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds sqLiteKotlinxLocalDateWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullAfterOrEq(LocalDate(2019, 11, 6)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteKotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds h2UuidWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullable(LocalDate(2018, 11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteKotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullable(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteKotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullableNotEq(LocalDate(2018, 11, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLocalDateNullableNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteKotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds sqLiteKotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullableBefore(LocalDate(2018, 11, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteKotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNullableBefore(LocalDate(2018, 11, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds sqLiteKotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullableBeforeOrEq(LocalDate(2018, 11, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteKotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds sqLiteKotlinxLocalDateWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNullableBeforeOrEq(LocalDate(2018, 11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteKotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds sqLiteKotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullableAfter(LocalDate(2018, 11, 3)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteKotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNullableAfter(LocalDate(2018, 11, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds sqLiteKotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullableAfterOrEq(LocalDate(2018, 11, 3)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteKotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds sqLiteKotlinxLocalDateWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNullableAfterOrEq(LocalDate(2018, 11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteKotlinxLocalDateWithNullable)
    }
}

class KotlinxLocalDateRepositorySelect(sqLiteOpenHelper: SQLiteOpenHelper, tables: Tables) : Repository {

    private val sqlClient = sqLiteOpenHelper.sqlClient(tables)

    override fun init() {
        createTables()
        insertLocalDates()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() =
            sqlClient.createTable<SqLiteKotlinxLocalDate>()

    private fun insertLocalDates() = sqlClient.insert(sqLiteKotlinxLocalDateWithNullable, sqLiteKotlinxLocalDateWithoutNullable)

    private fun deleteAll() = sqlClient.deleteAllFromTable<SqLiteKotlinxLocalDate>()

    fun selectAllByLocalDateNotNull(localDate: LocalDate) = sqlClient.select<SqLiteKotlinxLocalDate>()
            .where { it[SqLiteKotlinxLocalDate::localDateNotNull] eq localDate }
            .fetchAll()

    fun selectAllByLocalDateNotNullNotEq(localDate: LocalDate) = sqlClient.select<SqLiteKotlinxLocalDate>()
            .where { it[SqLiteKotlinxLocalDate::localDateNotNull] notEq localDate }
            .fetchAll()

    fun selectAllByLocalDateNotNullBefore(localDate: LocalDate) = sqlClient.select<SqLiteKotlinxLocalDate>()
            .where { it[SqLiteKotlinxLocalDate::localDateNotNull] before localDate }
            .fetchAll()

    fun selectAllByLocalDateNotNullBeforeOrEq(localDate: LocalDate) = sqlClient.select<SqLiteKotlinxLocalDate>()
            .where { it[SqLiteKotlinxLocalDate::localDateNotNull] beforeOrEq localDate }
            .fetchAll()

    fun selectAllByLocalDateNotNullAfter(localDate: LocalDate) = sqlClient.select<SqLiteKotlinxLocalDate>()
            .where { it[SqLiteKotlinxLocalDate::localDateNotNull] after localDate }
            .fetchAll()

    fun selectAllByLocalDateNotNullAfterOrEq(localDate: LocalDate) = sqlClient.select<SqLiteKotlinxLocalDate>()
            .where { it[SqLiteKotlinxLocalDate::localDateNotNull] afterOrEq localDate }
            .fetchAll()

    fun selectAllByLocalDateNullable(localDate: LocalDate?) = sqlClient.select<SqLiteKotlinxLocalDate>()
            .where { it[SqLiteKotlinxLocalDate::localDateNullable] eq localDate }
            .fetchAll()

    fun selectAllByLocalDateNullableNotEq(localDate: LocalDate?) = sqlClient.select<SqLiteKotlinxLocalDate>()
            .where { it[SqLiteKotlinxLocalDate::localDateNullable] notEq localDate }
            .fetchAll()

    fun selectAllByLocalDateNullableBefore(localDate: LocalDate) = sqlClient.select<SqLiteKotlinxLocalDate>()
            .where { it[SqLiteKotlinxLocalDate::localDateNullable] before localDate }
            .fetchAll()

    fun selectAllByLocalDateNullableBeforeOrEq(localDate: LocalDate) = sqlClient.select<SqLiteKotlinxLocalDate>()
            .where { it[SqLiteKotlinxLocalDate::localDateNullable] beforeOrEq localDate }
            .fetchAll()

    fun selectAllByLocalDateNullableAfter(localDate: LocalDate) = sqlClient.select<SqLiteKotlinxLocalDate>()
            .where { it[SqLiteKotlinxLocalDate::localDateNullable] after localDate }
            .fetchAll()

    fun selectAllByLocalDateNullableAfterOrEq(localDate: LocalDate) = sqlClient.select<SqLiteKotlinxLocalDate>()
            .where { it[SqLiteKotlinxLocalDate::localDateNullable] afterOrEq localDate }
            .fetchAll()
}
