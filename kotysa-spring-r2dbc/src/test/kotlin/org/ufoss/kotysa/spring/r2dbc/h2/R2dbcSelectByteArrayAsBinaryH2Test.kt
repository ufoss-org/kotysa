/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.spring.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.*

class R2dbcSelectByteArrayAsBinaryH2Test : AbstractR2dbcH2Test<ByteArrayRepositoryH2Select>() {
    override val context = startContext<ByteArrayRepositoryH2Select>()
    override val repository = getContextRepository<ByteArrayRepositoryH2Select>()

    @Test
    fun `Verify selectAllByByteArrayNotNull finds byteArrayBinaryWithNullable`() {
        assertThat(repository.selectAllByByteArrayNotNull(byteArrayBinaryWithNullable.byteArrayNotNull).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(byteArrayBinaryWithNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNotNullNotEq finds byteArrayWithoutNullable`() {
        assertThat(repository.selectAllByByteArrayNotNullNotEq(byteArrayBinaryWithNullable.byteArrayNotNull).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(byteArrayBinaryWithoutNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNotNullIn finds both`() {
        val seq = sequenceOf(byteArrayBinaryWithNullable.byteArrayNotNull, byteArrayWithoutNullable.byteArrayNotNull)
        assertThat(repository.selectAllByByteArrayNotNullIn(seq).toIterable())
                .hasSize(2)
                .containsExactlyInAnyOrder(byteArrayBinaryWithNullable, byteArrayBinaryWithoutNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNullable finds byteArrayBinaryWithNullable`() {
        assertThat(repository.selectAllByByteArrayNullable(byteArrayBinaryWithNullable.byteArrayNullable).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(byteArrayBinaryWithNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNullable finds byteArrayWithoutNullable`() {
        assertThat(repository.selectAllByByteArrayNullable(null).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(byteArrayBinaryWithoutNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNullableNotEq finds no results`() {
        assertThat(repository.selectAllByByteArrayNullableNotEq(byteArrayBinaryWithNullable.byteArrayNullable).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByByteArrayNullableNotEq finds byteArrayBinaryWithNullable`() {
        assertThat(repository.selectAllByByteArrayNullableNotEq(null).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(byteArrayBinaryWithNullable)
    }
}


class ByteArrayRepositoryH2Select(private val sqlClient: ReactorSqlClient) : Repository {

    override fun init() {
        createTables()
            .then(insertByteArrays().then())
            .block()
    }

    override fun delete() {
        deleteAll()
            .block()
    }

    private fun createTables() = sqlClient createTable H2ByteArrayAsBinarys

    private fun insertByteArrays() = sqlClient.insert(byteArrayBinaryWithNullable, byteArrayBinaryWithoutNullable)

    private fun deleteAll() = sqlClient deleteAllFrom H2ByteArrayAsBinarys

    fun selectAllByByteArrayNotNull(byteArray: ByteArray) =
            (sqlClient selectFrom H2ByteArrayAsBinarys
                    where H2ByteArrayAsBinarys.byteArrayNotNull eq byteArray
                    ).fetchAll()

    fun selectAllByByteArrayNotNullNotEq(byteArray: ByteArray) =
            (sqlClient selectFrom H2ByteArrayAsBinarys
                    where H2ByteArrayAsBinarys.byteArrayNotNull notEq byteArray
                    ).fetchAll()

    fun selectAllByByteArrayNotNullIn(values: Sequence<ByteArray>) =
            (sqlClient selectFrom H2ByteArrayAsBinarys
                    where H2ByteArrayAsBinarys.byteArrayNotNull `in` values
                    ).fetchAll()

    fun selectAllByByteArrayNullable(byteArray: ByteArray?) =
            (sqlClient selectFrom H2ByteArrayAsBinarys
                    where H2ByteArrayAsBinarys.byteArrayNullable eq byteArray
                    ).fetchAll()

    fun selectAllByByteArrayNullableNotEq(byteArray: ByteArray?) =
            (sqlClient selectFrom H2ByteArrayAsBinarys
                    where H2ByteArrayAsBinarys.byteArrayNullable notEq byteArray
                    ).fetchAll()
}
