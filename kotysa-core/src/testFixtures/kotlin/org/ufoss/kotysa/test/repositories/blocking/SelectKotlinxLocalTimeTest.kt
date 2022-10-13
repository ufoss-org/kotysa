/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import kotlinx.datetime.LocalTime
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.KotlinxLocalTimes
import org.ufoss.kotysa.test.kotlinxLocalTimeWithNullable
import org.ufoss.kotysa.test.kotlinxLocalTimeWithoutNullable
import org.ufoss.kotysa.transaction.Transaction

interface SelectKotlinxLocalTimeTest<T : KotlinxLocalTimes, U : SelectKotlinxLocalTimeRepository<T>, V : Transaction> :
    RepositoryTest<U, V> {

    @Test
    fun `Verify selectAllByLocalTimeNotNull finds kotlinxLocalTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNull(LocalTime(12, 4)))
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullNotEq finds kotlinxLocalTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullNotEq(LocalTime(12, 4)))
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullIn finds both`() {
        val seq = sequenceOf(
            kotlinxLocalTimeWithNullable.localTimeNotNull,
            kotlinxLocalTimeWithoutNullable.localTimeNotNull
        )
        assertThat(repository.selectAllByLocalTimeNotNullIn(seq))
            .hasSize(2)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithNullable, kotlinxLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBefore finds kotlinxLocalTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullBefore(LocalTime(12, 5)))
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalTimeNotNullBefore(LocalTime(12, 4)))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBeforeOrEq finds kotlinxLocalTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullBeforeOrEq(LocalTime(12, 5)))
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBeforeOrEq finds kotlinxLocalTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalTimeNotNullBeforeOrEq(LocalTime(12, 4)))
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfter finds kotlinxLocalTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullAfter(LocalTime(12, 5)))
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalTimeNotNullAfter(LocalTime(12, 6)))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfterOrEq finds kotlinxLocalTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullAfterOrEq(LocalTime(12, 5)))
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfterOrEq finds kotlinxLocalTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalTimeNotNullAfterOrEq(LocalTime(12, 6)))
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullable finds h2UuidWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNullable(LocalTime(11, 4)))
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullable finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNullable(null))
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableNotEq finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableNotEq(LocalTime(11, 4)))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLocalTimeNullableNotEq(null))
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBefore finds kotlinxLocalTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableBefore(LocalTime(11, 5)))
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalTimeNullableBefore(LocalTime(11, 4)))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBeforeOrEq finds kotlinxLocalTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableBeforeOrEq(LocalTime(11, 5)))
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBeforeOrEq finds kotlinxLocalTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalTimeNullableBeforeOrEq(LocalTime(11, 4)))
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfter finds kotlinxLocalTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableAfter(LocalTime(11, 3)))
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalTimeNullableAfter(LocalTime(11, 4)))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfterOrEq finds kotlinxLocalTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableAfterOrEq(LocalTime(11, 3)))
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfterOrEq finds kotlinxLocalTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalTimeNullableAfterOrEq(LocalTime(11, 4)))
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalTimeWithNullable)
    }
}
