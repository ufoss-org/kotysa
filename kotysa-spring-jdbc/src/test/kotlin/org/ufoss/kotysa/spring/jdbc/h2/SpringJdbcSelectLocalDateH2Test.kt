/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import java.time.LocalDate


class SpringJdbcSelectLocalDateH2Test : AbstractSpringJdbcH2Test<LocalDateRepositoryH2Select>() {
    override val context = startContext<LocalDateRepositoryH2Select>()

    override val repository = getContextRepository<LocalDateRepositoryH2Select>()

    @Test
    fun `Verify selectAllByLocalDateNotNull finds h2LocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNull(LocalDate.of(2019, 11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullNotEq finds h2LocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullNotEq(LocalDate.of(2019, 11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullIn finds both`() {
        val seq = sequenceOf(
                h2LocalDateWithNullable.localDateNotNull,
                h2LocalDateWithoutNullable.localDateNotNull)
        assertThat(repository.selectAllByLocalDateNotNullIn(seq))
                .hasSize(2)
                .containsExactlyInAnyOrder(h2LocalDateWithNullable, h2LocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds h2LocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullBefore(LocalDate.of(2019, 11, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullBefore(LocalDate.of(2019, 11, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds h2LocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullBeforeOrEq(LocalDate.of(2019, 11, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds h2LocalDateWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullBeforeOrEq(LocalDate.of(2019, 11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds h2LocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullAfter(LocalDate.of(2019, 11, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullAfter(LocalDate.of(2019, 11, 6)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds h2LocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullAfterOrEq(LocalDate.of(2019, 11, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds h2LocalDateWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullAfterOrEq(LocalDate.of(2019, 11, 6)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds h2UuidWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullable(LocalDate.of(2018, 11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullable(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateWithoutNullable)
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
                .containsExactlyInAnyOrder(h2LocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds h2LocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullableBefore(LocalDate.of(2018, 11, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNullableBefore(LocalDate.of(2018, 11, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds h2LocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullableBeforeOrEq(LocalDate.of(2018, 11, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds h2LocalDateWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNullableBeforeOrEq(LocalDate.of(2018, 11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds h2LocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullableAfter(LocalDate.of(2018, 11, 3)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNullableAfter(LocalDate.of(2018, 11, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds h2LocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullableAfterOrEq(LocalDate.of(2018, 11, 3)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds h2LocalDateWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNullableAfterOrEq(LocalDate.of(2018, 11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalDateWithNullable)
    }
}


class LocalDateRepositoryH2Select(client: JdbcOperations) : Repository {

    private val sqlClient = client.sqlClient(h2Tables)

    override fun init() {
        createTables()
        insertLocalDates()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() =
            sqlClient.createTable<H2LocalDate>()

    private fun insertLocalDates() = sqlClient.insert(h2LocalDateWithNullable, h2LocalDateWithoutNullable)

    private fun deleteAll() = sqlClient.deleteAllFromTable<H2LocalDate>()

    fun selectAllByLocalDateNotNull(localDate: LocalDate) = sqlClient.select<H2LocalDate>()
            .where { it[H2LocalDate::localDateNotNull] eq localDate }
            .fetchAll()

    fun selectAllByLocalDateNotNullNotEq(localDate: LocalDate) = sqlClient.select<H2LocalDate>()
            .where { it[H2LocalDate::localDateNotNull] notEq localDate }
            .fetchAll()

    fun selectAllByLocalDateNotNullIn(values: Sequence<LocalDate>) =
            sqlClient.select<H2LocalDate>()
                    .where { it[H2LocalDate::localDateNotNull] `in` values }
                    .fetchAll()

    fun selectAllByLocalDateNotNullBefore(localDate: LocalDate) = sqlClient.select<H2LocalDate>()
            .where { it[H2LocalDate::localDateNotNull] before localDate }
            .fetchAll()

    fun selectAllByLocalDateNotNullBeforeOrEq(localDate: LocalDate) = sqlClient.select<H2LocalDate>()
            .where { it[H2LocalDate::localDateNotNull] beforeOrEq localDate }
            .fetchAll()

    fun selectAllByLocalDateNotNullAfter(localDate: LocalDate) = sqlClient.select<H2LocalDate>()
            .where { it[H2LocalDate::localDateNotNull] after localDate }
            .fetchAll()

    fun selectAllByLocalDateNotNullAfterOrEq(localDate: LocalDate) = sqlClient.select<H2LocalDate>()
            .where { it[H2LocalDate::localDateNotNull] afterOrEq localDate }
            .fetchAll()

    fun selectAllByLocalDateNullable(localDate: LocalDate?) = sqlClient.select<H2LocalDate>()
            .where { it[H2LocalDate::localDateNullable] eq localDate }
            .fetchAll()

    fun selectAllByLocalDateNullableNotEq(localDate: LocalDate?) = sqlClient.select<H2LocalDate>()
            .where { it[H2LocalDate::localDateNullable] notEq localDate }
            .fetchAll()

    fun selectAllByLocalDateNullableBefore(localDate: LocalDate) = sqlClient.select<H2LocalDate>()
            .where { it[H2LocalDate::localDateNullable] before localDate }
            .fetchAll()

    fun selectAllByLocalDateNullableBeforeOrEq(localDate: LocalDate) = sqlClient.select<H2LocalDate>()
            .where { it[H2LocalDate::localDateNullable] beforeOrEq localDate }
            .fetchAll()

    fun selectAllByLocalDateNullableAfter(localDate: LocalDate) = sqlClient.select<H2LocalDate>()
            .where { it[H2LocalDate::localDateNullable] after localDate }
            .fetchAll()

    fun selectAllByLocalDateNullableAfterOrEq(localDate: LocalDate) = sqlClient.select<H2LocalDate>()
            .where { it[H2LocalDate::localDateNullable] afterOrEq localDate }
            .fetchAll()
}
