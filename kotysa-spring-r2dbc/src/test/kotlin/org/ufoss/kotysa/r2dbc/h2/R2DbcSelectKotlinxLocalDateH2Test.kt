/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import kotlinx.datetime.LocalDate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.H2KotlinxLocalDate
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.h2KotlinxLocalDateWithNullable
import org.ufoss.kotysa.test.h2KotlinxLocalDateWithoutNullable


class R2DbcSelectKotlinxLocalDateH2Test : AbstractR2dbcH2Test<KotlinxLocalDateRepositoryH2Select>() {
    override val context = startContext<KotlinxLocalDateRepositoryH2Select>()

    override val repository = getContextRepository<KotlinxLocalDateRepositoryH2Select>()

    @Test
    fun `Verify selectAllByLocalDateNotNull finds h2KotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNull(LocalDate(2019, 11, 4)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2KotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullNotEq finds h2KotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullNotEq(LocalDate(2019, 11, 4)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2KotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds h2KotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullBefore(LocalDate(2019, 11, 5)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2KotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullBefore(LocalDate(2019, 11, 4)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds h2KotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullBeforeOrEq(LocalDate(2019, 11, 5)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2KotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds h2KotlinxLocalDateWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullBeforeOrEq(LocalDate(2019, 11, 4)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2KotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds h2KotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullAfter(LocalDate(2019, 11, 5)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2KotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullAfter(LocalDate(2019, 11, 6)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds h2KotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullAfterOrEq(LocalDate(2019, 11, 5)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2KotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds h2KotlinxLocalDateWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullAfterOrEq(LocalDate(2019, 11, 6)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2KotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds h2UuidWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullable(LocalDate(2018, 11, 4)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2KotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullable(null).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2KotlinxLocalDateWithoutNullable)
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
                .containsExactlyInAnyOrder(h2KotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds h2KotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullableBefore(LocalDate(2018, 11, 5)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2KotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNullableBefore(LocalDate(2018, 11, 4)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds h2KotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullableBeforeOrEq(LocalDate(2018, 11, 5)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2KotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds h2KotlinxLocalDateWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNullableBeforeOrEq(LocalDate(2018, 11, 4)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2KotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds h2KotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullableAfter(LocalDate(2018, 11, 3)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2KotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNullableAfter(LocalDate(2018, 11, 4)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds h2KotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullableAfterOrEq(LocalDate(2018, 11, 3)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2KotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds h2KotlinxLocalDateWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNullableAfterOrEq(LocalDate(2018, 11, 4)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2KotlinxLocalDateWithNullable)
    }
}


class KotlinxLocalDateRepositoryH2Select(private val sqlClient: ReactorSqlClient) : Repository {

    override fun init() {
        createTables()
                .then(insertLocalDates())
                .block()
    }

    override fun delete() {
        deleteAll()
                .block()
    }

    private fun createTables() =
            sqlClient.createTable<H2KotlinxLocalDate>()

    private fun insertLocalDates() =
            sqlClient.insert(h2KotlinxLocalDateWithNullable, h2KotlinxLocalDateWithoutNullable)

    private fun deleteAll() = sqlClient.deleteAllFromTable<H2KotlinxLocalDate>()

    fun selectAllByLocalDateNotNull(localDate: LocalDate) =
            sqlClient.select<H2KotlinxLocalDate>()
                    .where { it[H2KotlinxLocalDate::localDateNotNull] eq localDate }
                    .fetchAll()

    fun selectAllByLocalDateNotNullNotEq(localDate: LocalDate) =
            sqlClient.select<H2KotlinxLocalDate>()
                    .where { it[H2KotlinxLocalDate::localDateNotNull] notEq localDate }
                    .fetchAll()

    fun selectAllByLocalDateNotNullBefore(localDate: LocalDate) =
            sqlClient.select<H2KotlinxLocalDate>()
                    .where { it[H2KotlinxLocalDate::localDateNotNull] before localDate }
                    .fetchAll()

    fun selectAllByLocalDateNotNullBeforeOrEq(localDate: LocalDate) =
            sqlClient.select<H2KotlinxLocalDate>()
                    .where { it[H2KotlinxLocalDate::localDateNotNull] beforeOrEq localDate }
                    .fetchAll()

    fun selectAllByLocalDateNotNullAfter(localDate: LocalDate) =
            sqlClient.select<H2KotlinxLocalDate>()
                    .where { it[H2KotlinxLocalDate::localDateNotNull] after localDate }
                    .fetchAll()

    fun selectAllByLocalDateNotNullAfterOrEq(localDate: LocalDate) =
            sqlClient.select<H2KotlinxLocalDate>()
                    .where { it[H2KotlinxLocalDate::localDateNotNull] afterOrEq localDate }
                    .fetchAll()

    fun selectAllByLocalDateNullable(localDate: LocalDate?) =
            sqlClient.select<H2KotlinxLocalDate>()
                    .where { it[H2KotlinxLocalDate::localDateNullable] eq localDate }
                    .fetchAll()

    fun selectAllByLocalDateNullableNotEq(localDate: LocalDate?) =
            sqlClient.select<H2KotlinxLocalDate>()
                    .where { it[H2KotlinxLocalDate::localDateNullable] notEq localDate }
                    .fetchAll()

    fun selectAllByLocalDateNullableBefore(localDate: LocalDate) =
            sqlClient.select<H2KotlinxLocalDate>()
                    .where { it[H2KotlinxLocalDate::localDateNullable] before localDate }
                    .fetchAll()

    fun selectAllByLocalDateNullableBeforeOrEq(localDate: LocalDate) =
            sqlClient.select<H2KotlinxLocalDate>()
                    .where { it[H2KotlinxLocalDate::localDateNullable] beforeOrEq localDate }
                    .fetchAll()

    fun selectAllByLocalDateNullableAfter(localDate: LocalDate) =
            sqlClient.select<H2KotlinxLocalDate>()
                    .where { it[H2KotlinxLocalDate::localDateNullable] after localDate }
                    .fetchAll()

    fun selectAllByLocalDateNullableAfterOrEq(localDate: LocalDate) =
            sqlClient.select<H2KotlinxLocalDate>()
                    .where { it[H2KotlinxLocalDate::localDateNullable] afterOrEq localDate }
                    .fetchAll()
}
