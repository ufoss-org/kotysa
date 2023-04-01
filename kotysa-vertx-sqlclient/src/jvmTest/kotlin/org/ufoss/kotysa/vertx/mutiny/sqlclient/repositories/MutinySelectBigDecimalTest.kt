/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.BigDecimals
import org.ufoss.kotysa.test.bigDecimalWithNullable
import org.ufoss.kotysa.test.bigDecimalWithoutNullable
import java.math.BigDecimal

interface MutinySelectBigDecimalTest<T : BigDecimals, U : MutinySelectBigDecimalRepository<T>>
    : MutinyRepositoryTest<U> {

    @Test
    fun `Verify selectAllByBigDecimalNotNull finds bigDecimalWithNullable`() {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNotNull(bigDecimalWithNullable.bigDecimalNotNull).await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullNotEq finds bigDecimalWithoutNullable`() {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNotNullNotEq(bigDecimalWithNullable.bigDecimalNotNull)
                .await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalWithoutNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullIn finds both`() {
        val seq = sequenceOf(bigDecimalWithNullable.bigDecimalNotNull, bigDecimalWithoutNullable.bigDecimalNotNull)
        Assertions.assertThat(repository.selectAllByBigDecimalNotNullIn(seq).await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(bigDecimalWithNullable, bigDecimalWithoutNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullInf finds bigDecimalWithNullable`() {
        Assertions.assertThat(repository.selectAllByBigDecimalNotNullInf(BigDecimal("1.2")).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullInf finds no results when equals`() {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNotNullInf(bigDecimalWithNullable.bigDecimalNotNull).await().indefinitely()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullInfOrEq finds bigDecimalWithNullable`() {
        Assertions.assertThat(repository.selectAllByBigDecimalNotNullInfOrEq(BigDecimal("1.2"))
            .await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullInfOrEq finds bigDecimalWithNullable when equals`() {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNotNullInfOrEq(bigDecimalWithNullable.bigDecimalNotNull)
                .await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullSup finds bigDecimalWithoutNullable`() {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNotNullSup(bigDecimalWithNullable.bigDecimalNotNull).await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalWithoutNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullSup finds no results when equals`() {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNotNullSup(bigDecimalWithoutNullable.bigDecimalNotNull)
                .await().indefinitely()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullSupOrEq finds bigDecimalWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByBigDecimalNotNullSupOrEq(BigDecimal("1.2"))
            .await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalWithoutNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNotNullSupOrEq finds bigDecimalWithoutNullable when equals`() {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNotNullSupOrEq(bigDecimalWithoutNullable.bigDecimalNotNull)
                .await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalWithoutNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullable finds bigDecimalWithNullable`() {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNullable(bigDecimalWithNullable.bigDecimalNullable).await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullable finds bigDecimalWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByBigDecimalNullable(null).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalWithoutNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableNotEq finds nothing`() {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNullableNotEq(bigDecimalWithNullable.bigDecimalNullable)
                .await().indefinitely()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableNotEq finds no results`() {
        Assertions.assertThat(repository.selectAllByBigDecimalNullableNotEq(null).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableInf finds bigDecimalWithNullable`() {
        Assertions.assertThat(repository.selectAllByBigDecimalNullableInf(BigDecimal("2.3")).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableInf finds no results when equals`() {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNullableInf(bigDecimalWithNullable.bigDecimalNullable!!)
                .await().indefinitely()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableInfOrEq finds bigDecimalWithNullable`() {
        Assertions.assertThat(repository.selectAllByBigDecimalNullableInfOrEq(BigDecimal("2.3"))
            .await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableInfOrEq finds bigDecimalWithNullable when equals`() {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNullableInfOrEq(bigDecimalWithNullable.bigDecimalNullable!!)
                .await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableSup finds bigDecimalWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByBigDecimalNullableSup(BigDecimal("1.3")).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableSup finds no results when equals`() {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNullableSup(bigDecimalWithNullable.bigDecimalNullable!!)
                .await().indefinitely()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableSupOrEq finds bigDecimalWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByBigDecimalNullableSupOrEq(BigDecimal("1.3"))
            .await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalWithNullable)
    }

    @Test
    fun `Verify selectAllByBigDecimalNullableSupOrEq finds bigDecimalWithoutNullable when equals`() {
        Assertions.assertThat(
            repository.selectAllByBigDecimalNullableSupOrEq(bigDecimalWithNullable.bigDecimalNullable!!)
                .await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(bigDecimalWithNullable)
    }
}