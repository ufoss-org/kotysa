/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.ByteArrays
import org.ufoss.kotysa.test.byteArrayWithNullable
import org.ufoss.kotysa.test.byteArrayWithoutNullable
import org.ufoss.kotysa.transaction.Transaction

interface CoroutinesSelectByteArrayTest<T : ByteArrays, U : CoroutinesSelectByteArrayRepository<T>,
        V : Transaction> : CoroutinesRepositoryTest<U, V> {

    @Test
    fun `Verify selectAllByByteArrayNotNull finds byteArrayWithNullable`() = runTest {
        assertThat(repository.selectAllByByteArrayNotNull(byteArrayWithNullable.byteArrayNotNull).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(byteArrayWithNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNotNullNotEq finds byteArrayWithoutNullable`() = runTest {
        assertThat(repository.selectAllByByteArrayNotNullNotEq(byteArrayWithNullable.byteArrayNotNull).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(byteArrayWithoutNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNotNullIn finds both`() = runTest {
        val seq = sequenceOf(byteArrayWithNullable.byteArrayNotNull, byteArrayWithoutNullable.byteArrayNotNull)
        assertThat(repository.selectAllByByteArrayNotNullIn(seq).toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(byteArrayWithNullable, byteArrayWithoutNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNullable finds byteArrayWithNullable`() = runTest {
        assertThat(repository.selectAllByByteArrayNullable(byteArrayWithNullable.byteArrayNullable).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(byteArrayWithNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNullable finds byteArrayWithoutNullable`() = runTest {
        assertThat(repository.selectAllByByteArrayNullable(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(byteArrayWithoutNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNullableNotEq finds no results`() = runTest {
        assertThat(repository.selectAllByByteArrayNullableNotEq(byteArrayWithNullable.byteArrayNullable).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByByteArrayNullableNotEq finds byteArrayWithNullable`() = runTest {
        assertThat(repository.selectAllByByteArrayNullableNotEq(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(byteArrayWithNullable)
    }
}
