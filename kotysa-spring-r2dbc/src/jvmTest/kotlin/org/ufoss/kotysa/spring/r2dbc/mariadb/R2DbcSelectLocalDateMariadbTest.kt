/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.MariadbCoroutinesSqlClient
import org.ufoss.kotysa.MariadbReactorSqlClient
import org.ufoss.kotysa.test.MariadbLocalDates
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.localDateWithNullable
import org.ufoss.kotysa.test.localDateWithoutNullable
import java.time.LocalDate


class R2DbcSelectLocalDateMariadbTest : AbstractR2dbcMariadbTest<LocalDateRepositoryMariadbSelect>() {

    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        LocalDateRepositoryMariadbSelect(sqlClient)

    @Test
    fun `Verify selectAllByLocalDateNotNull finds localDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNull(LocalDate.of(2019, 11, 4)).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullNotEq finds localDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullNotEq(LocalDate.of(2019, 11, 4)).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullIn finds both`() {
        val seq = sequenceOf(
            localDateWithNullable.localDateNotNull,
            localDateWithoutNullable.localDateNotNull
        )
        assertThat(repository.selectAllByLocalDateNotNullIn(seq).toIterable())
            .hasSize(2)
            .containsExactlyInAnyOrder(localDateWithNullable, localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds localDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullBefore(LocalDate.of(2019, 11, 5)).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullBefore(LocalDate.of(2019, 11, 4)).toIterable())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds localDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullBeforeOrEq(LocalDate.of(2019, 11, 5)).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds localDateWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullBeforeOrEq(LocalDate.of(2019, 11, 4)).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds localDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullAfter(LocalDate.of(2019, 11, 5)).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullAfter(LocalDate.of(2019, 11, 6)).toIterable())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds localDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullAfterOrEq(LocalDate.of(2019, 11, 5)).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds localDateWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullAfterOrEq(LocalDate.of(2019, 11, 6)).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds localDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullable(LocalDate.of(2018, 11, 4)).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds localDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullable(null).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLocalDateNullableNotEq(LocalDate.of(2018, 11, 4)).toIterable())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds localDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullableNotEq(null).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds localDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullableBefore(LocalDate.of(2018, 11, 5)).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNullableBefore(LocalDate.of(2018, 11, 4)).toIterable())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds localDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullableBeforeOrEq(LocalDate.of(2018, 11, 5)).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds localDateWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNullableBeforeOrEq(LocalDate.of(2018, 11, 4)).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds localDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullableAfter(LocalDate.of(2018, 11, 3)).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNullableAfter(LocalDate.of(2018, 11, 4)).toIterable())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds localDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullableAfterOrEq(LocalDate.of(2018, 11, 3)).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds localDateWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNullableAfterOrEq(LocalDate.of(2018, 11, 4)).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }
}


class LocalDateRepositoryMariadbSelect(private val sqlClient: MariadbReactorSqlClient) : Repository {

    override fun init() {
        createTables()
            .then(insertLocalDates().then())
            .block()
    }

    override fun delete() {
        deleteAll()
            .block()
    }

    private fun createTables() = sqlClient createTable MariadbLocalDates

    private fun insertLocalDates() = sqlClient.insert(localDateWithNullable, localDateWithoutNullable)

    private fun deleteAll() = sqlClient deleteAllFrom MariadbLocalDates

    fun selectAllByLocalDateNotNull(localDate: LocalDate) =
        (sqlClient selectFrom MariadbLocalDates
                where MariadbLocalDates.localDateNotNull eq localDate
                ).fetchAll()

    fun selectAllByLocalDateNotNullNotEq(localDate: LocalDate) =
        (sqlClient selectFrom MariadbLocalDates
                where MariadbLocalDates.localDateNotNull notEq localDate
                ).fetchAll()

    fun selectAllByLocalDateNotNullIn(values: Sequence<LocalDate>) =
        (sqlClient selectFrom MariadbLocalDates
                where MariadbLocalDates.localDateNotNull `in` values
                ).fetchAll()

    fun selectAllByLocalDateNotNullBefore(localDate: LocalDate) =
        (sqlClient selectFrom MariadbLocalDates
                where MariadbLocalDates.localDateNotNull before localDate
                ).fetchAll()

    fun selectAllByLocalDateNotNullBeforeOrEq(localDate: LocalDate) =
        (sqlClient selectFrom MariadbLocalDates
                where MariadbLocalDates.localDateNotNull beforeOrEq localDate
                ).fetchAll()

    fun selectAllByLocalDateNotNullAfter(localDate: LocalDate) =
        (sqlClient selectFrom MariadbLocalDates
                where MariadbLocalDates.localDateNotNull after localDate
                ).fetchAll()

    fun selectAllByLocalDateNotNullAfterOrEq(localDate: LocalDate) =
        (sqlClient selectFrom MariadbLocalDates
                where MariadbLocalDates.localDateNotNull afterOrEq localDate
                ).fetchAll()

    fun selectAllByLocalDateNullable(localDate: LocalDate?) =
        (sqlClient selectFrom MariadbLocalDates
                where MariadbLocalDates.localDateNullable eq localDate
                ).fetchAll()

    fun selectAllByLocalDateNullableNotEq(localDate: LocalDate?) =
        (sqlClient selectFrom MariadbLocalDates
                where MariadbLocalDates.localDateNullable notEq localDate
                ).fetchAll()

    fun selectAllByLocalDateNullableBefore(localDate: LocalDate) =
        (sqlClient selectFrom MariadbLocalDates
                where MariadbLocalDates.localDateNullable before localDate
                ).fetchAll()

    fun selectAllByLocalDateNullableBeforeOrEq(localDate: LocalDate) =
        (sqlClient selectFrom MariadbLocalDates
                where MariadbLocalDates.localDateNullable beforeOrEq localDate
                ).fetchAll()

    fun selectAllByLocalDateNullableAfter(localDate: LocalDate) =
        (sqlClient selectFrom MariadbLocalDates
                where MariadbLocalDates.localDateNullable after localDate
                ).fetchAll()

    fun selectAllByLocalDateNullableAfterOrEq(localDate: LocalDate) =
        (sqlClient selectFrom MariadbLocalDates
                where MariadbLocalDates.localDateNullable afterOrEq localDate
                ).fetchAll()
}