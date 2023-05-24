package org.ufoss.kotysa.test.repositories.reactor

import org.assertj.core.api.Assertions.assertThat
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

interface ReactorSelectIntAsIdentitiesTest<T : IntAsIdentities, U : ReactorSelectIntAsIdentitiesRepository<T>,
        V : Transaction> : ReactorRepositoryTest<U, V> {

    @Test
    fun `Verify selectAllByIntNotNull finds intAsIdentityWithNullable`() {
        assertThat(repository.selectAllByIntNotNull(10).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullNotEq finds intAsIdentityWithoutNullable`() {
        assertThat(repository.selectAllByIntNotNullNotEq(10).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullIn finds both`() {
        val seq = sequenceOf(intAsIdentityWithNullable.intNotNull, intAsIdentityWithoutNullable.intNotNull)
        assertThat(repository.selectAllByIntNotNullIn(seq).toIterable())
            .hasSize(2)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable, intAsIdentityWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullInf finds intAsIdentityWithNullable`() {
        assertThat(repository.selectAllByIntNotNullInf(11).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullInf finds no results when equals`() {
        assertThat(repository.selectAllByIntNotNullInf(10).toIterable())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNotNullInfOrEq finds intAsIdentityWithNullable`() {
        assertThat(repository.selectAllByIntNotNullInfOrEq(11).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullInfOrEq finds intAsIdentityWithNullable when equals`() {
        assertThat(repository.selectAllByIntNotNullInfOrEq(10).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullSup finds intAsIdentityWithoutNullable`() {
        assertThat(repository.selectAllByIntNotNullSup(11).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullSup finds no results when equals`() {
        assertThat(repository.selectAllByIntNotNullSup(12).toIterable())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNotNullSupOrEq finds intAsIdentityWithoutNullable`() {
        assertThat(repository.selectAllByIntNotNullSupOrEq(11).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullSupOrEq finds intAsIdentityWithoutNullable when equals`() {
        assertThat(repository.selectAllByIntNotNullSupOrEq(12).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNullable finds intAsIdentityWithNullable`() {
        assertThat(repository.selectAllByIntNullable(6).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullable finds intAsIdentityWithoutNullable`() {
        assertThat(repository.selectAllByIntNullable(null).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableNotEq finds nothing`() {
        assertThat(repository.selectAllByIntNullableNotEq(6).toIterable())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNullableNotEq finds no results`() {
        assertThat(repository.selectAllByIntNullableNotEq(null).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableInf finds intAsIdentityWithNullable`() {
        assertThat(repository.selectAllByIntNullableInf(7).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableInf finds no results when equals`() {
        assertThat(repository.selectAllByIntNullableInf(6).toIterable())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNullableInfOrEq finds intAsIdentityWithNullable`() {
        assertThat(repository.selectAllByIntNullableInfOrEq(7).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableInfOrEq finds intAsIdentityWithNullable when equals`() {
        assertThat(repository.selectAllByIntNullableInfOrEq(6).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableSup finds intAsIdentityWithoutNullable`() {
        assertThat(repository.selectAllByIntNullableSup(5).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableSup finds no results when equals`() {
        assertThat(repository.selectAllByIntNullableSup(6).toIterable())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNullableSupOrEq finds intAsIdentityWithoutNullable`() {
        assertThat(repository.selectAllByIntNullableSupOrEq(5).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableSupOrEq finds intAsIdentityWithoutNullable when equals`() {
        assertThat(repository.selectAllByIntNullableSupOrEq(6).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }
}