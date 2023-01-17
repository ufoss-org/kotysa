/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.LocalDates
import org.ufoss.kotysa.test.localDateWithNullable
import org.ufoss.kotysa.test.localDateWithoutNullable
import org.ufoss.kotysa.transaction.Transaction
import java.time.LocalDate

interface CoroutinesSelectLocalDateTest<T : LocalDates, U : CoroutinesSelectLocalDateRepository<T>, V : Transaction> :
    CoroutinesRepositoryTest<U, V> {

    @Test
    fun `Verify selectAllByLocalDateNotNull finds localDateWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNotNull(LocalDate.of(2019, 11, 4)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullNotEq finds localDateWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNotNullNotEq(
            LocalDate.of(2019, 11, 4)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullIn finds both`() = runTest {
        val seq = sequenceOf(
            localDateWithNullable.localDateNotNull,
            localDateWithoutNullable.localDateNotNull)
        assertThat(repository.selectAllByLocalDateNotNullIn(seq).toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(localDateWithNullable, localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds localDateWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNotNullBefore(
            LocalDate.of(2019, 11, 5)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateNotNullBefore(
            LocalDate.of(2019, 11, 4)).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds localDateWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNotNullBeforeOrEq(
            LocalDate.of(2019, 11, 5)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds localDateWithNullable when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateNotNullBeforeOrEq(
            LocalDate.of(2019, 11, 4)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds localDateWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNotNullAfter(
            LocalDate.of(2019, 11, 5)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateNotNullAfter(
            LocalDate.of(2019, 11, 6)).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds localDateWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNotNullAfterOrEq(
            LocalDate.of(2019, 11, 5)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds localDateWithoutNullable when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateNotNullAfterOrEq(
            LocalDate.of(2019, 11, 6)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds h2UuidWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNullable(LocalDate.of(2018, 11, 4)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds h2UuidWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNullable(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds h2UuidWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNullableNotEq(
            LocalDate.of(2018, 11, 4)).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds no results`() = runTest {
        assertThat(repository.selectAllByLocalDateNullableNotEq(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds localDateWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNullableBefore(
            LocalDate.of(2018, 11, 5)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateNullableBefore(
            LocalDate.of(2018, 11, 4)).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds localDateWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNullableBeforeOrEq(
            LocalDate.of(2018, 11, 5)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds localDateWithNullable when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateNullableBeforeOrEq(
            LocalDate.of(2018, 11, 4)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds localDateWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNullableAfter(
            LocalDate.of(2018, 11, 3)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateNullableAfter(
            LocalDate.of(2018, 11, 4)).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds localDateWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNullableAfterOrEq(
            LocalDate.of(2018, 11, 3)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds localDateWithoutNullable when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateNullableAfterOrEq(
            LocalDate.of(2018, 11, 4)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }
}
