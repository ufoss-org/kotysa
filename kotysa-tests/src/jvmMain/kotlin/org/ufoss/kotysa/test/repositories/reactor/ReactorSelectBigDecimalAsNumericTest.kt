/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.reactor

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.BigDecimalAsNumerics
import org.ufoss.kotysa.test.bigDecimalAsNumericWithNullable
import org.ufoss.kotysa.test.bigDecimalAsNumericWithoutNullable
import org.ufoss.kotysa.transaction.Transaction
import java.math.BigDecimal

interface ReactorSelectBigDecimalAsNumericTest<T : BigDecimalAsNumerics,
        U : ReactorSelectBigDecimalAsNumericRepository<T>, V : Transaction> : ReactorRepositoryTest<U, V> {

    @Test
    fun `Verify selectAllByBigDecimalNotNull finds bigDecimalAsNumericWithNullable`() {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNotNull(bigDecimalAsNumericWithNullable.bigDecimalNotNull).toIterable()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullNotEq finds bigDecimalAsNumericWithoutNullable`() {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNotNullNotEq(bigDecimalAsNumericWithNullable.bigDecimalNotNull).toIterable()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithoutNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullIn finds both`() {
        val seq = sequenceOf(
            bigDecimalAsNumericWithNullable.bigDecimalNotNull,
            bigDecimalAsNumericWithoutNullable.bigDecimalNotNull
        )
        Assertions.assertThat(repository.selectAllByBigDecimalNotNullIn(seq).toIterable())
            .hasSize(2)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithNullable, bigDecimalAsNumericWithoutNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullInf finds bigDecimalAsNumericWithNullable`() {
        Assertions.assertThat(repository.selectAllByBigDecimalNotNullInf(BigDecimal("1.2")).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullInf finds no results when equals`() {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNotNullInf(bigDecimalAsNumericWithNullable.bigDecimalNotNull).toIterable()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullInfOrEq finds bigDecimalAsNumericWithNullable`() {
        Assertions.assertThat(repository.selectAllByBigDecimalNotNullInfOrEq(BigDecimal("1.2")).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullInfOrEq finds bigDecimalAsNumericWithNullable when equals`() {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNotNullInfOrEq(bigDecimalAsNumericWithNullable.bigDecimalNotNull)
                .toIterable()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullSup finds bigDecimalAsNumericWithoutNullable`() {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNotNullSup(bigDecimalAsNumericWithNullable.bigDecimalNotNull).toIterable()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithoutNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullSup finds no results when equals`() {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNotNullSup(bigDecimalAsNumericWithoutNullable.bigDecimalNotNull)
                .toIterable()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullSupOrEq finds bigDecimalAsNumericWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByBigDecimalNotNullSupOrEq(BigDecimal("1.2")).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithoutNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullSupOrEq finds bigDecimalAsNumericWithoutNullable when equals`() {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNotNullSupOrEq(bigDecimalAsNumericWithoutNullable.bigDecimalNotNull)
                .toIterable()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithoutNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullable finds bigDecimalAsNumericWithNullable`() {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNullable(bigDecimalAsNumericWithNullable.bigDecimalNullable).toIterable()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullable finds bigDecimalAsNumericWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByBigDecimalNullable(null).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithoutNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableNotEq finds nothing`() {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNullableNotEq(bigDecimalAsNumericWithNullable.bigDecimalNullable)
                .toIterable()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableNotEq finds no results`() {
        Assertions.assertThat(repository.selectAllByBigDecimalNullableNotEq(null).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableInf finds bigDecimalAsNumericWithNullable`() {
        Assertions.assertThat(repository.selectAllByBigDecimalNullableInf(BigDecimal("2.3")).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableInf finds no results when equals`() {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNullableInf(bigDecimalAsNumericWithNullable.bigDecimalNullable!!)
                .toIterable()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableInfOrEq finds bigDecimalAsNumericWithNullable`() {
        Assertions.assertThat(repository.selectAllByBigDecimalNullableInfOrEq(BigDecimal("2.3")).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableInfOrEq finds bigDecimalAsNumericWithNullable when equals`() {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNullableInfOrEq(bigDecimalAsNumericWithNullable.bigDecimalNullable!!)
                .toIterable()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableSup finds bigDecimalAsNumericWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByBigDecimalNullableSup(BigDecimal("1.3")).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableSup finds no results when equals`() {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNullableSup(bigDecimalAsNumericWithNullable.bigDecimalNullable!!)
                .toIterable()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableSupOrEq finds bigDecimalAsNumericWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByBigDecimalNullableSupOrEq(BigDecimal("1.3")).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableSupOrEq finds bigDecimalAsNumericWithoutNullable when equals`() {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNullableSupOrEq(bigDecimalAsNumericWithNullable.bigDecimalNullable!!)
                .toIterable()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalAsNumericWithNullable)
    }
}