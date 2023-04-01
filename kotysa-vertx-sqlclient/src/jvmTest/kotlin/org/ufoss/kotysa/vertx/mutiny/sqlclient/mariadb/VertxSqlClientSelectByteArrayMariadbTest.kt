/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient

class VertxSqlClientSelectByteArrayMariadbTest :
    AbstractVertxSqlClientMariadbTest<ByteArrayRepositoryMariadbSelect>() {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = ByteArrayRepositoryMariadbSelect(sqlClient)

    @Test
    fun `Verify selectAllByByteArrayNotNull finds byteArrayWithNullable`() {
        assertThat(
            repository.selectAllByByteArrayNotNull(byteArrayWithNullable.byteArrayNotNull).await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(byteArrayWithNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNotNullNotEq finds byteArrayWithoutNullable`() {
        assertThat(
            repository.selectAllByByteArrayNotNullNotEq(byteArrayWithNullable.byteArrayNotNull).await()
                .indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(byteArrayWithoutNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNotNullIn finds both`() {
        val seq = sequenceOf(byteArrayWithNullable.byteArrayNotNull, byteArrayWithoutNullable.byteArrayNotNull)
        assertThat(repository.selectAllByByteArrayNotNullIn(seq).await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(byteArrayWithNullable, byteArrayWithoutNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNullable finds byteArrayWithNullable`() {
        assertThat(
            repository.selectAllByByteArrayNullable(byteArrayWithNullable.byteArrayNullable).await()
                .indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(byteArrayWithNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNullable finds byteArrayWithoutNullable`() {
        assertThat(repository.selectAllByByteArrayNullable(null).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(byteArrayWithoutNullable)
    }

    @Test
    fun `Verify selectAllByByteArrayNullableNotEq finds no results`() {
        assertThat(
            repository.selectAllByByteArrayNullableNotEq(byteArrayWithNullable.byteArrayNullable).await()
                .indefinitely()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByByteArrayNullableNotEq finds byteArrayWithNullable`() {
        assertThat(repository.selectAllByByteArrayNullableNotEq(null).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(byteArrayWithNullable)
    }
}


class ByteArrayRepositoryMariadbSelect(private val sqlClient: VertxSqlClient) : Repository {

    override fun init() {
        createTables()
            .chain { -> insertByteArrays() }
            .await().indefinitely()
    }

    override fun delete() {
        deleteAll()
            .await().indefinitely()
    }

    private fun createTables() = sqlClient createTable MariadbByteArrays

    private fun insertByteArrays() = sqlClient.insert(byteArrayWithNullable, byteArrayWithoutNullable)

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
