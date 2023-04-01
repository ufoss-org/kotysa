package org.ufoss.kotysa.test.repositories.blocking

import ch.tutteli.atrium.api.fluent.en_GB.toBeEmpty
import ch.tutteli.atrium.api.fluent.en_GB.toContain
import ch.tutteli.atrium.api.fluent.en_GB.toHaveSize
import ch.tutteli.atrium.api.verbs.expect
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.LongEntity
import org.ufoss.kotysa.test.Longs
import org.ufoss.kotysa.transaction.Transaction

private val longWithNullable = LongEntity(
    org.ufoss.kotysa.test.longWithNullable.longNotNull,
    org.ufoss.kotysa.test.longWithNullable.longNullable,
    1
)

private val longWithoutNullable = LongEntity(
    org.ufoss.kotysa.test.longWithoutNullable.longNotNull,
    org.ufoss.kotysa.test.longWithoutNullable.longNullable,
    2
)

interface SelectLongTest<T : Longs, U : SelectLongRepository<T>, V : Transaction> : RepositoryTest<U, V> {

    @Test
    fun `Verify selectAllByLongNotNull finds longWithNullable`() {
        expect(repository.selectAllByLongNotNull(10))
            .toHaveSize(1)
            .toContain(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullNotEq finds longWithoutNullable`() {
        expect(repository.selectAllByLongNotNullNotEq(10))
            .toHaveSize(1)
            .toContain(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullIn finds both`() {
        val seq = sequenceOf(longWithNullable.longNotNull, longWithoutNullable.longNotNull)
        expect(repository.selectAllByLongNotNullIn(seq))
            .toHaveSize(2)
            .toContain(longWithNullable, longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullInf finds longWithNullable`() {
        expect(repository.selectAllByLongNotNullInf(11))
            .toHaveSize(1)
            .toContain(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullInf finds no results when equals`() {
        expect(repository.selectAllByLongNotNullInf(10))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByLongNotNullInfOrEq finds longWithNullable`() {
        expect(repository.selectAllByLongNotNullInfOrEq(11))
            .toHaveSize(1)
            .toContain(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullInfOrEq finds longWithNullable when equals`() {
        expect(repository.selectAllByLongNotNullInfOrEq(10))
            .toHaveSize(1)
            .toContain(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullSup finds longWithoutNullable`() {
        expect(repository.selectAllByLongNotNullSup(11))
            .toHaveSize(1)
            .toContain(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullSup finds no results when equals`() {
        expect(repository.selectAllByLongNotNullSup(12))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByLongNotNullSupOrEq finds longWithoutNullable`() {
        expect(repository.selectAllByLongNotNullSupOrEq(11))
            .toHaveSize(1)
            .toContain(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullSupOrEq finds longWithoutNullable when equals`() {
        expect(repository.selectAllByLongNotNullSupOrEq(12))
            .toHaveSize(1)
            .toContain(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNullable finds longWithNullable`() {
        expect(repository.selectAllByLongNullable(6))
            .toHaveSize(1)
            .toContain(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullable finds longWithoutNullable`() {
        expect(repository.selectAllByLongNullable(null))
            .toHaveSize(1)
            .toContain(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableNotEq finds no results`() {
        expect(repository.selectAllByLongNullableNotEq(6))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByLongNullableNotEq finds longWithNullable`() {
        expect(repository.selectAllByLongNullableNotEq(null))
            .toHaveSize(1)
            .toContain(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableInf finds longWithNullable`() {
        expect(repository.selectAllByLongNullableInf(7))
            .toHaveSize(1)
            .toContain(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableInf finds no results when equals`() {
        expect(repository.selectAllByLongNullableInf(6))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByLongNullableInfOrEq finds longWithNullable`() {
        expect(repository.selectAllByLongNullableInfOrEq(7))
            .toHaveSize(1)
            .toContain(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableInfOrEq finds longWithNullable when equals`() {
        expect(repository.selectAllByLongNullableInfOrEq(6))
            .toHaveSize(1)
            .toContain(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableSup finds longWithNullable`() {
        expect(repository.selectAllByLongNullableSup(5))
            .toHaveSize(1)
            .toContain(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableSup finds no results when equals`() {
        expect(repository.selectAllByLongNullableSup(6))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByLongNullableSupOrEq finds longWithNullable`() {
        expect(repository.selectAllByLongNullableSupOrEq(5))
            .toHaveSize(1)
            .toContain(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableSupOrEq finds longWithNullable when equals`() {
        expect(repository.selectAllByLongNullableSupOrEq(6))
            .toHaveSize(1)
            .toContain(longWithNullable)
    }
}