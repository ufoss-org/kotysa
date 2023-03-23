/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.MysqlCoroutinesSqlClient
import org.ufoss.kotysa.MysqlReactorSqlClient
import org.ufoss.kotysa.test.*

class R2dbcSelectByteArrayAsBinaryMysqlTest : AbstractR2dbcMysqlTest<ByteArrayRepositoryMysqlSelect>() {

    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        ByteArrayRepositoryMysqlSelect(sqlClient)

    @Test
    fun `Verify selectAllByByteArrayNotNull finds byteArrayBinaryWithNullable`() {
        assertThat(repository.selectAllByByteArrayNotNull(byteArrayBinaryWithNullable.byteArrayNotNull).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(byteArrayBinaryWithNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNotNullNotEq finds byteArrayWithoutNullable`() {
        assertThat(
            repository.selectAllByByteArrayNotNullNotEq(byteArrayBinaryWithNullable.byteArrayNotNull).toIterable()
        )
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
        assertThat(
            repository.selectAllByByteArrayNullableNotEq(byteArrayBinaryWithNullable.byteArrayNullable).toIterable()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByByteArrayNullableNotEq finds byteArrayBinaryWithNullable`() {
        assertThat(repository.selectAllByByteArrayNullableNotEq(null).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(byteArrayBinaryWithNullable)
    }
}


class ByteArrayRepositoryMysqlSelect(private val sqlClient: MysqlReactorSqlClient) : Repository {

    override fun init() {
        createTables()
            .then(insertByteArrays().then())
            .block()
    }

    override fun delete() {
        deleteAll()
            .block()
    }

    private fun createTables() = sqlClient createTable MysqlByteArrayAsBinaries

    private fun insertByteArrays() = sqlClient.insert(byteArrayBinaryWithNullable, byteArrayBinaryWithoutNullable)

    private fun deleteAll() = sqlClient deleteAllFrom MysqlByteArrayAsBinaries

    fun selectAllByByteArrayNotNull(byteArray: ByteArray) =
        (sqlClient selectFrom MysqlByteArrayAsBinaries
                where MysqlByteArrayAsBinaries.byteArrayNotNull eq byteArray
                ).fetchAll()

    fun selectAllByByteArrayNotNullNotEq(byteArray: ByteArray) =
        (sqlClient selectFrom MysqlByteArrayAsBinaries
                where MysqlByteArrayAsBinaries.byteArrayNotNull notEq byteArray
                ).fetchAll()

    fun selectAllByByteArrayNotNullIn(values: Sequence<ByteArray>) =
        (sqlClient selectFrom MysqlByteArrayAsBinaries
                where MysqlByteArrayAsBinaries.byteArrayNotNull `in` values
                ).fetchAll()

    fun selectAllByByteArrayNullable(byteArray: ByteArray?) =
        (sqlClient selectFrom MysqlByteArrayAsBinaries
                where MysqlByteArrayAsBinaries.byteArrayNullable eq byteArray
                ).fetchAll()

    fun selectAllByByteArrayNullableNotEq(byteArray: ByteArray?) =
        (sqlClient selectFrom MysqlByteArrayAsBinaries
                where MysqlByteArrayAsBinaries.byteArrayNullable notEq byteArray
                ).fetchAll()
}
