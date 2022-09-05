/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource

class SpringJdbcSelectByteArrayAsBlobMariadbTest : AbstractSpringJdbcMariadbTest<ByteArrayRepositoryMariadbSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<ByteArrayRepositoryMariadbSelect>(resource)
    }

    override val repository: ByteArrayRepositoryMariadbSelect by lazy {
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


class ByteArrayRepositoryMariadbSelect(client: JdbcOperations) : Repository {

    private val sqlClient = client.sqlClient(mariadbTables)

    override fun init() {
        createTables()
        insertByteArrays()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() {
        sqlClient createTable MariadbByteArrays
    }

    private fun insertByteArrays() {
        sqlClient.insert(byteArrayWithNullable, byteArrayWithoutNullable)
    }

    private fun deleteAll() = sqlClient deleteAllFrom MariadbByteArrays

    fun selectAllByByteArrayNotNull(byteArray: ByteArray) =
            (sqlClient selectFrom MariadbByteArrays
                    where MariadbByteArrays.byteArrayNotNull eq byteArray
                    ).fetchAll()

    fun selectAllByByteArrayNotNullNotEq(byteArray: ByteArray) =
            (sqlClient selectFrom MariadbByteArrays
                    where MariadbByteArrays.byteArrayNotNull notEq byteArray
                    ).fetchAll()

    fun selectAllByByteArrayNotNullIn(values: Sequence<ByteArray>) =
            (sqlClient selectFrom MariadbByteArrays
                    where MariadbByteArrays.byteArrayNotNull `in` values
                    ).fetchAll()

    fun selectAllByByteArrayNullable(byteArray: ByteArray?) =
            (sqlClient selectFrom MariadbByteArrays
                    where MariadbByteArrays.byteArrayNullable eq byteArray
                    ).fetchAll()

    fun selectAllByByteArrayNullableNotEq(byteArray: ByteArray?) =
            (sqlClient selectFrom MariadbByteArrays
                    where MariadbByteArrays.byteArrayNullable notEq byteArray
                    ).fetchAll()
}