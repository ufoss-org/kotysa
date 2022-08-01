/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.*

class JdbcSelectByteArrayAsBinaryMssqlTest : AbstractJdbcMssqlTest<ByteArrayAsBinaryRepositoryMssqlSelect>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = ByteArrayAsBinaryRepositoryMssqlSelect(sqlClient)

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


class ByteArrayAsBinaryRepositoryMssqlSelect(private val sqlClient: JdbcSqlClient) : Repository {

    override fun init() {
        createTables()
        insertByteArrays()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() {
        sqlClient createTable MssqlByteArrayAsBinarys
    }

    private fun insertByteArrays() {
        sqlClient.insert(byteArrayBinaryWithNullable, byteArrayBinaryWithoutNullable)
    }

    private fun deleteAll() = sqlClient deleteAllFrom MssqlByteArrayAsBinarys

    fun selectAllByByteArrayNotNull(byteArray: ByteArray) =
        (sqlClient selectFrom MssqlByteArrayAsBinarys
                where MssqlByteArrayAsBinarys.byteArrayNotNull eq byteArray
                ).fetchAll()

    fun selectAllByByteArrayNotNullNotEq(byteArray: ByteArray) =
        (sqlClient selectFrom MssqlByteArrayAsBinarys
                where MssqlByteArrayAsBinarys.byteArrayNotNull notEq byteArray
                ).fetchAll()

    fun selectAllByByteArrayNotNullIn(values: Sequence<ByteArray>) =
        (sqlClient selectFrom MssqlByteArrayAsBinarys
                where MssqlByteArrayAsBinarys.byteArrayNotNull `in` values
                ).fetchAll()

    fun selectAllByByteArrayNullable(byteArray: ByteArray?) =
        (sqlClient selectFrom MssqlByteArrayAsBinarys
                where MssqlByteArrayAsBinarys.byteArrayNullable eq byteArray
                ).fetchAll()

    fun selectAllByByteArrayNullableNotEq(byteArray: ByteArray?) =
        (sqlClient selectFrom MssqlByteArrayAsBinarys
                where MssqlByteArrayAsBinarys.byteArrayNullable notEq byteArray
                ).fetchAll()
}
