/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.LocalDateTimes
import org.ufoss.kotysa.test.localDateTimeWithNullable
import org.ufoss.kotysa.test.localDateTimeWithoutNullable
import java.time.LocalDateTime

interface MutinySelectLocalDateTimeTest<T : LocalDateTimes, U : MutinySelectLocalDateTimeRepository<T>>
    : MutinyRepositoryTest<U> {

    @Test
    fun `Verify selectAllByLocalDateTimeNotNull finds localDateTimeWithNullable`() {
        assertThat(
            repository.selectAllByLocalDateTimeNotNull(LocalDateTime.of(2019, 11, 4, 0, 0))
                .await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullNotEq finds localDateTimeWithoutNullable`() {
        assertThat(
            repository.selectAllByLocalDateTimeNotNullNotEq(LocalDateTime.of(2019, 11, 4, 0, 0))
                .await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullIn finds both`() {
        val seq = sequenceOf(
            localDateTimeWithNullable.localDateTimeNotNull,
            localDateTimeWithoutNullable.localDateTimeNotNull
        )
        assertThat(repository.selectAllByLocalDateTimeNotNullIn(seq).await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(localDateTimeWithNullable, localDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBefore finds localDateTimeWithNullable`() {
        assertThat(
            repository.selectAllByLocalDateTimeNotNullBefore(LocalDateTime.of(2019, 11, 4, 12, 0))
                .await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBefore finds no results when equals`() {
        assertThat(
            repository.selectAllByLocalDateTimeNotNullBefore(LocalDateTime.of(2019, 11, 4, 0, 0))
                .await().indefinitely()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds localDateTimeWithNullable`() {
        assertThat(
            repository.selectAllByLocalDateTimeNotNullBeforeOrEq(LocalDateTime.of(2019, 11, 4, 12, 0))
                .await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds localDateTimeWithNullable when equals`() {
        assertThat(
            repository.selectAllByLocalDateTimeNotNullBeforeOrEq(LocalDateTime.of(2019, 11, 4, 0, 0))
                .await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfter finds localDateTimeWithoutNullable`() {
        assertThat(
            repository.selectAllByLocalDateTimeNotNullAfter(LocalDateTime.of(2019, 11, 5, 12, 0))
                .await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfter finds no results when equals`() {
        assertThat(
            repository.selectAllByLocalDateTimeNotNullAfter(LocalDateTime.of(2019, 11, 6, 0, 0))
                .await().indefinitely()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds localDateTimeWithoutNullable`() {
        assertThat(
            repository.selectAllByLocalDateTimeNotNullAfterOrEq(LocalDateTime.of(2019, 11, 5, 12, 0))
                .await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds localDateTimeWithoutNullable when equals`() {
        assertThat(
            repository.selectAllByLocalDateTimeNotNullAfterOrEq(LocalDateTime.of(2019, 11, 6, 0, 0))
                .await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullable finds h2UuidWithNullable`() {
        assertThat(
            repository.selectAllByLocalDateTimeNullable(LocalDateTime.of(2018, 11, 4, 0, 0))
                .await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullable finds h2UuidWithoutNullable`() {
        assertThat(
            repository.selectAllByLocalDateTimeNullable(null).await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableNotEq finds h2UuidWithoutNullable`() {
        assertThat(
            repository.selectAllByLocalDateTimeNullableNotEq(LocalDateTime.of(2018, 11, 4, 0, 0))
                .await().indefinitely()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableNotEq finds no results`() {
        assertThat(
            repository.selectAllByLocalDateTimeNullableNotEq(null).await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBefore finds localDateTimeWithNullable`() {
        assertThat(
            repository.selectAllByLocalDateTimeNullableBefore(LocalDateTime.of(2018, 11, 4, 12, 0))
                .await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBefore finds no results when equals`() {
        assertThat(
            repository.selectAllByLocalDateTimeNullableBefore(LocalDateTime.of(2018, 11, 4, 0, 0))
                .await().indefinitely()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds localDateTimeWithNullable`() {
        assertThat(
            repository.selectAllByLocalDateTimeNullableBeforeOrEq(LocalDateTime.of(2018, 11, 5, 12, 0))
                .await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds localDateTimeWithNullable when equals`() {
        assertThat(
            repository.selectAllByLocalDateTimeNullableBeforeOrEq(LocalDateTime.of(2018, 11, 4, 0, 0))
                .await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfter finds localDateTimeWithoutNullable`() {
        assertThat(
            repository.selectAllByLocalDateTimeNullableAfter(LocalDateTime.of(2018, 11, 3, 12, 0))
                .await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfter finds no results when equals`() {
        assertThat(
            repository.selectAllByLocalDateTimeNullableAfter(LocalDateTime.of(2018, 11, 4, 0, 0))
                .await().indefinitely()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfterOrEq finds localDateTimeWithoutNullable`() {
        assertThat(
            repository.selectAllByLocalDateTimeNullableAfterOrEq(LocalDateTime.of(2018, 11, 3, 12, 0))
                .await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfterOrEq finds localDateTimeWithoutNullable when equals`() {
        assertThat(
            repository.selectAllByLocalDateTimeNullableAfterOrEq(LocalDateTime.of(2018, 11, 4, 0, 0))
                .await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateTimeWithNullable)
    }
}
