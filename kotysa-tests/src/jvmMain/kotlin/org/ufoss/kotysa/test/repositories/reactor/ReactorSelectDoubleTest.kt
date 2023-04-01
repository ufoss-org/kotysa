/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.reactor

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.Doubles
import org.ufoss.kotysa.test.doubleWithNullable
import org.ufoss.kotysa.test.doubleWithoutNullable
import org.ufoss.kotysa.transaction.Transaction

interface ReactorSelectDoubleTest<T : Doubles, U : ReactorSelectDoubleRepository<T>, V : Transaction>
    : ReactorRepositoryTest<U, V> {

    @Test
    fun `Verify selectAllByDoubleNotNull finds doubleWithNullable`() {
        Assertions.assertThat(repository.selectAllByDoubleNotNull(doubleWithNullable.doubleNotNull).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(doubleWithNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNotNullNotEq finds doubleWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByDoubleNotNullNotEq(doubleWithNullable.doubleNotNull).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(doubleWithoutNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNotNullIn finds both`() {
        val seq = sequenceOf(doubleWithNullable.doubleNotNull, doubleWithoutNullable.doubleNotNull)
        Assertions.assertThat(repository.selectAllByDoubleNotNullIn(seq).toIterable())
            .hasSize(2)
            .containsExactlyInAnyOrder(doubleWithNullable, doubleWithoutNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNotNullInf finds doubleWithNullable`() {
        Assertions.assertThat(repository.selectAllByDoubleNotNullInf(1.2).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(doubleWithNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNotNullInf finds no results when equals`() {
        Assertions.assertThat(repository.selectAllByDoubleNotNullInf(doubleWithNullable.doubleNotNull).toIterable())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByDoubleNotNullInfOrEq finds doubleWithNullable`() {
        Assertions.assertThat(repository.selectAllByDoubleNotNullInfOrEq(1.2).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(doubleWithNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNotNullInfOrEq finds doubleWithNullable when equals`() {
        Assertions.assertThat(repository.selectAllByDoubleNotNullInfOrEq(doubleWithNullable.doubleNotNull).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(doubleWithNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNotNullSup finds doubleWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByDoubleNotNullSup(doubleWithNullable.doubleNotNull).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(doubleWithoutNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNotNullSup finds no results when equals`() {
        Assertions.assertThat(repository.selectAllByDoubleNotNullSup(doubleWithoutNullable.doubleNotNull).toIterable())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByDoubleNotNullSupOrEq finds doubleWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByDoubleNotNullSupOrEq(1.2).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(doubleWithoutNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNotNullSupOrEq finds doubleWithoutNullable when equals`() {
        Assertions.assertThat(
            repository.selectAllByDoubleNotNullSupOrEq(doubleWithoutNullable.doubleNotNull).toIterable()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(doubleWithoutNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNullable finds doubleWithNullable`() {
        Assertions.assertThat(repository.selectAllByDoubleNullable(doubleWithNullable.doubleNullable).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(doubleWithNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNullable finds doubleWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByDoubleNullable(null).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(doubleWithoutNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNullableNotEq finds nothing`() {
        Assertions.assertThat(repository.selectAllByDoubleNullableNotEq(doubleWithNullable.doubleNullable).toIterable())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByDoubleNullableNotEq finds no results`() {
        Assertions.assertThat(repository.selectAllByDoubleNullableNotEq(null).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(doubleWithNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNullableInf finds doubleWithNullable`() {
        Assertions.assertThat(repository.selectAllByDoubleNullableInf(2.3).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(doubleWithNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNullableInf finds no results when equals`() {
        Assertions.assertThat(repository.selectAllByDoubleNullableInf(doubleWithNullable.doubleNullable!!).toIterable())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByDoubleNullableInfOrEq finds doubleWithNullable`() {
        Assertions.assertThat(repository.selectAllByDoubleNullableInfOrEq(2.3).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(doubleWithNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNullableInfOrEq finds doubleWithNullable when equals`() {
        Assertions.assertThat(
            repository.selectAllByDoubleNullableInfOrEq(doubleWithNullable.doubleNullable!!).toIterable()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(doubleWithNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNullableSup finds doubleWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByDoubleNullableSup(1.3).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(doubleWithNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNullableSup finds no results when equals`() {
        Assertions.assertThat(repository.selectAllByDoubleNullableSup(doubleWithNullable.doubleNullable!!).toIterable())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByDoubleNullableSupOrEq finds doubleWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByDoubleNullableSupOrEq(1.3).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(doubleWithNullable)
    }

    @Test
    fun `Verify selectAllByDoubleNullableSupOrEq finds doubleWithoutNullable when equals`() {
        Assertions.assertThat(
            repository.selectAllByDoubleNullableSupOrEq(doubleWithNullable.doubleNullable!!).toIterable()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(doubleWithNullable)
    }
}