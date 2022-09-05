/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.R2dbcSqlClient
import org.ufoss.kotysa.test.*

class R2dbcSelectByteArrayAsBinaryMysqlTest : AbstractR2dbcMysqlTest<ByteArrayRepositoryMysqlSelect>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = ByteArrayRepositoryMysqlSelect(sqlClient)

    @Test
    fun `Verify selectAllByByteArrayNotNull finds byteArrayBinaryWithNullable`() = runTest {
        assertThat(repository.selectAllByByteArrayNotNull(byteArrayBinaryWithNullable.byteArrayNotNull).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(byteArrayBinaryWithNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNotNullNotEq finds byteArrayWithoutNullable`() = runTest {
        assertThat(repository.selectAllByByteArrayNotNullNotEq(byteArrayBinaryWithNullable.byteArrayNotNull).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(byteArrayBinaryWithoutNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNotNullIn finds both`() = runTest {
        val seq = sequenceOf(byteArrayBinaryWithNullable.byteArrayNotNull, byteArrayWithoutNullable.byteArrayNotNull)
        assertThat(repository.selectAllByByteArrayNotNullIn(seq).toList())
                .hasSize(2)
                .containsExactlyInAnyOrder(byteArrayBinaryWithNullable, byteArrayBinaryWithoutNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNullable finds byteArrayBinaryWithNullable`() = runTest {
        assertThat(repository.selectAllByByteArrayNullable(byteArrayBinaryWithNullable.byteArrayNullable).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(byteArrayBinaryWithNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNullable finds byteArrayWithoutNullable`() = runTest {
        assertThat(repository.selectAllByByteArrayNullable(null).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(byteArrayBinaryWithoutNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNullableNotEq finds no results`() = runTest {
        assertThat(repository.selectAllByByteArrayNullableNotEq(byteArrayBinaryWithNullable.byteArrayNullable).toList())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByByteArrayNullableNotEq finds byteArrayBinaryWithNullable`() = runTest {
        assertThat(repository.selectAllByByteArrayNullableNotEq(null).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(byteArrayBinaryWithNullable)
    }
}


class ByteArrayRepositoryMysqlSelect(private val sqlClient: R2dbcSqlClient) : Repository {

    override fun init()  = runBlocking {
        createTables()
        insertByteArrays()
    }

    override fun delete() = runBlocking<Unit> {
        deleteAll()
    }

    private suspend fun createTables() {
        sqlClient createTable MysqlByteArrayAsBinarys
    }

    private suspend fun insertByteArrays() {
        sqlClient.insert(byteArrayBinaryWithNullable, byteArrayBinaryWithoutNullable)
    }

    private suspend fun deleteAll() = sqlClient deleteAllFrom MysqlByteArrayAsBinarys

    fun selectAllByByteArrayNotNull(byteArray: ByteArray) =
            (sqlClient selectFrom MysqlByteArrayAsBinarys
                    where MysqlByteArrayAsBinarys.byteArrayNotNull eq byteArray
                    ).fetchAll()

    fun selectAllByByteArrayNotNullNotEq(byteArray: ByteArray) =
            (sqlClient selectFrom MysqlByteArrayAsBinarys
                    where MysqlByteArrayAsBinarys.byteArrayNotNull notEq byteArray
                    ).fetchAll()

    fun selectAllByByteArrayNotNullIn(values: Sequence<ByteArray>) =
            (sqlClient selectFrom MysqlByteArrayAsBinarys
                    where MysqlByteArrayAsBinarys.byteArrayNotNull `in` values
                    ).fetchAll()

    fun selectAllByByteArrayNullable(byteArray: ByteArray?) =
            (sqlClient selectFrom MysqlByteArrayAsBinarys
                    where MysqlByteArrayAsBinarys.byteArrayNullable eq byteArray
                    ).fetchAll()

    fun selectAllByByteArrayNullableNotEq(byteArray: ByteArray?) =
            (sqlClient selectFrom MysqlByteArrayAsBinarys
                    where MysqlByteArrayAsBinarys.byteArrayNullable notEq byteArray
                    ).fetchAll()
}