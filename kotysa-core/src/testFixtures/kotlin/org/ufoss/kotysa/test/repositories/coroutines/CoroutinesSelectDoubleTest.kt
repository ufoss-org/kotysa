/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.Doubles
import org.ufoss.kotysa.test.doubleWithNullable
import org.ufoss.kotysa.test.doubleWithoutNullable
import org.ufoss.kotysa.transaction.Transaction

interface CoroutinesSelectDoubleTest<T : Doubles, U : CoroutinesSelectDoubleRepository<T>, V : Transaction>
    : CoroutinesRepositoryTest<U, V> {

    @Test
    fun `Verify selectAllByDoubleNotNull finds doubleWithNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByDoubleNotNull(doubleWithNullable.doubleNotNull).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(doubleWithNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNotNullNotEq finds doubleWithoutNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByDoubleNotNullNotEq(doubleWithNullable.doubleNotNull).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(doubleWithoutNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNotNullIn finds both`() = runTest {
        val seq = sequenceOf(doubleWithNullable.doubleNotNull, doubleWithoutNullable.doubleNotNull)
        Assertions.assertThat(repository.selectAllByDoubleNotNullIn(seq).toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(doubleWithNullable, doubleWithoutNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNotNullInf finds doubleWithNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByDoubleNotNullInf(1.2).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(doubleWithNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNotNullInf finds no results when equals`() = runTest {
        Assertions.assertThat(repository.selectAllByDoubleNotNullInf(doubleWithNullable.doubleNotNull).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByDoubleNotNullInfOrEq finds doubleWithNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByDoubleNotNullInfOrEq(1.2).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(doubleWithNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNotNullInfOrEq finds doubleWithNullable when equals`() = runTest {
        Assertions.assertThat(repository.selectAllByDoubleNotNullInfOrEq(doubleWithNullable.doubleNotNull).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(doubleWithNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNotNullSup finds doubleWithoutNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByDoubleNotNullSup(doubleWithNullable.doubleNotNull).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(doubleWithoutNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNotNullSup finds no results when equals`() = runTest {
        Assertions.assertThat(repository.selectAllByDoubleNotNullSup(doubleWithoutNullable.doubleNotNull).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByDoubleNotNullSupOrEq finds doubleWithoutNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByDoubleNotNullSupOrEq(1.2).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(doubleWithoutNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNotNullSupOrEq finds doubleWithoutNullable when equals`() = runTest {
        Assertions.assertThat(repository.selectAllByDoubleNotNullSupOrEq(doubleWithoutNullable.doubleNotNull).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(doubleWithoutNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNullable finds doubleWithNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByDoubleNullable(doubleWithNullable.doubleNullable).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(doubleWithNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNullable finds doubleWithoutNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByDoubleNullable(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(doubleWithoutNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNullableNotEq finds nothing`() = runTest {
        Assertions.assertThat(repository.selectAllByDoubleNullableNotEq(doubleWithNullable.doubleNullable).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByDoubleNullableNotEq finds no results`() = runTest {
        Assertions.assertThat(repository.selectAllByDoubleNullableNotEq(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(doubleWithNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNullableInf finds doubleWithNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByDoubleNullableInf(2.3).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(doubleWithNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNullableInf finds no results when equals`() = runTest {
        Assertions.assertThat(repository.selectAllByDoubleNullableInf(doubleWithNullable.doubleNullable!!).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByDoubleNullableInfOrEq finds doubleWithNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByDoubleNullableInfOrEq(2.3).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(doubleWithNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNullableInfOrEq finds doubleWithNullable when equals`() = runTest {
        Assertions.assertThat(repository.selectAllByDoubleNullableInfOrEq(doubleWithNullable.doubleNullable!!).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(doubleWithNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNullableSup finds doubleWithoutNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByDoubleNullableSup(1.3).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(doubleWithNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNullableSup finds no results when equals`() = runTest {
        Assertions.assertThat(repository.selectAllByDoubleNullableSup(doubleWithNullable.doubleNullable!!).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByDoubleNullableSupOrEq finds doubleWithoutNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByDoubleNullableSupOrEq(1.3).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(doubleWithNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNullableSupOrEq finds doubleWithoutNullable when equals`() = runTest {
        Assertions.assertThat(repository.selectAllByDoubleNullableSupOrEq(doubleWithNullable.doubleNullable!!).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(doubleWithNullable)
    }
}