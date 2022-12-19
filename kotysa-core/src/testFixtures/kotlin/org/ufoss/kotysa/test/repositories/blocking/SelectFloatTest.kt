/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.Floats
import org.ufoss.kotysa.test.floatWithNullable
import org.ufoss.kotysa.test.floatWithoutNullable
import org.ufoss.kotysa.transaction.Transaction

interface SelectFloatTest<T : Floats, U : SelectFloatRepository<T>, V : Transaction> : RepositoryTest<U, V> {

    @Test
    fun `Verify selectAllByFloatNotNull finds floatWithNullable`() {
        Assertions.assertThat(repository.selectAllByFloatNotNull(floatWithNullable.floatNotNull))
            .hasSize(1)
            .containsExactlyInAnyOrder(floatWithNullable)
    }

    @Test
    fun `Verify selectAllByFloatNotNullNotEq finds floatWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByFloatNotNullNotEq(floatWithNullable.floatNotNull))
            .hasSize(1)
            .containsExactlyInAnyOrder(floatWithoutNullable)
    }

    @Test
    fun `Verify selectAllByFloatNotNullIn finds both`() {
        val seq = sequenceOf(floatWithNullable.floatNotNull, floatWithoutNullable.floatNotNull)
        Assertions.assertThat(repository.selectAllByFloatNotNullIn(seq))
            .hasSize(2)
            .containsExactlyInAnyOrder(floatWithNullable, floatWithoutNullable)
    }

    @Test
    fun `Verify selectAllByFloatNotNullInf finds floatWithNullable`() {
        Assertions.assertThat(repository.selectAllByFloatNotNullInf(1.2f))
            .hasSize(1)
            .containsExactlyInAnyOrder(floatWithNullable)
    }

    @Test
    fun `Verify selectAllByFloatNotNullInf finds no results when equals`() {
        Assertions.assertThat(repository.selectAllByFloatNotNullInf(floatWithNullable.floatNotNull))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByFloatNotNullInfOrEq finds floatWithNullable`() {
        Assertions.assertThat(repository.selectAllByFloatNotNullInfOrEq(1.2f))
            .hasSize(1)
            .containsExactlyInAnyOrder(floatWithNullable)
    }

    @Test
    fun `Verify selectAllByFloatNotNullInfOrEq finds floatWithNullable when equals`() {
        Assertions.assertThat(repository.selectAllByFloatNotNullInfOrEq(floatWithNullable.floatNotNull))
            .hasSize(1)
            .containsExactlyInAnyOrder(floatWithNullable)
    }

    @Test
    fun `Verify selectAllByFloatNotNullSup finds floatWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByFloatNotNullSup(floatWithNullable.floatNotNull))
            .hasSize(1)
            .containsExactlyInAnyOrder(floatWithoutNullable)
    }

    @Test
    fun `Verify selectAllByFloatNotNullSup finds no results when equals`() {
        Assertions.assertThat(repository.selectAllByFloatNotNullSup(floatWithoutNullable.floatNotNull))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByFloatNotNullSupOrEq finds floatWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByFloatNotNullSupOrEq(1.2f))
            .hasSize(1)
            .containsExactlyInAnyOrder(floatWithoutNullable)
    }

    @Test
    fun `Verify selectAllByFloatNotNullSupOrEq finds floatWithoutNullable when equals`() {
        Assertions.assertThat(repository.selectAllByFloatNotNullSupOrEq(floatWithoutNullable.floatNotNull))
            .hasSize(1)
            .containsExactlyInAnyOrder(floatWithoutNullable)
    }

    @Test
    fun `Verify selectAllByFloatNullable finds floatWithNullable`() {
        Assertions.assertThat(repository.selectAllByFloatNullable(floatWithNullable.floatNullable))
            .hasSize(1)
            .containsExactlyInAnyOrder(floatWithNullable)
    }

    @Test
    fun `Verify selectAllByFloatNullable finds floatWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByFloatNullable(null))
            .hasSize(1)
            .containsExactlyInAnyOrder(floatWithoutNullable)
    }

    @Test
    fun `Verify selectAllByFloatNullableNotEq finds nothing`() {
        Assertions.assertThat(repository.selectAllByFloatNullableNotEq(floatWithNullable.floatNullable))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByFloatNullableNotEq finds no results`() {
        Assertions.assertThat(repository.selectAllByFloatNullableNotEq(null))
            .hasSize(1)
            .containsExactlyInAnyOrder(floatWithNullable)
    }

    @Test
    fun `Verify selectAllByFloatNullableInf finds floatWithNullable`() {
        Assertions.assertThat(repository.selectAllByFloatNullableInf(2.3f))
            .hasSize(1)
            .containsExactlyInAnyOrder(floatWithNullable)
    }

    @Test
    fun `Verify selectAllByFloatNullableInf finds no results when equals`() {
        Assertions.assertThat(repository.selectAllByFloatNullableInf(floatWithNullable.floatNullable!!))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByFloatNullableInfOrEq finds floatWithNullable`() {
        Assertions.assertThat(repository.selectAllByFloatNullableInfOrEq(2.3f))
            .hasSize(1)
            .containsExactlyInAnyOrder(floatWithNullable)
    }

    @Test
    fun `Verify selectAllByFloatNullableInfOrEq finds floatWithNullable when equals`() {
        Assertions.assertThat(repository.selectAllByFloatNullableInfOrEq(floatWithNullable.floatNullable!!))
            .hasSize(1)
            .containsExactlyInAnyOrder(floatWithNullable)
    }

    @Test
    fun `Verify selectAllByFloatNullableSup finds floatWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByFloatNullableSup(1.3f))
            .hasSize(1)
            .containsExactlyInAnyOrder(floatWithNullable)
    }

    @Test
    fun `Verify selectAllByFloatNullableSup finds no results when equals`() {
        Assertions.assertThat(repository.selectAllByFloatNullableSup(floatWithNullable.floatNullable!!))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByFloatNullableSupOrEq finds floatWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByFloatNullableSupOrEq(1.3f))
            .hasSize(1)
            .containsExactlyInAnyOrder(floatWithNullable)
    }

    @Test
    fun `Verify selectAllByFloatNullableSupOrEq finds floatWithoutNullable when equals`() {
        Assertions.assertThat(repository.selectAllByFloatNullableSupOrEq(floatWithNullable.floatNullable!!))
            .hasSize(1)
            .containsExactlyInAnyOrder(floatWithNullable)
    }
}