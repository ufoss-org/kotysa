/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.LocalDates
import org.ufoss.kotysa.test.localDateWithNullable
import org.ufoss.kotysa.test.localDateWithoutNullable
import org.ufoss.kotysa.transaction.Transaction
import java.time.LocalDate

interface SelectLocalDateTest<T : LocalDates, U : SelectLocalDateRepository<T>, V : Transaction> :
    RepositoryTest<U, V> {

    @Test
    fun `Verify selectAllByLocalDateNotNull finds localDateWithNullable`() {
        Assertions.assertThat(repository.selectAllByLocalDateNotNull(LocalDate.of(2019, 11, 4)))
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullNotEq finds localDateWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByLocalDateNotNullNotEq(LocalDate.of(2019, 11, 4)))
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullIn finds both`() {
        val seq = sequenceOf(
            localDateWithNullable.localDateNotNull,
            localDateWithoutNullable.localDateNotNull)
        Assertions.assertThat(repository.selectAllByLocalDateNotNullIn(seq))
            .hasSize(2)
            .containsExactlyInAnyOrder(localDateWithNullable, localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds localDateWithNullable`() {
        Assertions.assertThat(repository.selectAllByLocalDateNotNullBefore(LocalDate.of(2019, 11, 5)))
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds no results when equals`() {
        Assertions.assertThat(repository.selectAllByLocalDateNotNullBefore(LocalDate.of(2019, 11, 4)))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds localDateWithNullable`() {
        Assertions.assertThat(repository.selectAllByLocalDateNotNullBeforeOrEq(LocalDate.of(2019, 11, 5)))
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds localDateWithNullable when equals`() {
        Assertions.assertThat(repository.selectAllByLocalDateNotNullBeforeOrEq(LocalDate.of(2019, 11, 4)))
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds localDateWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByLocalDateNotNullAfter(LocalDate.of(2019, 11, 5)))
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds no results when equals`() {
        Assertions.assertThat(repository.selectAllByLocalDateNotNullAfter(LocalDate.of(2019, 11, 6)))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds localDateWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByLocalDateNotNullAfterOrEq(LocalDate.of(2019, 11, 5)))
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds localDateWithoutNullable when equals`() {
        Assertions.assertThat(repository.selectAllByLocalDateNotNullAfterOrEq(LocalDate.of(2019, 11, 6)))
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds h2UuidWithNullable`() {
        Assertions.assertThat(repository.selectAllByLocalDateNullable(LocalDate.of(2018, 11, 4)))
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds h2UuidWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByLocalDateNullable(null))
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds h2UuidWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByLocalDateNullableNotEq(LocalDate.of(2018, 11, 4)))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds no results`() {
        Assertions.assertThat(repository.selectAllByLocalDateNullableNotEq(null))
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds localDateWithNullable`() {
        Assertions.assertThat(repository.selectAllByLocalDateNullableBefore(LocalDate.of(2018, 11, 5)))
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds no results when equals`() {
        Assertions.assertThat(repository.selectAllByLocalDateNullableBefore(LocalDate.of(2018, 11, 4)))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds localDateWithNullable`() {
        Assertions.assertThat(repository.selectAllByLocalDateNullableBeforeOrEq(LocalDate.of(2018, 11, 5)))
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds localDateWithNullable when equals`() {
        Assertions.assertThat(repository.selectAllByLocalDateNullableBeforeOrEq(LocalDate.of(2018, 11, 4)))
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds localDateWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByLocalDateNullableAfter(LocalDate.of(2018, 11, 3)))
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds no results when equals`() {
        Assertions.assertThat(repository.selectAllByLocalDateNullableAfter(LocalDate.of(2018, 11, 4)))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds localDateWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByLocalDateNullableAfterOrEq(LocalDate.of(2018, 11, 3)))
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds localDateWithoutNullable when equals`() {
        Assertions.assertThat(repository.selectAllByLocalDateNullableAfterOrEq(LocalDate.of(2018, 11, 4)))
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }
}
