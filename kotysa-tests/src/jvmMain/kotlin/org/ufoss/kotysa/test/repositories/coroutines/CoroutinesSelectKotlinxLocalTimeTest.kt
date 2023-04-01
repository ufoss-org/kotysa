/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalTime
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.transaction.Transaction

interface CoroutinesSelectKotlinxLocalTimeTest<T : KotlinxLocalTimes, U : CoroutinesSelectKotlinxLocalTimeRepository<T>,
        V : Transaction> : CoroutinesRepositoryTest<U, V> {

    @Test
    fun `Verify selectAllByLocalTimeNotNull finds kotlinxLocalTimeWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNotNull(LocalTime(12, 4)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullNotEq finds kotlinxLocalTimeWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNotNullNotEq(LocalTime(12, 4)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullIn finds both`() = runTest {
        val seq = sequenceOf(
            kotlinxLocalTimeWithNullable.localTimeNotNull,
            kotlinxLocalTimeWithoutNullable.localTimeNotNull
        )
        assertThat(repository.selectAllByLocalTimeNotNullIn(seq).toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithNullable, kotlinxLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBefore finds kotlinxLocalTimeWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNotNullBefore(LocalTime(12, 5)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBefore finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLocalTimeNotNullBefore(LocalTime(12, 4)).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBeforeOrEq finds kotlinxLocalTimeWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNotNullBeforeOrEq(LocalTime(12, 5)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBeforeOrEq finds kotlinxLocalTimeWithNullable when equals`() = runTest {
        assertThat(repository.selectAllByLocalTimeNotNullBeforeOrEq(LocalTime(12, 4)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfter finds kotlinxLocalTimeWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNotNullAfter(LocalTime(12, 5)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfter finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLocalTimeNotNullAfter(LocalTime(12, 6)).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfterOrEq finds kotlinxLocalTimeWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNotNullAfterOrEq(LocalTime(12, 5)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfterOrEq finds kotlinxLocalTimeWithoutNullable when equals`() = runTest {
        assertThat(repository.selectAllByLocalTimeNotNullAfterOrEq(LocalTime(12, 6)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullable finds h2UuidWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullable(LocalTime(11, 4)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullable finds h2UuidWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullable(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableNotEq finds h2UuidWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullableNotEq(LocalTime(11, 4)).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableNotEq finds no results`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullableNotEq(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBefore finds kotlinxLocalTimeWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullableBefore(LocalTime(11, 5)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBefore finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullableBefore(LocalTime(11, 4)).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBeforeOrEq finds kotlinxLocalTimeWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullableBeforeOrEq(LocalTime(11, 5)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBeforeOrEq finds kotlinxLocalTimeWithNullable when equals`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullableBeforeOrEq(LocalTime(11, 4)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfter finds kotlinxLocalTimeWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullableAfter(LocalTime(11, 3)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfter finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullableAfter(LocalTime(11, 4)).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfterOrEq finds kotlinxLocalTimeWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullableAfterOrEq(LocalTime(11, 3)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfterOrEq finds kotlinxLocalTimeWithoutNullable when equals`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullableAfterOrEq(LocalTime(11, 4)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithNullable)
    }
}
