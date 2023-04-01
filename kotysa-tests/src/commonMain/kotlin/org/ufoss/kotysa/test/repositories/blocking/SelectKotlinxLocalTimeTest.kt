/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import ch.tutteli.atrium.api.fluent.en_GB.toBeEmpty
import ch.tutteli.atrium.api.fluent.en_GB.toContain
import ch.tutteli.atrium.api.fluent.en_GB.toHaveSize
import kotlinx.datetime.LocalTime
import ch.tutteli.atrium.api.verbs.expect
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.KotlinxLocalTimes
import org.ufoss.kotysa.test.kotlinxLocalTimeWithNullable
import org.ufoss.kotysa.test.kotlinxLocalTimeWithoutNullable
import org.ufoss.kotysa.transaction.Transaction

interface SelectKotlinxLocalTimeTest<T : KotlinxLocalTimes, U : SelectKotlinxLocalTimeRepository<T>, V : Transaction> :
    RepositoryTest<U, V> {

    @Test
    fun `Verify selectAllByLocalTimeNotNull finds kotlinxLocalTimeWithNullable`() {
        expect(repository.selectAllByLocalTimeNotNull(LocalTime(12, 4)))
            .toHaveSize(1)
            .toContain(kotlinxLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullNotEq finds kotlinxLocalTimeWithoutNullable`() {
        expect(repository.selectAllByLocalTimeNotNullNotEq(LocalTime(12, 4)))
            .toHaveSize(1)
            .toContain(kotlinxLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullIn finds both`() {
        val seq = sequenceOf(
            kotlinxLocalTimeWithNullable.localTimeNotNull,
            kotlinxLocalTimeWithoutNullable.localTimeNotNull
        )
        expect(repository.selectAllByLocalTimeNotNullIn(seq))
            .toHaveSize(2)
            .toContain(kotlinxLocalTimeWithNullable, kotlinxLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBefore finds kotlinxLocalTimeWithNullable`() {
        expect(repository.selectAllByLocalTimeNotNullBefore(LocalTime(12, 5)))
            .toHaveSize(1)
            .toContain(kotlinxLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBefore finds no results when equals`() {
        expect(repository.selectAllByLocalTimeNotNullBefore(LocalTime(12, 4)))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBeforeOrEq finds kotlinxLocalTimeWithNullable`() {
        expect(repository.selectAllByLocalTimeNotNullBeforeOrEq(LocalTime(12, 5)))
            .toHaveSize(1)
            .toContain(kotlinxLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBeforeOrEq finds kotlinxLocalTimeWithNullable when equals`() {
        expect(repository.selectAllByLocalTimeNotNullBeforeOrEq(LocalTime(12, 4)))
            .toHaveSize(1)
            .toContain(kotlinxLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfter finds kotlinxLocalTimeWithoutNullable`() {
        expect(repository.selectAllByLocalTimeNotNullAfter(LocalTime(12, 5)))
            .toHaveSize(1)
            .toContain(kotlinxLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfter finds no results when equals`() {
        expect(repository.selectAllByLocalTimeNotNullAfter(LocalTime(12, 6)))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfterOrEq finds kotlinxLocalTimeWithoutNullable`() {
        expect(repository.selectAllByLocalTimeNotNullAfterOrEq(LocalTime(12, 5)))
            .toHaveSize(1)
            .toContain(kotlinxLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfterOrEq finds kotlinxLocalTimeWithoutNullable when equals`() {
        expect(repository.selectAllByLocalTimeNotNullAfterOrEq(LocalTime(12, 6)))
            .toHaveSize(1)
            .toContain(kotlinxLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullable finds h2UuidWithNullable`() {
        expect(repository.selectAllByLocalTimeNullable(LocalTime(11, 4)))
            .toHaveSize(1)
            .toContain(kotlinxLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullable finds h2UuidWithoutNullable`() {
        expect(repository.selectAllByLocalTimeNullable(null))
            .toHaveSize(1)
            .toContain(kotlinxLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableNotEq finds h2UuidWithoutNullable`() {
        expect(repository.selectAllByLocalTimeNullableNotEq(LocalTime(11, 4)))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableNotEq finds no results`() {
        expect(repository.selectAllByLocalTimeNullableNotEq(null))
            .toHaveSize(1)
            .toContain(kotlinxLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBefore finds kotlinxLocalTimeWithNullable`() {
        expect(repository.selectAllByLocalTimeNullableBefore(LocalTime(11, 5)))
            .toHaveSize(1)
            .toContain(kotlinxLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBefore finds no results when equals`() {
        expect(repository.selectAllByLocalTimeNullableBefore(LocalTime(11, 4)))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBeforeOrEq finds kotlinxLocalTimeWithNullable`() {
        expect(repository.selectAllByLocalTimeNullableBeforeOrEq(LocalTime(11, 5)))
            .toHaveSize(1)
            .toContain(kotlinxLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBeforeOrEq finds kotlinxLocalTimeWithNullable when equals`() {
        expect(repository.selectAllByLocalTimeNullableBeforeOrEq(LocalTime(11, 4)))
            .toHaveSize(1)
            .toContain(kotlinxLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfter finds kotlinxLocalTimeWithoutNullable`() {
        expect(repository.selectAllByLocalTimeNullableAfter(LocalTime(11, 3)))
            .toHaveSize(1)
            .toContain(kotlinxLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfter finds no results when equals`() {
        expect(repository.selectAllByLocalTimeNullableAfter(LocalTime(11, 4)))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfterOrEq finds kotlinxLocalTimeWithoutNullable`() {
        expect(repository.selectAllByLocalTimeNullableAfterOrEq(LocalTime(11, 3)))
            .toHaveSize(1)
            .toContain(kotlinxLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfterOrEq finds kotlinxLocalTimeWithoutNullable when equals`() {
        expect(repository.selectAllByLocalTimeNullableAfterOrEq(LocalTime(11, 4)))
            .toHaveSize(1)
            .toContain(kotlinxLocalTimeWithNullable)
    }
}
