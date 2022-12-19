/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.Floats
import org.ufoss.kotysa.test.floatWithNullable
import org.ufoss.kotysa.test.floatWithoutNullable
import org.ufoss.kotysa.transaction.Transaction

interface CoroutinesSelectFloatTest<T : Floats, U : CoroutinesSelectFloatRepository<T>, V : Transaction>
    : CoroutinesRepositoryTest<U, V> {

    @Test
    fun `Verify selectAllByFloatNotNull finds floatWithNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByFloatNotNull(floatWithNullable.floatNotNull).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(floatWithNullable)
    }

    @Test
    fun `Verify selectAllByFloatNotNullNotEq finds floatWithoutNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByFloatNotNullNotEq(floatWithNullable.floatNotNull).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(floatWithoutNullable)
    }

    @Test
    fun `Verify selectAllByFloatNotNullIn finds both`() = runTest {
        val seq = sequenceOf(floatWithNullable.floatNotNull, floatWithoutNullable.floatNotNull)
        Assertions.assertThat(repository.selectAllByFloatNotNullIn(seq).toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(floatWithNullable, floatWithoutNullable)
    }

    @Test
    fun `Verify selectAllByFloatNotNullInf finds floatWithNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByFloatNotNullInf(1.2f).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(floatWithNullable)
    }

    @Test
    fun `Verify selectAllByFloatNotNullInf finds no results when equals`() = runTest {
        Assertions.assertThat(repository.selectAllByFloatNotNullInf(floatWithNullable.floatNotNull).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByFloatNotNullInfOrEq finds floatWithNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByFloatNotNullInfOrEq(1.2f).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(floatWithNullable)
    }

    @Test
    fun `Verify selectAllByFloatNotNullInfOrEq finds floatWithNullable when equals`() = runTest {
        Assertions.assertThat(repository.selectAllByFloatNotNullInfOrEq(floatWithNullable.floatNotNull).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(floatWithNullable)
    }

    @Test
    fun `Verify selectAllByFloatNotNullSup finds floatWithoutNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByFloatNotNullSup(floatWithNullable.floatNotNull).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(floatWithoutNullable)
    }

    @Test
    fun `Verify selectAllByFloatNotNullSup finds no results when equals`() = runTest {
        Assertions.assertThat(repository.selectAllByFloatNotNullSup(floatWithoutNullable.floatNotNull).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByFloatNotNullSupOrEq finds floatWithoutNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByFloatNotNullSupOrEq(1.2f).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(floatWithoutNullable)
    }

    @Test
    fun `Verify selectAllByFloatNotNullSupOrEq finds floatWithoutNullable when equals`() = runTest {
        Assertions.assertThat(repository.selectAllByFloatNotNullSupOrEq(floatWithoutNullable.floatNotNull).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(floatWithoutNullable)
    }

    @Test
    fun `Verify selectAllByFloatNullable finds floatWithNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByFloatNullable(floatWithNullable.floatNullable).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(floatWithNullable)
    }

    @Test
    fun `Verify selectAllByFloatNullable finds floatWithoutNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByFloatNullable(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(floatWithoutNullable)
    }

    @Test
    fun `Verify selectAllByFloatNullableNotEq finds nothing`() = runTest {
        Assertions.assertThat(repository.selectAllByFloatNullableNotEq(floatWithNullable.floatNullable).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByFloatNullableNotEq finds no results`() = runTest {
        Assertions.assertThat(repository.selectAllByFloatNullableNotEq(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(floatWithNullable)
    }

    @Test
    fun `Verify selectAllByFloatNullableInf finds floatWithNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByFloatNullableInf(2.3f).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(floatWithNullable)
    }

    @Test
    fun `Verify selectAllByFloatNullableInf finds no results when equals`() = runTest {
        Assertions.assertThat(repository.selectAllByFloatNullableInf(floatWithNullable.floatNullable!!).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByFloatNullableInfOrEq finds floatWithNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByFloatNullableInfOrEq(2.3f).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(floatWithNullable)
    }

    @Test
    fun `Verify selectAllByFloatNullableInfOrEq finds floatWithNullable when equals`() = runTest {
        Assertions.assertThat(repository.selectAllByFloatNullableInfOrEq(floatWithNullable.floatNullable!!).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(floatWithNullable)
    }

    @Test
    fun `Verify selectAllByFloatNullableSup finds floatWithoutNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByFloatNullableSup(1.3f).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(floatWithNullable)
    }

    @Test
    fun `Verify selectAllByFloatNullableSup finds no results when equals`() = runTest {
        Assertions.assertThat(repository.selectAllByFloatNullableSup(floatWithNullable.floatNullable!!).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByFloatNullableSupOrEq finds floatWithoutNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByFloatNullableSupOrEq(1.3f).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(floatWithNullable)
    }

    @Test
    fun `Verify selectAllByFloatNullableSupOrEq finds floatWithoutNullable when equals`() = runTest {
        Assertions.assertThat(repository.selectAllByFloatNullableSupOrEq(floatWithNullable.floatNullable!!).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(floatWithNullable)
    }
}