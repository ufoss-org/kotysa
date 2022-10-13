/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.transaction.Transaction

interface SelectByteArrayTest<T : ByteArrays, U : SelectByteArrayRepository<T>,
        V : Transaction> : RepositoryTest<U, V> {

    @Test
    fun `Verify selectAllByByteArrayNotNull finds byteArrayWithNullable`() {
        assertThat(repository.selectAllByByteArrayNotNull(byteArrayWithNullable.byteArrayNotNull))
            .hasSize(1)
            .containsExactlyInAnyOrder(byteArrayWithNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNotNullNotEq finds byteArrayWithoutNullable`() {
        assertThat(repository.selectAllByByteArrayNotNullNotEq(byteArrayWithNullable.byteArrayNotNull))
            .hasSize(1)
            .containsExactlyInAnyOrder(byteArrayWithoutNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNotNullIn finds both`() {
        val seq = sequenceOf(byteArrayWithNullable.byteArrayNotNull, byteArrayWithoutNullable.byteArrayNotNull)
        assertThat(repository.selectAllByByteArrayNotNullIn(seq))
            .hasSize(2)
            .containsExactlyInAnyOrder(byteArrayWithNullable, byteArrayWithoutNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNullable finds byteArrayWithNullable`() {
        assertThat(repository.selectAllByByteArrayNullable(byteArrayWithNullable.byteArrayNullable))
            .hasSize(1)
            .containsExactlyInAnyOrder(byteArrayWithNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNullable finds byteArrayWithoutNullable`() {
        assertThat(repository.selectAllByByteArrayNullable(null))
            .hasSize(1)
            .containsExactlyInAnyOrder(byteArrayWithoutNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNullableNotEq finds no results`() {
        assertThat(repository.selectAllByByteArrayNullableNotEq(byteArrayWithNullable.byteArrayNullable))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByByteArrayNullableNotEq finds byteArrayWithNullable`() {
        assertThat(repository.selectAllByByteArrayNullableNotEq(null))
            .hasSize(1)
            .containsExactlyInAnyOrder(byteArrayWithNullable)
    }
}
