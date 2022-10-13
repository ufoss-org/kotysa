/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.transaction.Transaction

interface SelectUuidTest<T : Uuids, U : SelectUuidRepository<T>, V : Transaction> : RepositoryTest<U, V> {

    @Test
    fun `Verify selectAllByUuidNotNull finds uuidWithNullable`() {
        assertThat(repository.selectAllByUuidIdNotNull(uuidWithNullable.uuidNotNull))
            .hasSize(1)
            .containsExactlyInAnyOrder(uuidWithNullable)
    }

    @Test
    fun `Verify selectAllByUuidNotNullNotEq finds uuidWithoutNullable`() {
        assertThat(repository.selectAllByUuidNotNullNotEq(uuidWithNullable.uuidNotNull))
            .hasSize(1)
            .containsExactlyInAnyOrder(uuidWithoutNullable)
    }

    @Test
    fun `Verify selectAllByUuidNotNullIn finds both`() {
        val seq = sequenceOf(uuidWithNullable.id, uuidWithoutNullable.id)
        assertThat(repository.selectAllByUuidNotNullIn(seq))
            .hasSize(2)
            .containsExactlyInAnyOrder(uuidWithNullable, uuidWithoutNullable)
    }

    @Test
    fun `Verify selectAllByUuidNullable finds uuidWithNullable`() {
        assertThat(repository.selectAllByUuidNullable(uuidWithNullable.uuidNullable))
            .hasSize(1)
            .containsExactlyInAnyOrder(uuidWithNullable)
    }

    @Test
    fun `Verify selectAllByUuidNullable finds uuidWithoutNullable`() {
        assertThat(repository.selectAllByUuidNullable(null))
            .hasSize(1)
            .containsExactlyInAnyOrder(uuidWithoutNullable)
    }

    @Test
    fun `Verify selectAllByUuidNullableNotEq finds uuidWithoutNullable`() {
        assertThat(repository.selectAllByUuidNullableNotEq(uuidWithNullable.uuidNullable))
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByUuidNullableNotEq finds no results`() {
        assertThat(repository.selectAllByUuidNullableNotEq(null))
            .hasSize(1)
            .containsExactlyInAnyOrder(uuidWithNullable)
    }
}
