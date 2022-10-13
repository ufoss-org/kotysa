/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.transaction.Transaction

interface SelectLocalDateTimeAsTimestampTest<T : LocalDateTimeAsTimestamps,
        U : SelectLocalDateTimeAsTimestampRepository<T>, V : Transaction> : RepositoryTest<U, V> {

    @Test
    fun `Verify selectAllByLocalDateTimeNotNull finds localDateTimeAsTimestampWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNull(java.time.LocalDateTime.of(2019, 11, 4, 0, 0)))
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullNotEq finds localDateTimeAsTimestampWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullNotEq(java.time.LocalDateTime.of(2019, 11, 4, 0, 0)))
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateTimeAsTimestampWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullIn finds both`() {
        val seq = sequenceOf(
            localDateTimeWithNullable.localDateTimeNotNull,
            localDateTimeWithoutNullable.localDateTimeNotNull
        )
        assertThat(repository.selectAllByLocalDateTimeNotNullIn(seq))
            .hasSize(2)
            .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable, localDateTimeAsTimestampWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBefore finds localDateTimeAsTimestampWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBefore(java.time.LocalDateTime.of(2019, 11, 4, 12, 0)))
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBefore(java.time.LocalDateTime.of(2019, 11, 4, 0, 0)))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds localDateTimeAsTimestampWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBeforeOrEq(java.time.LocalDateTime.of(2019, 11, 4, 12, 0)))
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds localDateTimeAsTimestampWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBeforeOrEq(java.time.LocalDateTime.of(2019, 11, 4, 0, 0)))
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfter finds localDateTimeAsTimestampWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfter(java.time.LocalDateTime.of(2019, 11, 5, 12, 0)))
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateTimeAsTimestampWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfter(java.time.LocalDateTime.of(2019, 11, 6, 0, 0)))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds localDateTimeAsTimestampWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfterOrEq(java.time.LocalDateTime.of(2019, 11, 5, 12, 0)))
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateTimeAsTimestampWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds localDateTimeAsTimestampWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfterOrEq(java.time.LocalDateTime.of(2019, 11, 6, 0, 0)))
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateTimeAsTimestampWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullable finds h2UuidWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullable(java.time.LocalDateTime.of(2018, 11, 4, 0, 0)))
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullable finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullable(null))
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateTimeAsTimestampWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableNotEq finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableNotEq(java.time.LocalDateTime.of(2018, 11, 4, 0, 0)))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLocalDateTimeNullableNotEq(null))
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBefore finds localDateTimeAsTimestampWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBefore(java.time.LocalDateTime.of(2018, 11, 4, 12, 0)))
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBefore(java.time.LocalDateTime.of(2018, 11, 4, 0, 0)))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds localDateTimeAsTimestampWithNullable`() {
        assertThat(
            repository.selectAllByLocalDateTimeNullableBeforeOrEq(
                java.time.LocalDateTime.of(
                    2018,
                    11,
                    5,
                    12,
                    0
                )
            )
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds localDateTimeAsTimestampWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBeforeOrEq(java.time.LocalDateTime.of(2018, 11, 4, 0, 0)))
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfter finds localDateTimeAsTimestampWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfter(java.time.LocalDateTime.of(2018, 11, 3, 12, 0)))
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfter(java.time.LocalDateTime.of(2018, 11, 4, 0, 0)))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfterOrEq finds localDateTimeAsTimestampWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfterOrEq(java.time.LocalDateTime.of(2018, 11, 3, 12, 0)))
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfterOrEq finds localDateTimeAsTimestampWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfterOrEq(java.time.LocalDateTime.of(2018, 11, 4, 0, 0)))
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }
}
