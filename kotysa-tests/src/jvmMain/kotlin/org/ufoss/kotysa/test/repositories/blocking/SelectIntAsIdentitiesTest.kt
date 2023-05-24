package org.ufoss.kotysa.test.repositories.blocking

import ch.tutteli.atrium.api.fluent.en_GB.toBeEmpty
import ch.tutteli.atrium.api.fluent.en_GB.toContain
import ch.tutteli.atrium.api.fluent.en_GB.toHaveSize
import ch.tutteli.atrium.api.verbs.expect
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.IntAsIdentities
import org.ufoss.kotysa.test.IntEntityAsIdentity
import org.ufoss.kotysa.transaction.Transaction

private val intAsIdentityWithNullable = IntEntityAsIdentity(
    org.ufoss.kotysa.test.intAsIdentityWithNullable.intNotNull,
    org.ufoss.kotysa.test.intAsIdentityWithNullable.intNullable,
    1
)

private val intAsIdentityWithoutNullable = IntEntityAsIdentity(
    org.ufoss.kotysa.test.intAsIdentityWithoutNullable.intNotNull,
    org.ufoss.kotysa.test.intAsIdentityWithoutNullable.intNullable,
    2
)

interface SelectIntAsIdentitiesTest<T : IntAsIdentities, U : SelectIntAsIdentitiesRepository<T>, V : Transaction> :
    RepositoryTest<U, V> {

    @Test
    fun `Verify selectAllByIntNotNull finds intAsIdentityWithNullable`() {
        expect(repository.selectAllByIntNotNull(10))
            .toHaveSize(1)
            .toContain(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullNotEq finds intAsIdentityWithoutNullable`() {
        expect(repository.selectAllByIntNotNullNotEq(10))
            .toHaveSize(1)
            .toContain(intAsIdentityWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullIn finds both`() {
        val seq = sequenceOf(intAsIdentityWithNullable.intNotNull, intAsIdentityWithoutNullable.intNotNull)
        expect(repository.selectAllByIntNotNullIn(seq))
            .toHaveSize(2)
            .toContain(intAsIdentityWithNullable, intAsIdentityWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullInf finds intAsIdentityWithNullable`() {
        expect(repository.selectAllByIntNotNullInf(11))
            .toHaveSize(1)
            .toContain(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullInf finds no results when equals`() {
        expect(repository.selectAllByIntNotNullInf(10))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByIntNotNullInfOrEq finds intAsIdentityWithNullable`() {
        expect(repository.selectAllByIntNotNullInfOrEq(11))
            .toHaveSize(1)
            .toContain(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullInfOrEq finds intAsIdentityWithNullable when equals`() {
        expect(repository.selectAllByIntNotNullInfOrEq(10))
            .toHaveSize(1)
            .toContain(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullSup finds intAsIdentityWithoutNullable`() {
        expect(repository.selectAllByIntNotNullSup(11))
            .toHaveSize(1)
            .toContain(intAsIdentityWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullSup finds no results when equals`() {
        expect(repository.selectAllByIntNotNullSup(12))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByIntNotNullSupOrEq finds intAsIdentityWithoutNullable`() {
        expect(repository.selectAllByIntNotNullSupOrEq(11))
            .toHaveSize(1)
            .toContain(intAsIdentityWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullSupOrEq finds intAsIdentityWithoutNullable when equals`() {
        expect(repository.selectAllByIntNotNullSupOrEq(12))
            .toHaveSize(1)
            .toContain(intAsIdentityWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNullable finds intAsIdentityWithNullable`() {
        expect(repository.selectAllByIntNullable(6))
            .toHaveSize(1)
            .toContain(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullable finds intAsIdentityWithoutNullable`() {
        expect(repository.selectAllByIntNullable(null))
            .toHaveSize(1)
            .toContain(intAsIdentityWithoutNullable)
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
            .toContain(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableInf finds intAsIdentityWithNullable`() {
        expect(repository.selectAllByIntNullableInf(7))
            .toHaveSize(1)
            .toContain(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableInf finds no results when equals`() {
        expect(repository.selectAllByIntNullableInf(6))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByIntNullableInfOrEq finds intAsIdentityWithNullable`() {
        expect(repository.selectAllByIntNullableInfOrEq(7))
            .toHaveSize(1)
            .toContain(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableInfOrEq finds intAsIdentityWithNullable when equals`() {
        expect(repository.selectAllByIntNullableInfOrEq(6))
            .toHaveSize(1)
            .toContain(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableSup finds intAsIdentityWithoutNullable`() {
        expect(repository.selectAllByIntNullableSup(5))
            .toHaveSize(1)
            .toContain(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableSup finds no results when equals`() {
        expect(repository.selectAllByIntNullableSup(6))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByIntNullableSupOrEq finds intAsIdentityWithoutNullable`() {
        expect(repository.selectAllByIntNullableSupOrEq(5))
            .toHaveSize(1)
            .toContain(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableSupOrEq finds intAsIdentityWithoutNullable when equals`() {
        expect(repository.selectAllByIntNullableSupOrEq(6))
            .toHaveSize(1)
            .toContain(intAsIdentityWithNullable)
    }
}