package org.ufoss.kotysa.test.repositories.coroutines

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.IntAsIdentities
import org.ufoss.kotysa.transaction.Transaction

interface CoroutinesSelectIntAsIdentitiesTest<T : IntAsIdentities, U : CoroutinesSelectIntAsIdentitiesRepository<T>,
        V : Transaction> : CoroutinesRepositoryTest<U, V> {

    private val intAsIdentityWithNullable get() = repository.generatedIntAsIdentityWithNullable
    private val intAsIdentityWithoutNullable get() = repository.generatedIntAsIdentityWithoutNullable

    @Test
    fun `Verify selectAllByIntNotNull finds intAsIdentityWithNullable`() = runTest {
        assertThat(repository.selectAllByIntNotNull(10).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullNotEq finds intAsIdentityWithoutNullable`() = runTest {
        assertThat(repository.selectAllByIntNotNullNotEq(10).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullIn finds both`() = runTest {
        val seq = sequenceOf(intAsIdentityWithNullable.intNotNull, intAsIdentityWithoutNullable.intNotNull)
        assertThat(repository.selectAllByIntNotNullIn(seq).toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable, intAsIdentityWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullInf finds intAsIdentityWithNullable`() = runTest {
        assertThat(repository.selectAllByIntNotNullInf(11).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullInf finds no results when equals`() = runTest {
        assertThat(repository.selectAllByIntNotNullInf(10).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNotNullInfOrEq finds intAsIdentityWithNullable`() = runTest {
        assertThat(repository.selectAllByIntNotNullInfOrEq(11).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullInfOrEq finds intAsIdentityWithNullable when equals`() = runTest {
        assertThat(repository.selectAllByIntNotNullInfOrEq(10).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullSup finds intAsIdentityWithoutNullable`() = runTest {
        assertThat(repository.selectAllByIntNotNullSup(11).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullSup finds no results when equals`() = runTest {
        assertThat(repository.selectAllByIntNotNullSup(12).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNotNullSupOrEq finds intAsIdentityWithoutNullable`() = runTest {
        assertThat(repository.selectAllByIntNotNullSupOrEq(11).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullSupOrEq finds intAsIdentityWithoutNullable when equals`() = runTest {
        assertThat(repository.selectAllByIntNotNullSupOrEq(12).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNullable finds intAsIdentityWithNullable`() = runTest {
        assertThat(repository.selectAllByIntNullable(6).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullable finds intAsIdentityWithoutNullable`() = runTest {
        assertThat(repository.selectAllByIntNullable(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableNotEq finds nothing`() = runTest {
        assertThat(repository.selectAllByIntNullableNotEq(6).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNullableNotEq finds no results`() = runTest {
        assertThat(repository.selectAllByIntNullableNotEq(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableInf finds intAsIdentityWithNullable`() = runTest {
        assertThat(repository.selectAllByIntNullableInf(7).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableInf finds no results when equals`() = runTest {
        assertThat(repository.selectAllByIntNullableInf(6).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNullableInfOrEq finds intAsIdentityWithNullable`() = runTest {
        assertThat(repository.selectAllByIntNullableInfOrEq(7).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableInfOrEq finds intAsIdentityWithNullable when equals`() = runTest {
        assertThat(repository.selectAllByIntNullableInfOrEq(6).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableSup finds intAsIdentityWithoutNullable`() = runTest {
        assertThat(repository.selectAllByIntNullableSup(5).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableSup finds no results when equals`() = runTest {
        assertThat(repository.selectAllByIntNullableSup(6).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNullableSupOrEq finds intAsIdentityWithoutNullable`() = runTest {
        assertThat(repository.selectAllByIntNullableSupOrEq(5).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableSupOrEq finds intAsIdentityWithoutNullable when equals`() = runTest {
        assertThat(repository.selectAllByIntNullableSupOrEq(6).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }
}