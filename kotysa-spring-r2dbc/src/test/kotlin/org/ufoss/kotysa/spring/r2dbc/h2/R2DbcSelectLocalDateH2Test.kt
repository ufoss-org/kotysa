/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.spring.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.H2_LOCAL_DATE
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.localDateWithNullable
import org.ufoss.kotysa.test.localDateWithoutNullable
import java.time.LocalDate


class R2DbcSelectLocalDateH2Test : AbstractR2dbcH2Test<LocalDateRepositoryH2Select>() {
    override val context = startContext<LocalDateRepositoryH2Select>()
    override val repository = getContextRepository<LocalDateRepositoryH2Select>()

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
                localDateWithoutNullable.localDateNotNull)
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
    fun `Verify selectAllByLocalDateNullable finds h2UuidWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullable(LocalDate.of(2018, 11, 4)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullable(null).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullableNotEq(LocalDate.of(2018, 11, 4)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds no results`() {
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


class LocalDateRepositoryH2Select(private val sqlClient: ReactorSqlClient) : Repository {

    override fun init() {
        createTables()
                .then(insertLocalDates().then())
                .block()
    }

    override fun delete() {
        deleteAll()
                .block()
    }

    private fun createTables() = sqlClient createTable H2_LOCAL_DATE

    private fun insertLocalDates() = sqlClient.insert(localDateWithNullable, localDateWithoutNullable)

    private fun deleteAll() = sqlClient deleteAllFrom H2_LOCAL_DATE

    fun selectAllByLocalDateNotNull(localDate: LocalDate) =
            (sqlClient selectFrom H2_LOCAL_DATE
                    where H2_LOCAL_DATE.localDateNotNull eq localDate
                    ).fetchAll()

    fun selectAllByLocalDateNotNullNotEq(localDate: LocalDate) =
            (sqlClient selectFrom H2_LOCAL_DATE
                    where H2_LOCAL_DATE.localDateNotNull notEq localDate
                    ).fetchAll()

    fun selectAllByLocalDateNotNullIn(values: Sequence<LocalDate>) =
            (sqlClient selectFrom H2_LOCAL_DATE
                    where H2_LOCAL_DATE.localDateNotNull `in` values
                    ).fetchAll()

    fun selectAllByLocalDateNotNullBefore(localDate: LocalDate) =
            (sqlClient selectFrom H2_LOCAL_DATE
                    where H2_LOCAL_DATE.localDateNotNull before localDate
                    ).fetchAll()

    fun selectAllByLocalDateNotNullBeforeOrEq(localDate: LocalDate) =
            (sqlClient selectFrom H2_LOCAL_DATE
                    where H2_LOCAL_DATE.localDateNotNull beforeOrEq localDate
                    ).fetchAll()

    fun selectAllByLocalDateNotNullAfter(localDate: LocalDate) =
            (sqlClient selectFrom H2_LOCAL_DATE
                    where H2_LOCAL_DATE.localDateNotNull after localDate
                    ).fetchAll()

    fun selectAllByLocalDateNotNullAfterOrEq(localDate: LocalDate) =
            (sqlClient selectFrom H2_LOCAL_DATE
                    where H2_LOCAL_DATE.localDateNotNull afterOrEq localDate
                    ).fetchAll()

    fun selectAllByLocalDateNullable(localDate: LocalDate?) =
            (sqlClient selectFrom H2_LOCAL_DATE
                    where H2_LOCAL_DATE.localDateNullable eq localDate
                    ).fetchAll()

    fun selectAllByLocalDateNullableNotEq(localDate: LocalDate?) =
            (sqlClient selectFrom H2_LOCAL_DATE
                    where H2_LOCAL_DATE.localDateNullable notEq localDate
                    ).fetchAll()

    fun selectAllByLocalDateNullableBefore(localDate: LocalDate) =
            (sqlClient selectFrom H2_LOCAL_DATE
                    where H2_LOCAL_DATE.localDateNullable before localDate
                    ).fetchAll()

    fun selectAllByLocalDateNullableBeforeOrEq(localDate: LocalDate) =
            (sqlClient selectFrom H2_LOCAL_DATE
                    where H2_LOCAL_DATE.localDateNullable beforeOrEq localDate
                    ).fetchAll()

    fun selectAllByLocalDateNullableAfter(localDate: LocalDate) =
            (sqlClient selectFrom H2_LOCAL_DATE
                    where H2_LOCAL_DATE.localDateNullable after localDate
                    ).fetchAll()

    fun selectAllByLocalDateNullableAfterOrEq(localDate: LocalDate) =
            (sqlClient selectFrom H2_LOCAL_DATE
                    where H2_LOCAL_DATE.localDateNullable afterOrEq localDate
                    ).fetchAll()
}
