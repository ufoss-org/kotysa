/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.BigDecimalAsNumerics
import org.ufoss.kotysa.test.bigDecimalAsNumericWithNullable
import org.ufoss.kotysa.test.bigDecimalAsNumericWithoutNullable
import org.ufoss.kotysa.transaction.Transaction
import java.math.BigDecimal

interface CoroutinesSelectBigDecimalAsNumericTest<T : BigDecimalAsNumerics,
        U : CoroutinesSelectBigDecimalAsNumericRepository<T>, V : Transaction> : CoroutinesRepositoryTest<U, V> {

    @Test
    fun `Verify selectAllByBigDecimalNotNull finds bigDecimalAsNumericWithNullable`() = runTest {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNotNull(bigDecimalAsNumericWithNullable.bigDecimalNotNull).toList()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullNotEq finds bigDecimalAsNumericWithoutNullable`() = runTest {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNotNullNotEq(bigDecimalAsNumericWithNullable.bigDecimalNotNull).toList()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithoutNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullIn finds both`() = runTest {
        val seq = sequenceOf(
            bigDecimalAsNumericWithNullable.bigDecimalNotNull,
            bigDecimalAsNumericWithoutNullable.bigDecimalNotNull
        )
        Assertions.assertThat(repository.selectAllByBigDecimalNotNullIn(seq).toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithNullable, bigDecimalAsNumericWithoutNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullInf finds bigDecimalAsNumericWithNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByBigDecimalNotNullInf(BigDecimal("1.2")).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullInf finds no results when equals`() = runTest {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNotNullInf(bigDecimalAsNumericWithNullable.bigDecimalNotNull).toList()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullInfOrEq finds bigDecimalAsNumericWithNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByBigDecimalNotNullInfOrEq(BigDecimal("1.2")).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullInfOrEq finds bigDecimalAsNumericWithNullable when equals`() = runTest {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNotNullInfOrEq(bigDecimalAsNumericWithNullable.bigDecimalNotNull).toList()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullSup finds bigDecimalAsNumericWithoutNullable`() = runTest {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNotNullSup(bigDecimalAsNumericWithNullable.bigDecimalNotNull).toList()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithoutNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullSup finds no results when equals`() = runTest {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNotNullSup(bigDecimalAsNumericWithoutNullable.bigDecimalNotNull).toList()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullSupOrEq finds bigDecimalAsNumericWithoutNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByBigDecimalNotNullSupOrEq(BigDecimal("1.2")).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithoutNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullSupOrEq finds bigDecimalAsNumericWithoutNullable when equals`() = runTest {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNotNullSupOrEq(bigDecimalAsNumericWithoutNullable.bigDecimalNotNull)
                .toList()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithoutNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullable finds bigDecimalAsNumericWithNullable`() = runTest {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNullable(bigDecimalAsNumericWithNullable.bigDecimalNullable).toList()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullable finds bigDecimalAsNumericWithoutNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByBigDecimalNullable(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithoutNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableNotEq finds nothing`() = runTest {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNullableNotEq(bigDecimalAsNumericWithNullable.bigDecimalNullable).toList()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableNotEq finds no results`() = runTest {
        Assertions.assertThat(repository.selectAllByBigDecimalNullableNotEq(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableInf finds bigDecimalAsNumericWithNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByBigDecimalNullableInf(BigDecimal("2.3")).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableInf finds no results when equals`() = runTest {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNullableInf(bigDecimalAsNumericWithNullable.bigDecimalNullable!!).toList()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableInfOrEq finds bigDecimalAsNumericWithNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByBigDecimalNullableInfOrEq(BigDecimal("2.3")).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableInfOrEq finds bigDecimalAsNumericWithNullable when equals`() = runTest {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNullableInfOrEq(bigDecimalAsNumericWithNullable.bigDecimalNullable!!)
                .toList()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableSup finds bigDecimalAsNumericWithoutNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByBigDecimalNullableSup(BigDecimal("1.3")).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableSup finds no results when equals`() = runTest {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNullableSup(bigDecimalAsNumericWithNullable.bigDecimalNullable!!).toList()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableSupOrEq finds bigDecimalAsNumericWithoutNullable`() = runTest {
        Assertions.assertThat(repository.selectAllByBigDecimalNullableSupOrEq(BigDecimal("1.3")).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableSupOrEq finds bigDecimalAsNumericWithoutNullable when equals`() = runTest {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNullableSupOrEq(bigDecimalAsNumericWithNullable.bigDecimalNullable!!)
                .toList()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithNullable)
    }
}