/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.ufoss.kotysa.Tables
import org.ufoss.kotysa.test.SqLiteOffsetDateTime
import org.ufoss.kotysa.test.sqLiteOffsetDateTimeWithNullable
import org.ufoss.kotysa.test.sqLiteOffsetDateTimeWithoutNullable
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.ufoss.kotysa.test.Repository
import java.time.OffsetDateTime
import java.time.ZoneOffset

class SqLiteSelectOffsetDateTimeTest : AbstractSqLiteTest<OffsetDateTimeRepositorySelect>() {

    override fun getRepository(sqLiteTables: Tables) =
            OffsetDateTimeRepositorySelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNull finds sqLiteOffsetDateTimeWithNullable`() {
        assertThat(repository.selectAllByOffsetDateTimeNotNull(OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteOffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNull finds sqLiteOffsetDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByOffsetDateTimeNotNull(OffsetDateTime.of(2019, 11, 6, 0, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteOffsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullNotEq finds sqLiteOffsetDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByOffsetDateTimeNotNullNotEq(OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteOffsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullBefore finds sqLiteOffsetDateTimeWithNullable`() {
        assertThat(repository.selectAllByOffsetDateTimeNotNullBefore(OffsetDateTime.of(2019, 11, 4, 12, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteOffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByOffsetDateTimeNotNullBefore(OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullBeforeOrEq finds sqLiteOffsetDateTimeWithNullable`() {
        assertThat(repository.selectAllByOffsetDateTimeNotNullBeforeOrEq(OffsetDateTime.of(2019, 11, 4, 12, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteOffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullBeforeOrEq finds sqLiteOffsetDateTimeWithNullable when equals`() {
        assertThat(repository.selectAllByOffsetDateTimeNotNullBeforeOrEq(OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteOffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullAfter finds sqLiteOffsetDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByOffsetDateTimeNotNullAfter(OffsetDateTime.of(2019, 11, 5, 12, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteOffsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByOffsetDateTimeNotNullAfter(OffsetDateTime.of(2019, 11, 6, 0, 0, 0, 0, ZoneOffset.UTC)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullAfterOrEq finds sqLiteOffsetDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByOffsetDateTimeNotNullAfterOrEq(OffsetDateTime.of(2019, 11, 5, 12, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteOffsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullAfterOrEq finds sqLiteOffsetDateTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByOffsetDateTimeNotNullAfterOrEq(OffsetDateTime.of(2019, 11, 6, 0, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteOffsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullable finds sqLiteOffsetDateTimeWithNullable`() {
        assertThat(repository.selectAllByOffsetDateTimeNullable(OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteOffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullable finds sqLiteOffsetDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByOffsetDateTimeNullable(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteOffsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableNotEq finds no result`() {
        assertThat(repository.selectAllByOffsetDateTimeNullableNotEq(OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableNotEq finds no results`() {
        assertThat(repository.selectAllByOffsetDateTimeNullableNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteOffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableBefore finds sqLiteOffsetDateTimeWithNullable`() {
        assertThat(repository.selectAllByOffsetDateTimeNullableBefore(OffsetDateTime.of(2018, 11, 4, 12, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteOffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByOffsetDateTimeNullableBefore(OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableBeforeOrEq finds sqLiteOffsetDateTimeWithNullable`() {
        assertThat(repository.selectAllByOffsetDateTimeNullableBeforeOrEq(OffsetDateTime.of(2018, 11, 5, 12, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteOffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableBeforeOrEq finds sqLiteOffsetDateTimeWithNullable when equals`() {
        assertThat(repository.selectAllByOffsetDateTimeNullableBeforeOrEq(OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteOffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableAfter finds sqLiteOffsetDateTimeWithNullable`() {
        assertThat(repository.selectAllByOffsetDateTimeNullableAfter(OffsetDateTime.of(2018, 11, 3, 12, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteOffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByOffsetDateTimeNullableAfter(OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableAfterOrEq finds sqLiteOffsetDateTimeWithNullable`() {
        assertThat(repository.selectAllByOffsetDateTimeNullableAfterOrEq(OffsetDateTime.of(2018, 11, 3, 12, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteOffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableAfterOrEq finds sqLiteOffsetDateTimeWithNullable when equals`() {
        assertThat(repository.selectAllByOffsetDateTimeNullableAfterOrEq(OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(sqLiteOffsetDateTimeWithNullable)
    }
}

class OffsetDateTimeRepositorySelect(sqLiteOpenHelper: SQLiteOpenHelper, tables: Tables) : Repository {

    private val sqlClient = sqLiteOpenHelper.sqlClient(tables)

    override fun init() {
        createTables()
        insertOffsetDateTimes()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() =
            sqlClient.createTable<SqLiteOffsetDateTime>()

    private fun insertOffsetDateTimes() = sqlClient.insert(sqLiteOffsetDateTimeWithNullable, sqLiteOffsetDateTimeWithoutNullable)

    private fun deleteAll() = sqlClient.deleteAllFromTable<SqLiteOffsetDateTime>()

    fun selectAllByOffsetDateTimeNotNull(offsetDateTime: OffsetDateTime) = sqlClient.select<SqLiteOffsetDateTime>()
            .where { it[SqLiteOffsetDateTime::offsetDateTimeNotNull] eq offsetDateTime }
            .fetchAll()

    fun selectAllByOffsetDateTimeNotNullNotEq(offsetDateTime: OffsetDateTime) = sqlClient.select<SqLiteOffsetDateTime>()
            .where { it[SqLiteOffsetDateTime::offsetDateTimeNotNull] notEq offsetDateTime }
            .fetchAll()

    fun selectAllByOffsetDateTimeNotNullBefore(offsetDateTime: OffsetDateTime) = sqlClient.select<SqLiteOffsetDateTime>()
            .where { it[SqLiteOffsetDateTime::offsetDateTimeNotNull] before offsetDateTime }
            .fetchAll()

    fun selectAllByOffsetDateTimeNotNullBeforeOrEq(offsetDateTime: OffsetDateTime) = sqlClient.select<SqLiteOffsetDateTime>()
            .where { it[SqLiteOffsetDateTime::offsetDateTimeNotNull] beforeOrEq offsetDateTime }
            .fetchAll()

    fun selectAllByOffsetDateTimeNotNullAfter(offsetDateTime: OffsetDateTime) = sqlClient.select<SqLiteOffsetDateTime>()
            .where { it[SqLiteOffsetDateTime::offsetDateTimeNotNull] after offsetDateTime }
            .fetchAll()

    fun selectAllByOffsetDateTimeNotNullAfterOrEq(offsetDateTime: OffsetDateTime) = sqlClient.select<SqLiteOffsetDateTime>()
            .where { it[SqLiteOffsetDateTime::offsetDateTimeNotNull] afterOrEq offsetDateTime }
            .fetchAll()

    fun selectAllByOffsetDateTimeNullable(offsetDateTime: OffsetDateTime?) = sqlClient.select<SqLiteOffsetDateTime>()
            .where { it[SqLiteOffsetDateTime::offsetDateTimeNullable] eq offsetDateTime }
            .fetchAll()

    fun selectAllByOffsetDateTimeNullableNotEq(offsetDateTime: OffsetDateTime?) = sqlClient.select<SqLiteOffsetDateTime>()
            .where { it[SqLiteOffsetDateTime::offsetDateTimeNullable] notEq offsetDateTime }
            .fetchAll()

    fun selectAllByOffsetDateTimeNullableBefore(offsetDateTime: OffsetDateTime) = sqlClient.select<SqLiteOffsetDateTime>()
            .where { it[SqLiteOffsetDateTime::offsetDateTimeNullable] before offsetDateTime }
            .fetchAll()

    fun selectAllByOffsetDateTimeNullableBeforeOrEq(offsetDateTime: OffsetDateTime) = sqlClient.select<SqLiteOffsetDateTime>()
            .where { it[SqLiteOffsetDateTime::offsetDateTimeNullable] beforeOrEq offsetDateTime }
            .fetchAll()

    fun selectAllByOffsetDateTimeNullableAfter(offsetDateTime: OffsetDateTime) = sqlClient.select<SqLiteOffsetDateTime>()
            .where { it[SqLiteOffsetDateTime::offsetDateTimeNullable] after offsetDateTime }
            .fetchAll()

    fun selectAllByOffsetDateTimeNullableAfterOrEq(offsetDateTime: OffsetDateTime) = sqlClient.select<SqLiteOffsetDateTime>()
            .where { it[SqLiteOffsetDateTime::offsetDateTimeNullable] afterOrEq offsetDateTime }
            .fetchAll()
}
