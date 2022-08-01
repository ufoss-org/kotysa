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

class SpringJdbcSelectByteArrayAsBlobMysqlTest : AbstractSpringJdbcMysqlTest<ByteArrayRepositoryMysqlSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<ByteArrayRepositoryMysqlSelect>(resource)
    }

    override val repository: ByteArrayRepositoryMysqlSelect by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify selectAllByByteArrayNotNull finds byteArrayWithNullable`() {
        assertThat(repository.selectAllByByteArrayNotNull(byteArrayWithNullable.byteArrayNotNull))
                .hasSize(1)
                .containsExactlyInAnyOrder(byteArrayWithNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNotNullNotEq finds byteArrayWithoutNullable`() {
        assertThat(repository.selectAllByByteArrayNotNullNotEq(byteArrayWithNullable.byteArrayNotNull))
                .hasSize(1)
                .containsExactlyInAnyOrder(byteArrayWithoutNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNotNullIn finds both`() {
        val seq = sequenceOf(byteArrayWithNullable.byteArrayNotNull, byteArrayWithoutNullable.byteArrayNotNull)
        assertThat(repository.selectAllByByteArrayNotNullIn(seq))
                .hasSize(2)
                .containsExactlyInAnyOrder(byteArrayWithNullable, byteArrayWithoutNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNullable finds byteArrayWithNullable`() {
        assertThat(repository.selectAllByByteArrayNullable(byteArrayWithNullable.byteArrayNullable))
                .hasSize(1)
                .containsExactlyInAnyOrder(byteArrayWithNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNullable finds byteArrayWithoutNullable`() {
        assertThat(repository.selectAllByByteArrayNullable(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(byteArrayWithoutNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNullableNotEq finds no results`() {
        assertThat(repository.selectAllByByteArrayNullableNotEq(byteArrayWithNullable.byteArrayNullable))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByByteArrayNullableNotEq finds byteArrayWithNullable`() {
        assertThat(repository.selectAllByByteArrayNullableNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(byteArrayWithNullable)
    }
}


class ByteArrayRepositoryMysqlSelect(client: JdbcOperations) : Repository {

    private val sqlClient = client.sqlClient(mysqlTables)

    override fun init() {
        createTables()
        insertByteArrays()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() {
        sqlClient createTable MysqlByteArrays
    }

    private fun insertByteArrays() {
        sqlClient.insert(byteArrayWithNullable, byteArrayWithoutNullable)
    }

    private fun deleteAll() = sqlClient deleteAllFrom MysqlByteArrays

    fun selectAllByByteArrayNotNull(byteArray: ByteArray) =
            (sqlClient selectFrom MysqlByteArrays
                    where MysqlByteArrays.byteArrayNotNull eq byteArray
                    ).fetchAll()

    fun selectAllByByteArrayNotNullNotEq(byteArray: ByteArray) =
            (sqlClient selectFrom MysqlByteArrays
                    where MysqlByteArrays.byteArrayNotNull notEq byteArray
                    ).fetchAll()

    fun selectAllByByteArrayNotNullIn(values: Sequence<ByteArray>) =
            (sqlClient selectFrom MysqlByteArrays
                    where MysqlByteArrays.byteArrayNotNull `in` values
                    ).fetchAll()

    fun selectAllByByteArrayNullable(byteArray: ByteArray?) =
            (sqlClient selectFrom MysqlByteArrays
                    where MysqlByteArrays.byteArrayNullable eq byteArray
                    ).fetchAll()

    fun selectAllByByteArrayNullableNotEq(byteArray: ByteArray?) =
            (sqlClient selectFrom MysqlByteArrays
                    where MysqlByteArrays.byteArrayNullable notEq byteArray
                    ).fetchAll()
}
