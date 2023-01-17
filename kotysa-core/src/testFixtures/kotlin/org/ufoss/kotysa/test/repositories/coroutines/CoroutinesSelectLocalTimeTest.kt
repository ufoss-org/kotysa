/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.LocalTimes
import org.ufoss.kotysa.test.localTimeWithNullable
import org.ufoss.kotysa.test.localTimeWithoutNullable
import org.ufoss.kotysa.transaction.Transaction
import java.time.LocalTime

interface CoroutinesSelectLocalTimeTest<T : LocalTimes, U : CoroutinesSelectLocalTimeRepository<T>, V : Transaction> :
    CoroutinesRepositoryTest<U, V> {

    @Test
    fun `Verify selectAllByLocalTimeNotNull finds localTimeWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNotNull(LocalTime.of(12, 4)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullNotEq finds localTimeWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNotNullNotEq(LocalTime.of(12, 4)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullIn finds both`() = runTest {
        val seq = sequenceOf(
            localTimeWithNullable.localTimeNotNull,
            localTimeWithoutNullable.localTimeNotNull
        )
        assertThat(repository.selectAllByLocalTimeNotNullIn(seq).toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(localTimeWithNullable, localTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBefore finds localTimeWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNotNullBefore(LocalTime.of(12, 5)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBefore finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLocalTimeNotNullBefore(LocalTime.of(12, 4)).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBeforeOrEq finds localTimeWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNotNullBeforeOrEq(LocalTime.of(12, 5)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBeforeOrEq finds localTimeWithNullable when equals`() = runTest {
        assertThat(repository.selectAllByLocalTimeNotNullBeforeOrEq(LocalTime.of(12, 4)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfter finds localTimeWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNotNullAfter(LocalTime.of(12, 5)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfter finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLocalTimeNotNullAfter(LocalTime.of(12, 6)).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfterOrEq finds localTimeWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNotNullAfterOrEq(LocalTime.of(12, 5)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfterOrEq finds localTimeWithoutNullable when equals`() = runTest {
        assertThat(repository.selectAllByLocalTimeNotNullAfterOrEq(LocalTime.of(12, 6)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullable finds h2UuidWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullable(LocalTime.of(11, 4)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullable finds h2UuidWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullable(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableNotEq finds h2UuidWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullableNotEq(LocalTime.of(11, 4)).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableNotEq finds no results`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullableNotEq(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBefore finds localTimeWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullableBefore(LocalTime.of(11, 5)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBefore finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullableBefore(LocalTime.of(11, 4)).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBeforeOrEq finds localTimeWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullableBeforeOrEq(LocalTime.of(11, 5)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBeforeOrEq finds localTimeWithNullable when equals`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullableBeforeOrEq(LocalTime.of(11, 4)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfter finds localTimeWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullableAfter(LocalTime.of(11, 3)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfter finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullableAfter(LocalTime.of(11, 4)).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfterOrEq finds localTimeWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullableAfterOrEq(LocalTime.of(11, 3)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfterOrEq finds localTimeWithoutNullable when equals`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullableAfterOrEq(LocalTime.of(11, 4)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }
}
