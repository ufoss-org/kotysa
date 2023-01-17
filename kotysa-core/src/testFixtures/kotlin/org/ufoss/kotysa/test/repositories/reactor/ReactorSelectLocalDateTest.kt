/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.reactor

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.LocalDates
import org.ufoss.kotysa.test.localDateWithNullable
import org.ufoss.kotysa.test.localDateWithoutNullable
import org.ufoss.kotysa.transaction.Transaction
import java.time.LocalDate

interface ReactorSelectLocalDateTest<T : LocalDates, U : ReactorSelectLocalDateRepository<T>, V : Transaction> :
    ReactorRepositoryTest<U, V> {

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
