/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import kotlinx.datetime.LocalDate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.spring.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.H2KotlinxLocalDates
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.kotlinxLocalDateWithNullable
import org.ufoss.kotysa.test.kotlinxLocalDateWithoutNullable


class R2DbcSelectKotlinxLocalDateH2Test : AbstractR2dbcH2Test<KotlinxLocalDateRepositoryH2Select>() {
    override val context = startContext<KotlinxLocalDateRepositoryH2Select>()
    override val repository = getContextRepository<KotlinxLocalDateRepositoryH2Select>()

    @Test
    fun `Verify selectAllByLocalDateNotNull finds kotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNull(LocalDate(2019, 11, 4)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullNotEq finds kotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullNotEq(LocalDate(2019, 11, 4)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullIn finds both`() {
        val seq = sequenceOf(
                kotlinxLocalDateWithNullable.localDateNotNull,
                kotlinxLocalDateWithoutNullable.localDateNotNull)
        assertThat(repository.selectAllByLocalDateNotNullIn(seq).toIterable())
                .hasSize(2)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable, kotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds kotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullBefore(LocalDate(2019, 11, 5)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullBefore(LocalDate(2019, 11, 4)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds kotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullBeforeOrEq(LocalDate(2019, 11, 5)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds kotlinxLocalDateWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullBeforeOrEq(LocalDate(2019, 11, 4)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds kotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullAfter(LocalDate(2019, 11, 5)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullAfter(LocalDate(2019, 11, 6)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds kotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullAfterOrEq(LocalDate(2019, 11, 5)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds kotlinxLocalDateWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullAfterOrEq(LocalDate(2019, 11, 6)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds h2UuidWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullable(LocalDate(2018, 11, 4)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullable(null).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullableNotEq(LocalDate(2018, 11, 4)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLocalDateNullableNotEq(null).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds kotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullableBefore(LocalDate(2018, 11, 5)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNullableBefore(LocalDate(2018, 11, 4)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds kotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullableBeforeOrEq(LocalDate(2018, 11, 5)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds kotlinxLocalDateWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNullableBeforeOrEq(LocalDate(2018, 11, 4)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds kotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullableAfter(LocalDate(2018, 11, 3)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNullableAfter(LocalDate(2018, 11, 4)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds kotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullableAfterOrEq(LocalDate(2018, 11, 3)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds kotlinxLocalDateWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNullableAfterOrEq(LocalDate(2018, 11, 4)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }
}


class KotlinxLocalDateRepositoryH2Select(private val sqlClient: ReactorSqlClient) : Repository {

    override fun init() {
        createTables()
                .then(insertLocalDates().then())
                .block()
    }

    override fun delete() {
        deleteAll()
                .block()
    }

    private fun createTables() = sqlClient createTable H2KotlinxLocalDates

    private fun insertLocalDates() = sqlClient.insert(kotlinxLocalDateWithNullable, kotlinxLocalDateWithoutNullable)

    private fun deleteAll() = sqlClient deleteAllFrom H2KotlinxLocalDates

    fun selectAllByLocalDateNotNull(localDate: LocalDate) =
            (sqlClient selectFrom H2KotlinxLocalDates
                    where H2KotlinxLocalDates.localDateNotNull eq localDate
                    ).fetchAll()

    fun selectAllByLocalDateNotNullNotEq(localDate: LocalDate) =
            (sqlClient selectFrom H2KotlinxLocalDates
                    where H2KotlinxLocalDates.localDateNotNull notEq localDate
                    ).fetchAll()

    fun selectAllByLocalDateNotNullIn(values: Sequence<LocalDate>) =
            (sqlClient selectFrom H2KotlinxLocalDates
                    where H2KotlinxLocalDates.localDateNotNull `in` values
                    ).fetchAll()

    fun selectAllByLocalDateNotNullBefore(localDate: LocalDate) =
            (sqlClient selectFrom H2KotlinxLocalDates
                    where H2KotlinxLocalDates.localDateNotNull before localDate
                    ).fetchAll()

    fun selectAllByLocalDateNotNullBeforeOrEq(localDate: LocalDate) =
            (sqlClient selectFrom H2KotlinxLocalDates
                    where H2KotlinxLocalDates.localDateNotNull beforeOrEq localDate
                    ).fetchAll()

    fun selectAllByLocalDateNotNullAfter(localDate: LocalDate) =
            (sqlClient selectFrom H2KotlinxLocalDates
                    where H2KotlinxLocalDates.localDateNotNull after localDate
                    ).fetchAll()

    fun selectAllByLocalDateNotNullAfterOrEq(localDate: LocalDate) =
            (sqlClient selectFrom H2KotlinxLocalDates
                    where H2KotlinxLocalDates.localDateNotNull afterOrEq localDate
                    ).fetchAll()

    fun selectAllByLocalDateNullable(localDate: LocalDate?) =
            (sqlClient selectFrom H2KotlinxLocalDates
                    where H2KotlinxLocalDates.localDateNullable eq localDate
                    ).fetchAll()

    fun selectAllByLocalDateNullableNotEq(localDate: LocalDate?) =
            (sqlClient selectFrom H2KotlinxLocalDates
                    where H2KotlinxLocalDates.localDateNullable notEq localDate
                    ).fetchAll()

    fun selectAllByLocalDateNullableBefore(localDate: LocalDate) =
            (sqlClient selectFrom H2KotlinxLocalDates
                    where H2KotlinxLocalDates.localDateNullable before localDate
                    ).fetchAll()

    fun selectAllByLocalDateNullableBeforeOrEq(localDate: LocalDate) =
            (sqlClient selectFrom H2KotlinxLocalDates
                    where H2KotlinxLocalDates.localDateNullable beforeOrEq localDate
                    ).fetchAll()

    fun selectAllByLocalDateNullableAfter(localDate: LocalDate) =
            (sqlClient selectFrom H2KotlinxLocalDates
                    where H2KotlinxLocalDates.localDateNullable after localDate
                    ).fetchAll()

    fun selectAllByLocalDateNullableAfterOrEq(localDate: LocalDate) =
            (sqlClient selectFrom H2KotlinxLocalDates
                    where H2KotlinxLocalDates.localDateNullable afterOrEq localDate
                    ).fetchAll()
}
