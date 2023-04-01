/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import ch.tutteli.atrium.api.fluent.en_GB.toBeEmpty
import ch.tutteli.atrium.api.fluent.en_GB.toContain
import ch.tutteli.atrium.api.fluent.en_GB.toHaveSize
import ch.tutteli.atrium.api.verbs.expect
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.LocalDates
import org.ufoss.kotysa.test.localDateWithNullable
import org.ufoss.kotysa.test.localDateWithoutNullable
import org.ufoss.kotysa.transaction.Transaction
import java.time.LocalDate

interface SelectLocalDateTest<T : LocalDates, U : SelectLocalDateRepository<T>, V : Transaction> :
    RepositoryTest<U, V> {

    @Test
    fun `Verify selectAllByLocalDateNotNull finds localDateWithNullable`() {
        expect(repository.selectAllByLocalDateNotNull(LocalDate.of(2019, 11, 4)))
            .toHaveSize(1)
            .toContain(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullNotEq finds localDateWithoutNullable`() {
        expect(repository.selectAllByLocalDateNotNullNotEq(LocalDate.of(2019, 11, 4)))
            .toHaveSize(1)
            .toContain(localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullIn finds both`() {
        val seq = sequenceOf(
            localDateWithNullable.localDateNotNull,
            localDateWithoutNullable.localDateNotNull)
        expect(repository.selectAllByLocalDateNotNullIn(seq))
            .toHaveSize(2)
            .toContain(localDateWithNullable, localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds localDateWithNullable`() {
        expect(repository.selectAllByLocalDateNotNullBefore(LocalDate.of(2019, 11, 5)))
            .toHaveSize(1)
            .toContain(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds no results when equals`() {
        expect(repository.selectAllByLocalDateNotNullBefore(LocalDate.of(2019, 11, 4)))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds localDateWithNullable`() {
        expect(repository.selectAllByLocalDateNotNullBeforeOrEq(LocalDate.of(2019, 11, 5)))
            .toHaveSize(1)
            .toContain(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds localDateWithNullable when equals`() {
        expect(repository.selectAllByLocalDateNotNullBeforeOrEq(LocalDate.of(2019, 11, 4)))
            .toHaveSize(1)
            .toContain(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds localDateWithoutNullable`() {
        expect(repository.selectAllByLocalDateNotNullAfter(LocalDate.of(2019, 11, 5)))
            .toHaveSize(1)
            .toContain(localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds no results when equals`() {
        expect(repository.selectAllByLocalDateNotNullAfter(LocalDate.of(2019, 11, 6)))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds localDateWithoutNullable`() {
        expect(repository.selectAllByLocalDateNotNullAfterOrEq(LocalDate.of(2019, 11, 5)))
            .toHaveSize(1)
            .toContain(localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds localDateWithoutNullable when equals`() {
        expect(repository.selectAllByLocalDateNotNullAfterOrEq(LocalDate.of(2019, 11, 6)))
            .toHaveSize(1)
            .toContain(localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds h2UuidWithNullable`() {
        expect(repository.selectAllByLocalDateNullable(LocalDate.of(2018, 11, 4)))
            .toHaveSize(1)
            .toContain(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds h2UuidWithoutNullable`() {
        expect(repository.selectAllByLocalDateNullable(null))
            .toHaveSize(1)
            .toContain(localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds h2UuidWithoutNullable`() {
        expect(repository.selectAllByLocalDateNullableNotEq(LocalDate.of(2018, 11, 4)))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds no results`() {
        expect(repository.selectAllByLocalDateNullableNotEq(null))
            .toHaveSize(1)
            .toContain(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds localDateWithNullable`() {
        expect(repository.selectAllByLocalDateNullableBefore(LocalDate.of(2018, 11, 5)))
            .toHaveSize(1)
            .toContain(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds no results when equals`() {
        expect(repository.selectAllByLocalDateNullableBefore(LocalDate.of(2018, 11, 4)))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds localDateWithNullable`() {
        expect(repository.selectAllByLocalDateNullableBeforeOrEq(LocalDate.of(2018, 11, 5)))
            .toHaveSize(1)
            .toContain(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds localDateWithNullable when equals`() {
        expect(repository.selectAllByLocalDateNullableBeforeOrEq(LocalDate.of(2018, 11, 4)))
            .toHaveSize(1)
            .toContain(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds localDateWithoutNullable`() {
        expect(repository.selectAllByLocalDateNullableAfter(LocalDate.of(2018, 11, 3)))
            .toHaveSize(1)
            .toContain(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds no results when equals`() {
        expect(repository.selectAllByLocalDateNullableAfter(LocalDate.of(2018, 11, 4)))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds localDateWithoutNullable`() {
        expect(repository.selectAllByLocalDateNullableAfterOrEq(LocalDate.of(2018, 11, 3)))
            .toHaveSize(1)
            .toContain(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds localDateWithoutNullable when equals`() {
        expect(repository.selectAllByLocalDateNullableAfterOrEq(LocalDate.of(2018, 11, 4)))
            .toHaveSize(1)
            .toContain(localDateWithNullable)
    }
}
