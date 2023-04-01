/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import ch.tutteli.atrium.api.fluent.en_GB.toBeEmpty
import ch.tutteli.atrium.api.fluent.en_GB.toContain
import ch.tutteli.atrium.api.fluent.en_GB.toHaveSize
import ch.tutteli.atrium.api.verbs.expect
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.LocalTimes
import org.ufoss.kotysa.test.localTimeWithNullable
import org.ufoss.kotysa.test.localTimeWithoutNullable
import org.ufoss.kotysa.transaction.Transaction
import java.time.LocalTime

interface SelectLocalTimeTest<T : LocalTimes, U : SelectLocalTimeRepository<T>, V : Transaction> :
    RepositoryTest<U, V> {

    @Test
    fun `Verify selectAllByLocalTimeNotNull finds localTimeWithNullable`() {
        expect(repository.selectAllByLocalTimeNotNull(LocalTime.of(12, 4)))
            .toHaveSize(1)
            .toContain(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullNotEq finds localTimeWithoutNullable`() {
        expect(repository.selectAllByLocalTimeNotNullNotEq(LocalTime.of(12, 4)))
            .toHaveSize(1)
            .toContain(localTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullIn finds both`() {
        val seq = sequenceOf(
            localTimeWithNullable.localTimeNotNull,
            localTimeWithoutNullable.localTimeNotNull
        )
        expect(repository.selectAllByLocalTimeNotNullIn(seq))
            .toHaveSize(2)
            .toContain(localTimeWithNullable, localTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBefore finds localTimeWithNullable`() {
        expect(repository.selectAllByLocalTimeNotNullBefore(LocalTime.of(12, 5)))
            .toHaveSize(1)
            .toContain(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBefore finds no results when equals`() {
        expect(repository.selectAllByLocalTimeNotNullBefore(LocalTime.of(12, 4)))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBeforeOrEq finds localTimeWithNullable`() {
        expect(repository.selectAllByLocalTimeNotNullBeforeOrEq(LocalTime.of(12, 5)))
            .toHaveSize(1)
            .toContain(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBeforeOrEq finds localTimeWithNullable when equals`() {
        expect(repository.selectAllByLocalTimeNotNullBeforeOrEq(LocalTime.of(12, 4)))
            .toHaveSize(1)
            .toContain(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfter finds localTimeWithoutNullable`() {
        expect(repository.selectAllByLocalTimeNotNullAfter(LocalTime.of(12, 5)))
            .toHaveSize(1)
            .toContain(localTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfter finds no results when equals`() {
        expect(repository.selectAllByLocalTimeNotNullAfter(LocalTime.of(12, 6)))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfterOrEq finds localTimeWithoutNullable`() {
        expect(repository.selectAllByLocalTimeNotNullAfterOrEq(LocalTime.of(12, 5)))
            .toHaveSize(1)
            .toContain(localTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfterOrEq finds localTimeWithoutNullable when equals`() {
        expect(repository.selectAllByLocalTimeNotNullAfterOrEq(LocalTime.of(12, 6)))
            .toHaveSize(1)
            .toContain(localTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullable finds h2UuidWithNullable`() {
        expect(repository.selectAllByLocalTimeNullable(LocalTime.of(11, 4)))
            .toHaveSize(1)
            .toContain(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullable finds h2UuidWithoutNullable`() {
        expect(repository.selectAllByLocalTimeNullable(null))
            .toHaveSize(1)
            .toContain(localTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableNotEq finds h2UuidWithoutNullable`() {
        expect(repository.selectAllByLocalTimeNullableNotEq(LocalTime.of(11, 4)))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableNotEq finds no results`() {
        expect(repository.selectAllByLocalTimeNullableNotEq(null))
            .toHaveSize(1)
            .toContain(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBefore finds localTimeWithNullable`() {
        expect(repository.selectAllByLocalTimeNullableBefore(LocalTime.of(11, 5)))
            .toHaveSize(1)
            .toContain(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBefore finds no results when equals`() {
        expect(repository.selectAllByLocalTimeNullableBefore(LocalTime.of(11, 4)))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBeforeOrEq finds localTimeWithNullable`() {
        expect(repository.selectAllByLocalTimeNullableBeforeOrEq(LocalTime.of(11, 5)))
            .toHaveSize(1)
            .toContain(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBeforeOrEq finds localTimeWithNullable when equals`() {
        expect(repository.selectAllByLocalTimeNullableBeforeOrEq(LocalTime.of(11, 4)))
            .toHaveSize(1)
            .toContain(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfter finds localTimeWithoutNullable`() {
        expect(repository.selectAllByLocalTimeNullableAfter(LocalTime.of(11, 3)))
            .toHaveSize(1)
            .toContain(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfter finds no results when equals`() {
        expect(repository.selectAllByLocalTimeNullableAfter(LocalTime.of(11, 4)))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfterOrEq finds localTimeWithoutNullable`() {
        expect(repository.selectAllByLocalTimeNullableAfterOrEq(LocalTime.of(11, 3)))
            .toHaveSize(1)
            .toContain(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfterOrEq finds localTimeWithoutNullable when equals`() {
        expect(repository.selectAllByLocalTimeNullableAfterOrEq(LocalTime.of(11, 4)))
            .toHaveSize(1)
            .toContain(localTimeWithNullable)
    }
}
