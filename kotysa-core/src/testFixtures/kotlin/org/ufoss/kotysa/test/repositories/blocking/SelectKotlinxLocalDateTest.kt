/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import kotlinx.datetime.LocalDate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.KotlinxLocalDates
import org.ufoss.kotysa.test.kotlinxLocalDateWithNullable
import org.ufoss.kotysa.test.kotlinxLocalDateWithoutNullable
import org.ufoss.kotysa.transaction.Transaction

interface SelectKotlinxLocalDateTest<T : KotlinxLocalDates, U : SelectKotlinxLocalDateRepository<T>, V : Transaction> :
    RepositoryTest<U, V> {

    @Test
    fun `Verify selectAllByLocalDateNotNull finds kotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNull(LocalDate(2019, 11, 4)))
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullNotEq finds kotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullNotEq(LocalDate(2019, 11, 4)))
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullIn finds both`() {
        val seq = sequenceOf(
            kotlinxLocalDateWithNullable.localDateNotNull,
            kotlinxLocalDateWithoutNullable.localDateNotNull
        )
        assertThat(repository.selectAllByLocalDateNotNullIn(seq))
            .hasSize(2)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable, kotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds kotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullBefore(LocalDate(2019, 11, 5)))
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullBefore(LocalDate(2019, 11, 4)))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds kotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullBeforeOrEq(LocalDate(2019, 11, 5)))
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds kotlinxLocalDateWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullBeforeOrEq(LocalDate(2019, 11, 4)))
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds kotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullAfter(LocalDate(2019, 11, 5)))
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullAfter(LocalDate(2019, 11, 6)))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds kotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullAfterOrEq(LocalDate(2019, 11, 5)))
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds kotlinxLocalDateWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullAfterOrEq(LocalDate(2019, 11, 6)))
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds h2UuidWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullable(LocalDate(2018, 11, 4)))
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullable(null))
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithoutNullable)
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
            .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds kotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullableBefore(LocalDate(2018, 11, 5)))
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNullableBefore(LocalDate(2018, 11, 4)))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds kotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullableBeforeOrEq(LocalDate(2018, 11, 5)))
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds kotlinxLocalDateWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNullableBeforeOrEq(LocalDate(2018, 11, 4)))
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds kotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullableAfter(LocalDate(2018, 11, 3)))
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNullableAfter(LocalDate(2018, 11, 4)))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds kotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullableAfterOrEq(LocalDate(2018, 11, 3)))
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds kotlinxLocalDateWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNullableAfterOrEq(LocalDate(2018, 11, 4)))
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }
}
