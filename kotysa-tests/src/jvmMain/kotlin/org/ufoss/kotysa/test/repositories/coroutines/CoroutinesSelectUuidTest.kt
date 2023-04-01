/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.transaction.Transaction

interface CoroutinesSelectUuidTest<T : Uuids, U : CoroutinesSelectUuidRepository<T>, V : Transaction> : CoroutinesRepositoryTest<U, V> {

    @Test
    fun `Verify selectAllByUuidNotNull finds uuidWithNullable`() = runTest {
        assertThat(repository.selectAllByUuidIdNotNull(uuidWithNullable.uuidNotNull).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(uuidWithNullable)
    }

    @Test
    fun `Verify selectAllByUuidNotNullNotEq finds uuidWithoutNullable`() = runTest {
        assertThat(repository.selectAllByUuidNotNullNotEq(uuidWithNullable.uuidNotNull).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(uuidWithoutNullable)
    }

    @Test
    fun `Verify selectAllByUuidNotNullIn finds both`() = runTest {
        val seq = sequenceOf(uuidWithNullable.id, uuidWithoutNullable.id)
        assertThat(repository.selectAllByUuidNotNullIn(seq).toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(uuidWithNullable, uuidWithoutNullable)
    }

    @Test
    fun `Verify selectAllByUuidNullable finds uuidWithNullable`() = runTest {
        assertThat(repository.selectAllByUuidNullable(uuidWithNullable.uuidNullable).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(uuidWithNullable)
    }

    @Test
    fun `Verify selectAllByUuidNullable finds uuidWithoutNullable`() = runTest {
        assertThat(repository.selectAllByUuidNullable(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(uuidWithoutNullable)
    }

    @Test
    fun `Verify selectAllByUuidNullableNotEq finds uuidWithoutNullable`() = runTest {
        assertThat(repository.selectAllByUuidNullableNotEq(uuidWithNullable.uuidNullable).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByUuidNullableNotEq finds no results`() = runTest {
        assertThat(repository.selectAllByUuidNullableNotEq(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(uuidWithNullable)
    }
}
