/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.BigDecimals
import org.ufoss.kotysa.test.bigDecimalWithNullable
import org.ufoss.kotysa.test.bigDecimalWithoutNullable
import org.ufoss.kotysa.transaction.Transaction
import java.math.BigDecimal

interface CoroutinesSelectBigDecimalTest<T : BigDecimals, U : CoroutinesSelectBigDecimalRepository<T>, V : Transaction>
    : CoroutinesRepositoryTest<U, V> {

    @Test
    fun `Verify selectAllByBigDecimalNotNull finds bigDecimalWithNullable`() = runTest {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNotNull(bigDecimalWithNullable.bigDecimalNotNull).toList()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullNotEq finds bigDecimalWithoutNullable`() = runTest {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNotNullNotEq(bigDecimalWithNullable.bigDecimalNotNull).toList()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalWithoutNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullIn finds both`() = runTest {
        val seq = sequenceOf(bigDecimalWithNullable.bigDecimalNotNull, bigDecimalWithoutNullable.bigDecimalNotNull)
        Assertions.assertThat(repository.selectAllByBigDecimalNotNullIn(seq).toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(bigDecimalWithNullable, bigDecimalWithoutNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullInf finds bigDecimalWithNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByBigDecimalNotNullInf(BigDecimal("1.2")).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullInf finds no results when equals`() = runTest {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNotNullInf(bigDecimalWithNullable.bigDecimalNotNull).toList()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullInfOrEq finds bigDecimalWithNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByBigDecimalNotNullInfOrEq(BigDecimal("1.2")).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullInfOrEq finds bigDecimalWithNullable when equals`() = runTest {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNotNullInfOrEq(bigDecimalWithNullable.bigDecimalNotNull).toList()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullSup finds bigDecimalWithoutNullable`() = runTest {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNotNullSup(bigDecimalWithNullable.bigDecimalNotNull).toList()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalWithoutNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullSup finds no results when equals`() = runTest {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNotNullSup(bigDecimalWithoutNullable.bigDecimalNotNull).toList()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullSupOrEq finds bigDecimalWithoutNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByBigDecimalNotNullSupOrEq(BigDecimal("1.2")).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalWithoutNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullSupOrEq finds bigDecimalWithoutNullable when equals`() = runTest {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNotNullSupOrEq(bigDecimalWithoutNullable.bigDecimalNotNull).toList()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalWithoutNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullable finds bigDecimalWithNullable`() = runTest {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNullable(bigDecimalWithNullable.bigDecimalNullable).toList()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullable finds bigDecimalWithoutNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByBigDecimalNullable(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalWithoutNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableNotEq finds nothing`() = runTest {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNullableNotEq(bigDecimalWithNullable.bigDecimalNullable).toList()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableNotEq finds no results`() = runTest {
        Assertions.assertThat(repository.selectAllByBigDecimalNullableNotEq(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableInf finds bigDecimalWithNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByBigDecimalNullableInf(BigDecimal("2.3")).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableInf finds no results when equals`() = runTest {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNullableInf(bigDecimalWithNullable.bigDecimalNullable!!).toList()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableInfOrEq finds bigDecimalWithNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByBigDecimalNullableInfOrEq(BigDecimal("2.3")).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableInfOrEq finds bigDecimalWithNullable when equals`() = runTest {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNullableInfOrEq(bigDecimalWithNullable.bigDecimalNullable!!).toList()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableSup finds bigDecimalWithoutNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByBigDecimalNullableSup(BigDecimal("1.3")).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableSup finds no results when equals`() = runTest {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNullableSup(bigDecimalWithNullable.bigDecimalNullable!!).toList()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableSupOrEq finds bigDecimalWithoutNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByBigDecimalNullableSupOrEq(BigDecimal("1.3")).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableSupOrEq finds bigDecimalWithoutNullable when equals`() = runTest {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNullableSupOrEq(bigDecimalWithNullable.bigDecimalNullable!!).toList()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalWithNullable)
    }
}