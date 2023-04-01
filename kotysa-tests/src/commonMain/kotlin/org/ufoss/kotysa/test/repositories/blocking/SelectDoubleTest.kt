/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import ch.tutteli.atrium.api.fluent.en_GB.toBeEmpty
import ch.tutteli.atrium.api.fluent.en_GB.toContain
import ch.tutteli.atrium.api.fluent.en_GB.toHaveSize
import ch.tutteli.atrium.api.verbs.expect
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.Doubles
import org.ufoss.kotysa.test.doubleWithNullable
import org.ufoss.kotysa.test.doubleWithoutNullable
import org.ufoss.kotysa.transaction.Transaction

interface SelectDoubleTest<T : Doubles, U : SelectDoubleRepository<T>, V : Transaction> : RepositoryTest<U, V> {

    @Test
    fun `Verify selectAllByDoubleNotNull finds doubleWithNullable`() {
        expect(repository.selectAllByDoubleNotNull(doubleWithNullable.doubleNotNull))
            .toHaveSize(1)
            .toContain(doubleWithNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNotNullNotEq finds doubleWithoutNullable`() {
        expect(repository.selectAllByDoubleNotNullNotEq(doubleWithNullable.doubleNotNull))
            .toHaveSize(1)
            .toContain(doubleWithoutNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNotNullIn finds both`() {
        val seq = sequenceOf(doubleWithNullable.doubleNotNull, doubleWithoutNullable.doubleNotNull)
        expect(repository.selectAllByDoubleNotNullIn(seq))
            .toHaveSize(2)
            .toContain(doubleWithNullable, doubleWithoutNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNotNullInf finds doubleWithNullable`() {
        expect(repository.selectAllByDoubleNotNullInf(1.2))
            .toHaveSize(1)
            .toContain(doubleWithNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNotNullInf finds no results when equals`() {
        expect(repository.selectAllByDoubleNotNullInf(doubleWithNullable.doubleNotNull))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByDoubleNotNullInfOrEq finds doubleWithNullable`() {
        expect(repository.selectAllByDoubleNotNullInfOrEq(1.2))
            .toHaveSize(1)
            .toContain(doubleWithNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNotNullInfOrEq finds doubleWithNullable when equals`() {
        expect(repository.selectAllByDoubleNotNullInfOrEq(doubleWithNullable.doubleNotNull))
            .toHaveSize(1)
            .toContain(doubleWithNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNotNullSup finds doubleWithoutNullable`() {
        expect(repository.selectAllByDoubleNotNullSup(doubleWithNullable.doubleNotNull))
            .toHaveSize(1)
            .toContain(doubleWithoutNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNotNullSup finds no results when equals`() {
        expect(repository.selectAllByDoubleNotNullSup(doubleWithoutNullable.doubleNotNull))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByDoubleNotNullSupOrEq finds doubleWithoutNullable`() {
        expect(repository.selectAllByDoubleNotNullSupOrEq(1.2))
            .toHaveSize(1)
            .toContain(doubleWithoutNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNotNullSupOrEq finds doubleWithoutNullable when equals`() {
        expect(repository.selectAllByDoubleNotNullSupOrEq(doubleWithoutNullable.doubleNotNull))
            .toHaveSize(1)
            .toContain(doubleWithoutNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNullable finds doubleWithNullable`() {
        expect(repository.selectAllByDoubleNullable(doubleWithNullable.doubleNullable))
            .toHaveSize(1)
            .toContain(doubleWithNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNullable finds doubleWithoutNullable`() {
        expect(repository.selectAllByDoubleNullable(null))
            .toHaveSize(1)
            .toContain(doubleWithoutNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNullableNotEq finds nothing`() {
        expect(repository.selectAllByDoubleNullableNotEq(doubleWithNullable.doubleNullable))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByDoubleNullableNotEq finds no results`() {
        expect(repository.selectAllByDoubleNullableNotEq(null))
            .toHaveSize(1)
            .toContain(doubleWithNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNullableInf finds doubleWithNullable`() {
        expect(repository.selectAllByDoubleNullableInf(2.3))
            .toHaveSize(1)
            .toContain(doubleWithNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNullableInf finds no results when equals`() {
        expect(repository.selectAllByDoubleNullableInf(doubleWithNullable.doubleNullable!!))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByDoubleNullableInfOrEq finds doubleWithNullable`() {
        expect(repository.selectAllByDoubleNullableInfOrEq(2.3))
            .toHaveSize(1)
            .toContain(doubleWithNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNullableInfOrEq finds doubleWithNullable when equals`() {
        expect(repository.selectAllByDoubleNullableInfOrEq(doubleWithNullable.doubleNullable!!))
            .toHaveSize(1)
            .toContain(doubleWithNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNullableSup finds doubleWithoutNullable`() {
        expect(repository.selectAllByDoubleNullableSup(1.3))
            .toHaveSize(1)
            .toContain(doubleWithNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNullableSup finds no results when equals`() {
        expect(repository.selectAllByDoubleNullableSup(doubleWithNullable.doubleNullable!!))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByDoubleNullableSupOrEq finds doubleWithoutNullable`() {
        expect(repository.selectAllByDoubleNullableSupOrEq(1.3))
            .toHaveSize(1)
            .toContain(doubleWithNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNullableSupOrEq finds doubleWithoutNullable when equals`() {
        expect(repository.selectAllByDoubleNullableSupOrEq(doubleWithNullable.doubleNullable!!))
            .toHaveSize(1)
            .toContain(doubleWithNullable)
    }
}