/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import ch.tutteli.atrium.api.fluent.en_GB.toBeEmpty
import ch.tutteli.atrium.api.fluent.en_GB.toContain
import ch.tutteli.atrium.api.fluent.en_GB.toHaveSize
import kotlinx.datetime.LocalDate
import ch.tutteli.atrium.api.verbs.expect
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.KotlinxLocalDates
import org.ufoss.kotysa.test.kotlinxLocalDateWithNullable
import org.ufoss.kotysa.test.kotlinxLocalDateWithoutNullable
import org.ufoss.kotysa.transaction.Transaction

interface SelectKotlinxLocalDateTest<T : KotlinxLocalDates, U : SelectKotlinxLocalDateRepository<T>, V : Transaction> :
    RepositoryTest<U, V> {

    @Test
    fun `Verify selectAllByLocalDateNotNull finds kotlinxLocalDateWithNullable`() {
        expect(repository.selectAllByLocalDateNotNull(LocalDate(2019, 11, 4)))
            .toHaveSize(1)
            .toContain(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullNotEq finds kotlinxLocalDateWithoutNullable`() {
        expect(repository.selectAllByLocalDateNotNullNotEq(LocalDate(2019, 11, 4)))
            .toHaveSize(1)
            .toContain(kotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullIn finds both`() {
        val seq = sequenceOf(
            kotlinxLocalDateWithNullable.localDateNotNull,
            kotlinxLocalDateWithoutNullable.localDateNotNull
        )
        expect(repository.selectAllByLocalDateNotNullIn(seq))
            .toHaveSize(2)
            .toContain(kotlinxLocalDateWithNullable, kotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds kotlinxLocalDateWithNullable`() {
        expect(repository.selectAllByLocalDateNotNullBefore(LocalDate(2019, 11, 5)))
            .toHaveSize(1)
            .toContain(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds no results when equals`() {
        expect(repository.selectAllByLocalDateNotNullBefore(LocalDate(2019, 11, 4)))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds kotlinxLocalDateWithNullable`() {
        expect(repository.selectAllByLocalDateNotNullBeforeOrEq(LocalDate(2019, 11, 5)))
            .toHaveSize(1)
            .toContain(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds kotlinxLocalDateWithNullable when equals`() {
        expect(repository.selectAllByLocalDateNotNullBeforeOrEq(LocalDate(2019, 11, 4)))
            .toHaveSize(1)
            .toContain(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds kotlinxLocalDateWithoutNullable`() {
        expect(repository.selectAllByLocalDateNotNullAfter(LocalDate(2019, 11, 5)))
            .toHaveSize(1)
            .toContain(kotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds no results when equals`() {
        expect(repository.selectAllByLocalDateNotNullAfter(LocalDate(2019, 11, 6)))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds kotlinxLocalDateWithoutNullable`() {
        expect(repository.selectAllByLocalDateNotNullAfterOrEq(LocalDate(2019, 11, 5)))
            .toHaveSize(1)
            .toContain(kotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds kotlinxLocalDateWithoutNullable when equals`() {
        expect(repository.selectAllByLocalDateNotNullAfterOrEq(LocalDate(2019, 11, 6)))
            .toHaveSize(1)
            .toContain(kotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds h2UuidWithNullable`() {
        expect(repository.selectAllByLocalDateNullable(LocalDate(2018, 11, 4)))
            .toHaveSize(1)
            .toContain(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds h2UuidWithoutNullable`() {
        expect(repository.selectAllByLocalDateNullable(null))
            .toHaveSize(1)
            .toContain(kotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds h2UuidWithoutNullable`() {
        expect(repository.selectAllByLocalDateNullableNotEq(LocalDate(2018, 11, 4)))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds no results`() {
        expect(repository.selectAllByLocalDateNullableNotEq(null))
            .toHaveSize(1)
            .toContain(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds kotlinxLocalDateWithNullable`() {
        expect(repository.selectAllByLocalDateNullableBefore(LocalDate(2018, 11, 5)))
            .toHaveSize(1)
            .toContain(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds no results when equals`() {
        expect(repository.selectAllByLocalDateNullableBefore(LocalDate(2018, 11, 4)))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds kotlinxLocalDateWithNullable`() {
        expect(repository.selectAllByLocalDateNullableBeforeOrEq(LocalDate(2018, 11, 5)))
            .toHaveSize(1)
            .toContain(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds kotlinxLocalDateWithNullable when equals`() {
        expect(repository.selectAllByLocalDateNullableBeforeOrEq(LocalDate(2018, 11, 4)))
            .toHaveSize(1)
            .toContain(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds kotlinxLocalDateWithoutNullable`() {
        expect(repository.selectAllByLocalDateNullableAfter(LocalDate(2018, 11, 3)))
            .toHaveSize(1)
            .toContain(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds no results when equals`() {
        expect(repository.selectAllByLocalDateNullableAfter(LocalDate(2018, 11, 4)))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds kotlinxLocalDateWithoutNullable`() {
        expect(repository.selectAllByLocalDateNullableAfterOrEq(LocalDate(2018, 11, 3)))
            .toHaveSize(1)
            .toContain(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds kotlinxLocalDateWithoutNullable when equals`() {
        expect(repository.selectAllByLocalDateNullableAfterOrEq(LocalDate(2018, 11, 4)))
            .toHaveSize(1)
            .toContain(kotlinxLocalDateWithNullable)
    }
}
