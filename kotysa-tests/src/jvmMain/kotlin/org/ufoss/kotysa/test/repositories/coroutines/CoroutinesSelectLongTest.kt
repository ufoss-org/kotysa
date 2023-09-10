package org.ufoss.kotysa.test.repositories.coroutines

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.Longs
import org.ufoss.kotysa.transaction.Transaction

interface CoroutinesSelectLongTest<T : Longs, U : CoroutinesSelectLongRepository<T>, V : Transaction> :
    CoroutinesRepositoryTest<U, V> {

    private val longWithNullable get() = repository.generatedLongWithNullable
    private val longWithoutNullable get() = repository.generatedLongWithoutNullable

    @Test
    fun `Verify selectAllByLongNotNull finds longWithNullable`() = runTest {
        assertThat(repository.selectAllByLongNotNull(10).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullNotEq finds longWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLongNotNullNotEq(10).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullIn finds both`() = runTest {
        val seq = sequenceOf(longWithNullable.longNotNull, longWithoutNullable.longNotNull)
        assertThat(repository.selectAllByLongNotNullIn(seq).toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(longWithNullable, longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullInf finds longWithNullable`() = runTest {
        assertThat(repository.selectAllByLongNotNullInf(11).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullInf finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLongNotNullInf(10).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLongNotNullInfOrEq finds longWithNullable`() = runTest {
        assertThat(repository.selectAllByLongNotNullInfOrEq(11).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullInfOrEq finds longWithNullable when equals`() = runTest {
        assertThat(repository.selectAllByLongNotNullInfOrEq(10).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullSup finds longWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLongNotNullSup(11).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullSup finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLongNotNullSup(12).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLongNotNullSupOrEq finds longWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLongNotNullSupOrEq(11).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullSupOrEq finds longWithoutNullable when equals`() = runTest {
        assertThat(repository.selectAllByLongNotNullSupOrEq(12).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNullable finds longWithNullable`() = runTest {
        assertThat(repository.selectAllByLongNullable(6).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullable finds longWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLongNullable(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableNotEq finds no results`() = runTest {
        assertThat(repository.selectAllByLongNullableNotEq(6).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLongNullableNotEq finds longWithNullable`() = runTest {
        assertThat(repository.selectAllByLongNullableNotEq(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableInf finds longWithNullable`() = runTest {
        assertThat(repository.selectAllByLongNullableInf(7).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableInf finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLongNullableInf(6).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLongNullableInfOrEq finds longWithNullable`() = runTest {
        assertThat(repository.selectAllByLongNullableInfOrEq(7).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableInfOrEq finds longWithNullable when equals`() = runTest {
        assertThat(repository.selectAllByLongNullableInfOrEq(6).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableSup finds longWithNullable`() = runTest {
        assertThat(repository.selectAllByLongNullableSup(5).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableSup finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLongNullableSup(6).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLongNullableSupOrEq finds longWithNullable`() = runTest {
        assertThat(repository.selectAllByLongNullableSupOrEq(5).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableSupOrEq finds longWithNullable when equals`() = runTest {
        assertThat(repository.selectAllByLongNullableSupOrEq(6).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }
}