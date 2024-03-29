/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.KotlinxLocalDates
import org.ufoss.kotysa.test.kotlinxLocalDateWithNullable
import org.ufoss.kotysa.test.kotlinxLocalDateWithoutNullable
import org.ufoss.kotysa.transaction.Transaction

interface CoroutinesSelectKotlinxLocalDateTest<T : KotlinxLocalDates, U : CoroutinesSelectKotlinxLocalDateRepository<T>, V : Transaction> :
    CoroutinesRepositoryTest<U, V> {

    @Test
    fun `Verify selectAllByLocalDateNotNull finds kotlinxLocalDateWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNotNull(LocalDate(2019, 11, 4)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullNotEq finds kotlinxLocalDateWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNotNullNotEq(
            LocalDate(2019, 11, 4)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullIn finds both`() = runTest {
        val seq = sequenceOf(
            kotlinxLocalDateWithNullable.localDateNotNull,
            kotlinxLocalDateWithoutNullable.localDateNotNull
        )
        assertThat(repository.selectAllByLocalDateNotNullIn(seq).toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable, kotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds kotlinxLocalDateWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNotNullBefore(
            LocalDate(2019, 11, 5)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateNotNullBefore(
            LocalDate(2019, 11, 4)).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds kotlinxLocalDateWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNotNullBeforeOrEq(
            LocalDate(2019, 11, 5)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds kotlinxLocalDateWithNullable when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateNotNullBeforeOrEq(
            LocalDate(2019, 11, 4)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds kotlinxLocalDateWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNotNullAfter(
            LocalDate(2019, 11, 5)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateNotNullAfter(
            LocalDate(2019, 11, 6)).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds kotlinxLocalDateWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNotNullAfterOrEq(
            LocalDate(2019, 11, 5)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds kotlinxLocalDateWithoutNullable when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateNotNullAfterOrEq(
            LocalDate(2019, 11, 6)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds h2UuidWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNullable(LocalDate(2018, 11, 4)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds h2UuidWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNullable(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds h2UuidWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNullableNotEq(
            LocalDate(2018, 11, 4)).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds no results`() = runTest {
        assertThat(repository.selectAllByLocalDateNullableNotEq(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds kotlinxLocalDateWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNullableBefore(
            LocalDate(2018, 11, 5)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateNullableBefore(
            LocalDate(2018, 11, 4)).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds kotlinxLocalDateWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNullableBeforeOrEq(
            LocalDate(2018, 11, 5)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds kotlinxLocalDateWithNullable when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateNullableBeforeOrEq(
            LocalDate(2018, 11, 4)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds kotlinxLocalDateWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNullableAfter(
            LocalDate(2018, 11, 3)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateNullableAfter(
            LocalDate(2018, 11, 4)).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds kotlinxLocalDateWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNullableAfterOrEq(
            LocalDate(2018, 11, 3)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds kotlinxLocalDateWithoutNullable when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateNullableAfterOrEq(
            LocalDate(2018, 11, 4)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }
}
