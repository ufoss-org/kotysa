/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource

class SpringJdbcSelectByteArrayAsBinaryMysqlTest : AbstractSpringJdbcMysqlTest<ByteArrayAsBinaryRepositoryMysqlSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<ByteArrayAsBinaryRepositoryMysqlSelect>(resource)
    }

    override val repository: ByteArrayAsBinaryRepositoryMysqlSelect by lazy {
        getContextRepository()
    }

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


class ByteArrayAsBinaryRepositoryMysqlSelect(client: JdbcOperations) : Repository {

    private val sqlClient = client.sqlClient(mysqlTables)

    override fun init() {
        createTables()
        insertByteArrays()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() {
        sqlClient createTable MysqlByteArrayAsBinarys
    }

    private fun insertByteArrays() {
        sqlClient.insert(byteArrayBinaryWithNullable, byteArrayBinaryWithoutNullable)
    }

    private fun deleteAll() = sqlClient deleteAllFrom MysqlByteArrayAsBinarys

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
