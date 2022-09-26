/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mssql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient

class VertxSqlClientSelectByteArrayAsBinaryMssqlTest :
    AbstractVertxSqlClientMssqlTest<ByteArrayRepositoryMssqlSelect>() {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = ByteArrayRepositoryMssqlSelect(sqlClient)

    @Test
    fun `Verify selectAllByByteArrayNotNull finds byteArrayBinaryWithNullable`() {
        assertThat(
            repository.selectAllByByteArrayNotNull(byteArrayBinaryWithNullable.byteArrayNotNull).await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(byteArrayBinaryWithNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNotNullNotEq finds byteArrayBinaryWithoutNullable`() {
        assertThat(
            repository.selectAllByByteArrayNotNullNotEq(byteArrayBinaryWithNullable.byteArrayNotNull).await()
                .indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(byteArrayBinaryWithoutNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNotNullIn finds both`() {
        val seq = sequenceOf(byteArrayBinaryWithNullable.byteArrayNotNull, byteArrayBinaryWithoutNullable.byteArrayNotNull)
        assertThat(repository.selectAllByByteArrayNotNullIn(seq).await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(byteArrayBinaryWithNullable, byteArrayBinaryWithoutNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNullable finds byteArrayBinaryWithNullable`() {
        assertThat(
            repository.selectAllByByteArrayNullable(byteArrayBinaryWithNullable.byteArrayNullable).await()
                .indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(byteArrayBinaryWithNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNullable finds byteArrayBinaryWithoutNullable`() {
        assertThat(repository.selectAllByByteArrayNullable(null).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(byteArrayBinaryWithoutNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNullableNotEq finds no results`() {
        assertThat(
            repository.selectAllByByteArrayNullableNotEq(byteArrayBinaryWithNullable.byteArrayNullable).await()
                .indefinitely()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByByteArrayNullableNotEq finds byteArrayBinaryWithNullable`() {
        assertThat(repository.selectAllByByteArrayNullableNotEq(null).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(byteArrayBinaryWithNullable)
    }
}


class ByteArrayRepositoryMssqlSelect(private val sqlClient: VertxSqlClient) : Repository {

    override fun init() {
        createTables()
            .chain { -> insertByteArrays() }
            .await().indefinitely()
    }

    override fun delete() {
        deleteAll()
            .await().indefinitely()
    }

    private fun createTables() = sqlClient createTable MssqlByteArrayAsBinarys

    private fun insertByteArrays() = sqlClient.insert(byteArrayBinaryWithNullable, byteArrayBinaryWithoutNullable)

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
