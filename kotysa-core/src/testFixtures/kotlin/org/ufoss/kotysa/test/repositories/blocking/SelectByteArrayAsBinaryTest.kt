/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.transaction.Transaction

interface SelectByteArrayAsBinaryTest<T : ByteArrayAsBinaries, U : SelectByteArrayAsBinaryRepository<T>,
        V : Transaction> : RepositoryTest<U, V> {

    @Test
    fun `Verify selectAllByByteArrayNotNull finds byteArrayBinaryWithNullable`() {
        assertThat(repository.selectAllByByteArrayNotNull(byteArrayBinaryWithNullable.byteArrayNotNull))
            .hasSize(1)
            .containsExactlyInAnyOrder(byteArrayBinaryWithNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNotNullNotEq finds byteArrayBinaryWithoutNullable`() {
        assertThat(repository.selectAllByByteArrayNotNullNotEq(byteArrayBinaryWithNullable.byteArrayNotNull))
            .hasSize(1)
            .containsExactlyInAnyOrder(byteArrayBinaryWithoutNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNotNullIn finds both`() {
        val seq = sequenceOf(byteArrayBinaryWithNullable.byteArrayNotNull, byteArrayBinaryWithoutNullable.byteArrayNotNull)
        assertThat(repository.selectAllByByteArrayNotNullIn(seq))
            .hasSize(2)
            .containsExactlyInAnyOrder(byteArrayBinaryWithNullable, byteArrayBinaryWithoutNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNullable finds byteArrayBinaryWithNullable`() {
        assertThat(repository.selectAllByByteArrayNullable(byteArrayBinaryWithNullable.byteArrayNullable))
            .hasSize(1)
            .containsExactlyInAnyOrder(byteArrayBinaryWithNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNullable finds byteArrayBinaryWithoutNullable`() {
        assertThat(repository.selectAllByByteArrayNullable(null))
            .hasSize(1)
            .containsExactlyInAnyOrder(byteArrayBinaryWithoutNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNullableNotEq finds no results`() {
        assertThat(repository.selectAllByByteArrayNullableNotEq(byteArrayBinaryWithNullable.byteArrayNullable))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByByteArrayNullableNotEq finds byteArrayBinaryWithNullable`() {
        assertThat(repository.selectAllByByteArrayNullableNotEq(null))
            .hasSize(1)
            .containsExactlyInAnyOrder(byteArrayBinaryWithNullable)
    }
}
