/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import ch.tutteli.atrium.api.fluent.en_GB.toBeEmpty
import ch.tutteli.atrium.api.fluent.en_GB.toContain
import ch.tutteli.atrium.api.fluent.en_GB.toHaveSize
import ch.tutteli.atrium.api.verbs.expect
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.Floats
import org.ufoss.kotysa.test.floatWithNullable
import org.ufoss.kotysa.test.floatWithoutNullable
import org.ufoss.kotysa.transaction.Transaction

interface SelectFloatTest<T : Floats, U : SelectFloatRepository<T>, V : Transaction> : RepositoryTest<U, V> {

    @Test
    fun `Verify selectAllByFloatNotNull finds floatWithNullable`() {
        expect(repository.selectAllByFloatNotNull(floatWithNullable.floatNotNull))
            .toHaveSize(1)
            .toContain(floatWithNullable)
    }

    @Test
    fun `Verify selectAllByFloatNotNullNotEq finds floatWithoutNullable`() {
        expect(repository.selectAllByFloatNotNullNotEq(floatWithNullable.floatNotNull))
            .toHaveSize(1)
            .toContain(floatWithoutNullable)
    }

    @Test
    fun `Verify selectAllByFloatNotNullIn finds both`() {
        val seq = sequenceOf(floatWithNullable.floatNotNull, floatWithoutNullable.floatNotNull)
        expect(repository.selectAllByFloatNotNullIn(seq))
            .toHaveSize(2)
            .toContain(floatWithNullable, floatWithoutNullable)
    }

    @Test
    fun `Verify selectAllByFloatNotNullInf finds floatWithNullable`() {
        expect(repository.selectAllByFloatNotNullInf(1.2f))
            .toHaveSize(1)
            .toContain(floatWithNullable)
    }

    @Test
    fun `Verify selectAllByFloatNotNullInf finds no results when equals`() {
        expect(repository.selectAllByFloatNotNullInf(floatWithNullable.floatNotNull))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByFloatNotNullInfOrEq finds floatWithNullable`() {
        expect(repository.selectAllByFloatNotNullInfOrEq(1.2f))
            .toHaveSize(1)
            .toContain(floatWithNullable)
    }

    @Test
    fun `Verify selectAllByFloatNotNullInfOrEq finds floatWithNullable when equals`() {
        expect(repository.selectAllByFloatNotNullInfOrEq(floatWithNullable.floatNotNull))
            .toHaveSize(1)
            .toContain(floatWithNullable)
    }

    @Test
    fun `Verify selectAllByFloatNotNullSup finds floatWithoutNullable`() {
        expect(repository.selectAllByFloatNotNullSup(floatWithNullable.floatNotNull))
            .toHaveSize(1)
            .toContain(floatWithoutNullable)
    }

    @Test
    fun `Verify selectAllByFloatNotNullSup finds no results when equals`() {
        expect(repository.selectAllByFloatNotNullSup(floatWithoutNullable.floatNotNull))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByFloatNotNullSupOrEq finds floatWithoutNullable`() {
        expect(repository.selectAllByFloatNotNullSupOrEq(1.2f))
            .toHaveSize(1)
            .toContain(floatWithoutNullable)
    }

    @Test
    fun `Verify selectAllByFloatNotNullSupOrEq finds floatWithoutNullable when equals`() {
        expect(repository.selectAllByFloatNotNullSupOrEq(floatWithoutNullable.floatNotNull))
            .toHaveSize(1)
            .toContain(floatWithoutNullable)
    }

    @Test
    fun `Verify selectAllByFloatNullable finds floatWithNullable`() {
        expect(repository.selectAllByFloatNullable(floatWithNullable.floatNullable))
            .toHaveSize(1)
            .toContain(floatWithNullable)
    }

    @Test
    fun `Verify selectAllByFloatNullable finds floatWithoutNullable`() {
        expect(repository.selectAllByFloatNullable(null))
            .toHaveSize(1)
            .toContain(floatWithoutNullable)
    }

    @Test
    fun `Verify selectAllByFloatNullableNotEq finds nothing`() {
        expect(repository.selectAllByFloatNullableNotEq(floatWithNullable.floatNullable))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByFloatNullableNotEq finds no results`() {
        expect(repository.selectAllByFloatNullableNotEq(null))
            .toHaveSize(1)
            .toContain(floatWithNullable)
    }

    @Test
    fun `Verify selectAllByFloatNullableInf finds floatWithNullable`() {
        expect(repository.selectAllByFloatNullableInf(2.3f))
            .toHaveSize(1)
            .toContain(floatWithNullable)
    }

    @Test
    fun `Verify selectAllByFloatNullableInf finds no results when equals`() {
        expect(repository.selectAllByFloatNullableInf(floatWithNullable.floatNullable!!))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByFloatNullableInfOrEq finds floatWithNullable`() {
        expect(repository.selectAllByFloatNullableInfOrEq(2.3f))
            .toHaveSize(1)
            .toContain(floatWithNullable)
    }

    @Test
    fun `Verify selectAllByFloatNullableInfOrEq finds floatWithNullable when equals`() {
        expect(repository.selectAllByFloatNullableInfOrEq(floatWithNullable.floatNullable!!))
            .toHaveSize(1)
            .toContain(floatWithNullable)
    }

    @Test
    fun `Verify selectAllByFloatNullableSup finds floatWithoutNullable`() {
        expect(repository.selectAllByFloatNullableSup(1.3f))
            .toHaveSize(1)
            .toContain(floatWithNullable)
    }

    @Test
    fun `Verify selectAllByFloatNullableSup finds no results when equals`() {
        expect(repository.selectAllByFloatNullableSup(floatWithNullable.floatNullable!!))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByFloatNullableSupOrEq finds floatWithoutNullable`() {
        expect(repository.selectAllByFloatNullableSupOrEq(1.3f))
            .toHaveSize(1)
            .toContain(floatWithNullable)
    }

    @Test
    fun `Verify selectAllByFloatNullableSupOrEq finds floatWithoutNullable when equals`() {
        expect(repository.selectAllByFloatNullableSupOrEq(floatWithNullable.floatNullable!!))
            .toHaveSize(1)
            .toContain(floatWithNullable)
    }
}