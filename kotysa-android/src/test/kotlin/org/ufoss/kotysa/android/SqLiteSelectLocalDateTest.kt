/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android
/*
import android.database.sqlite.SQLiteOpenHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.ufoss.kotysa.Tables
import org.ufoss.kotysa.test.*
import java.time.LocalDate

class SqLiteSelectLocalDateTest : AbstractSqLiteTest<LocalDateRepositorySelect>() {

    override fun getRepository(sqLiteTables: Tables) = LocalDateRepositorySelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectAllByLocalDateNotNull finds SqLiteLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNull(LocalDate.of(2019, 11, 4)))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullNotEq finds SqLiteLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullNotEq(LocalDate.of(2019, 11, 4)))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullIn finds both`() {
        val seq = sequenceOf(
                sqLiteLocalDateWithNullable.localDateNotNull,
                sqLiteLocalDateWithoutNullable.localDateNotNull)
        assertThat(repository.selectAllByLocalDateNotNullIn(seq))
                .hasSize(2)
                .containsExactlyInAnyOrder(sqLiteLocalDateWithNullable, sqLiteLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds SqLiteLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullBefore(LocalDate.of(2019, 11, 5)))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullBefore(LocalDate.of(2019, 11, 4)))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds SqLiteLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullBeforeOrEq(LocalDate.of(2019, 11, 5)))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds SqLiteLocalDateWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullBeforeOrEq(LocalDate.of(2019, 11, 4)))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds SqLiteLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullAfter(LocalDate.of(2019, 11, 5)))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullAfter(LocalDate.of(2019, 11, 6)))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds SqLiteLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullAfterOrEq(LocalDate.of(2019, 11, 5)))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds SqLiteLocalDateWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullAfterOrEq(LocalDate.of(2019, 11, 6)))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds h2UuidWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullable(LocalDate.of(2018, 11, 4)))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullable(null))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullableNotEq(LocalDate.of(2018, 11, 4)))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLocalDateNullableNotEq(null))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds SqLiteLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullableBefore(LocalDate.of(2018, 11, 5)))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNullableBefore(LocalDate.of(2018, 11, 4)))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds SqLiteLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullableBeforeOrEq(LocalDate.of(2018, 11, 5)))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds SqLiteLocalDateWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNullableBeforeOrEq(LocalDate.of(2018, 11, 4)))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds SqLiteLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullableAfter(LocalDate.of(2018, 11, 3)))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNullableAfter(LocalDate.of(2018, 11, 4)))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds SqLiteLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullableAfterOrEq(LocalDate.of(2018, 11, 3)))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds SqLiteLocalDateWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNullableAfterOrEq(LocalDate.of(2018, 11, 4)))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteLocalDateWithNullable)
    }
}

class LocalDateRepositorySelect(sqLiteOpenHelper: SQLiteOpenHelper, tables: Tables) : Repository {

    private val sqlClient = sqLiteOpenHelper.sqlClient(tables)

    override fun init() {
        createTables()
        insertLocalDates()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() {
        sqlClient.createTable<SqLiteLocalDate>()
    }

    private fun insertLocalDates() {
        sqlClient.insert(sqLiteLocalDateWithNullable, sqLiteLocalDateWithoutNullable)
    }

    private fun deleteAll() = sqlClient.deleteAllFromTable<SqLiteLocalDate>()

    fun selectAllByLocalDateNotNull(localDate: LocalDate) =
        sqlClient.select<SqLiteLocalDate>()
            .where { it[SqLiteLocalDate::localDateNotNull] eq localDate }
            .fetchAll()

    fun selectAllByLocalDateNotNullNotEq(localDate: LocalDate) =
        sqlClient.select<SqLiteLocalDate>()
            .where { it[SqLiteLocalDate::localDateNotNull] notEq localDate }
            .fetchAll()

    fun selectAllByLocalDateNotNullIn(values: Sequence<LocalDate>) =
            sqlClient.select<SqLiteLocalDate>()
                    .where { it[SqLiteLocalDate::localDateNotNull] `in` values }
                    .fetchAll()

    fun selectAllByLocalDateNotNullBefore(localDate: LocalDate) =
        sqlClient.select<SqLiteLocalDate>()
            .where { it[SqLiteLocalDate::localDateNotNull] before localDate }
            .fetchAll()

    fun selectAllByLocalDateNotNullBeforeOrEq(localDate: LocalDate) =
        sqlClient.select<SqLiteLocalDate>()
            .where { it[SqLiteLocalDate::localDateNotNull] beforeOrEq localDate }
            .fetchAll()

    fun selectAllByLocalDateNotNullAfter(localDate: LocalDate) =
        sqlClient.select<SqLiteLocalDate>()
            .where { it[SqLiteLocalDate::localDateNotNull] after localDate }
            .fetchAll()

    fun selectAllByLocalDateNotNullAfterOrEq(localDate: LocalDate) =
        sqlClient.select<SqLiteLocalDate>()
            .where { it[SqLiteLocalDate::localDateNotNull] afterOrEq localDate }
            .fetchAll()

    fun selectAllByLocalDateNullable(localDate: LocalDate?) =
        sqlClient.select<SqLiteLocalDate>()
            .where { it[SqLiteLocalDate::localDateNullable] eq localDate }
            .fetchAll()

    fun selectAllByLocalDateNullableNotEq(localDate: LocalDate?) =
        sqlClient.select<SqLiteLocalDate>()
            .where { it[SqLiteLocalDate::localDateNullable] notEq localDate }
            .fetchAll()

    fun selectAllByLocalDateNullableBefore(localDate: LocalDate) =
        sqlClient.select<SqLiteLocalDate>()
            .where { it[SqLiteLocalDate::localDateNullable] before localDate }
            .fetchAll()

    fun selectAllByLocalDateNullableBeforeOrEq(localDate: LocalDate) =
        sqlClient.select<SqLiteLocalDate>()
            .where { it[SqLiteLocalDate::localDateNullable] beforeOrEq localDate }
            .fetchAll()

    fun selectAllByLocalDateNullableAfter(localDate: LocalDate) =
        sqlClient.select<SqLiteLocalDate>()
            .where { it[SqLiteLocalDate::localDateNullable] after localDate }
            .fetchAll()

    fun selectAllByLocalDateNullableAfterOrEq(localDate: LocalDate) =
        sqlClient.select<SqLiteLocalDate>()
            .where { it[SqLiteLocalDate::localDateNullable] afterOrEq localDate }
            .fetchAll()
}*/
