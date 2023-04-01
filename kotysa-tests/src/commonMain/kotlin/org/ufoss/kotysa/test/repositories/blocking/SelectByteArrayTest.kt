/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import ch.tutteli.atrium.api.fluent.en_GB.toBeEmpty
import ch.tutteli.atrium.api.fluent.en_GB.toContain
import ch.tutteli.atrium.api.fluent.en_GB.toHaveSize
import ch.tutteli.atrium.api.verbs.expect
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.transaction.Transaction

interface SelectByteArrayTest<T : ByteArrays, U : SelectByteArrayRepository<T>,
        V : Transaction> : RepositoryTest<U, V> {

    @Test
    fun `Verify selectAllByByteArrayNotNull finds byteArrayWithNullable`() {
        expect(repository.selectAllByByteArrayNotNull(byteArrayWithNullable.byteArrayNotNull))
            .toHaveSize(1)
            .toContain(byteArrayWithNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNotNullNotEq finds byteArrayWithoutNullable`() {
        expect(repository.selectAllByByteArrayNotNullNotEq(byteArrayWithNullable.byteArrayNotNull))
            .toHaveSize(1)
            .toContain(byteArrayWithoutNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNotNullIn finds both`() {
        val seq = sequenceOf(byteArrayWithNullable.byteArrayNotNull, byteArrayWithoutNullable.byteArrayNotNull)
        expect(repository.selectAllByByteArrayNotNullIn(seq))
            .toHaveSize(2)
            .toContain(byteArrayWithNullable, byteArrayWithoutNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNullable finds byteArrayWithNullable`() {
        expect(repository.selectAllByByteArrayNullable(byteArrayWithNullable.byteArrayNullable))
            .toHaveSize(1)
            .toContain(byteArrayWithNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNullable finds byteArrayWithoutNullable`() {
        expect(repository.selectAllByByteArrayNullable(null))
            .toHaveSize(1)
            .toContain(byteArrayWithoutNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNullableNotEq finds no results`() {
        expect(repository.selectAllByByteArrayNullableNotEq(byteArrayWithNullable.byteArrayNullable))
            .toBeEmpty()
    }

    @Test
    fun `Verify selectAllByByteArrayNullableNotEq finds byteArrayWithNullable`() {
        expect(repository.selectAllByByteArrayNullableNotEq(null))
            .toHaveSize(1)
            .toContain(byteArrayWithNullable)
    }
}
