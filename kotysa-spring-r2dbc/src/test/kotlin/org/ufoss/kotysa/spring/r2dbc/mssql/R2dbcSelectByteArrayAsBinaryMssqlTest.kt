/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource

class R2dbcSelectByteArrayAsBinaryMssqlTest : AbstractR2dbcMssqlTest<ByteArrayRepositoryMssqlSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<ByteArrayRepositoryMssqlSelect>(resource)
    }

    override val repository: ByteArrayRepositoryMssqlSelect by lazy {
        getContextRepository()
    }

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


class ByteArrayRepositoryMssqlSelect(dbClient: DatabaseClient) : Repository {

    private val sqlClient = dbClient.sqlClient(mssqlTables)

    override fun init() {
        createTables()
            .then(insertByteArrays().then())
            .block()
    }

    override fun delete() {
        deleteAll()
            .block()
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
