/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import ch.tutteli.atrium.api.fluent.en_GB.toBeEmpty
import ch.tutteli.atrium.api.fluent.en_GB.toContain
import ch.tutteli.atrium.api.fluent.en_GB.toHaveSize
import ch.tutteli.atrium.api.verbs.expect
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.transaction.Transaction

private val intWithNullable = IntEntity(
    org.ufoss.kotysa.test.intWithNullable.intNotNull,
    org.ufoss.kotysa.test.intWithNullable.intNullable,
    1
)

private val intWithoutNullable = IntEntity(
    org.ufoss.kotysa.test.intWithoutNullable.intNotNull,
    org.ufoss.kotysa.test.intWithoutNullable.intNullable,
    2
)

interface SelectIntTest<T : Ints, U : SelectIntRepository<T>, V : Transaction> : RepositoryTest<U, V> {

    @Test
    fun `Verify selectAllByIntNotNull finds intWithNullable`() {
        expect(repository.selectAllByIntNotNull(10))
            .toHaveSize(1)
            .toContain(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullNotEq finds intWithoutNullable`() {
        expect(repository.selectAllByIntNotNullNotEq(10))
            .toHaveSize(1)
            .toContain(intWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullIn finds both`() {
        val seq = sequenceOf(intWithNullable.intNotNull, intWithoutNullable.intNotNull)
        expect(repository.selectAllByIntNotNullIn(seq))
            .toHaveSize(2)
            .toContain(intWithNullable, intWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullInf finds intWithNullable`() {
        expect(repository.selectAllByIntNotNullInf(11))
            .toHaveSize(1)
            .toContain(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullInf finds no results when equals`() {
        expect(repository.selectAllByIntNotNullInf(10))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByIntNotNullInfOrEq finds intWithNullable`() {
        expect(repository.selectAllByIntNotNullInfOrEq(11))
            .toHaveSize(1)
            .toContain(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullInfOrEq finds intWithNullable when equals`() {
        expect(repository.selectAllByIntNotNullInfOrEq(10))
            .toHaveSize(1)
            .toContain(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullSup finds intWithoutNullable`() {
        expect(repository.selectAllByIntNotNullSup(11))
            .toHaveSize(1)
            .toContain(intWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullSup finds no results when equals`() {
        expect(repository.selectAllByIntNotNullSup(12))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByIntNotNullSupOrEq finds intWithoutNullable`() {
        expect(repository.selectAllByIntNotNullSupOrEq(11))
            .toHaveSize(1)
            .toContain(intWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullSupOrEq finds intWithoutNullable when equals`() {
        expect(repository.selectAllByIntNotNullSupOrEq(12))
            .toHaveSize(1)
            .toContain(intWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNullable finds intWithNullable`() {
        expect(repository.selectAllByIntNullable(6))
            .toHaveSize(1)
            .toContain(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullable finds intWithoutNullable`() {
        expect(repository.selectAllByIntNullable(null))
            .toHaveSize(1)
            .toContain(intWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableNotEq finds nothing`() {
        expect(repository.selectAllByIntNullableNotEq(6))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByIntNullableNotEq finds no results`() {
        expect(repository.selectAllByIntNullableNotEq(null))
            .toHaveSize(1)
            .toContain(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableInf finds intWithNullable`() {
        expect(repository.selectAllByIntNullableInf(7))
            .toHaveSize(1)
            .toContain(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableInf finds no results when equals`() {
        expect(repository.selectAllByIntNullableInf(6))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByIntNullableInfOrEq finds intWithNullable`() {
        expect(repository.selectAllByIntNullableInfOrEq(7))
            .toHaveSize(1)
            .toContain(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableInfOrEq finds intWithNullable when equals`() {
        expect(repository.selectAllByIntNullableInfOrEq(6))
            .toHaveSize(1)
            .toContain(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableSup finds intWithoutNullable`() {
        expect(repository.selectAllByIntNullableSup(5))
            .toHaveSize(1)
            .toContain(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableSup finds no results when equals`() {
        expect(repository.selectAllByIntNullableSup(6))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByIntNullableSupOrEq finds intWithoutNullable`() {
        expect(repository.selectAllByIntNullableSupOrEq(5))
            .toHaveSize(1)
            .toContain(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableSupOrEq finds intWithoutNullable when equals`() {
        expect(repository.selectAllByIntNullableSupOrEq(6))
            .toHaveSize(1)
            .toContain(intWithNullable)
    }
}
