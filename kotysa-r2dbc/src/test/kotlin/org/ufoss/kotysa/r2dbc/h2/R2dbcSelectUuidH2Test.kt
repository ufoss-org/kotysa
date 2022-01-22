/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import io.r2dbc.spi.Connection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.test.*
import java.util.*

class R2dbcSelectUuidH2Test : AbstractR2dbcH2Test<UuidRepositoryH2Select>() {
    override fun instantiateRepository(connection: Connection) = UuidRepositoryH2Select(connection)

    @Test
    fun `Verify selectAllByUuidNotNull finds uuidWithNullable`() = runTest {
        assertThat(repository.selectAllByUuidIdNotNull(uuidWithNullable.uuidNotNull).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(uuidWithNullable)
    }

    @Test
    fun `Verify selectAllByUuidNotNullNotEq finds uuidWithoutNullable`() = runTest {
        assertThat(repository.selectAllByUuidNotNullNotEq(uuidWithNullable.uuidNotNull).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(uuidWithoutNullable)
    }

    @Test
    fun `Verify selectAllByUuidNotNullIn finds both`() = runTest {
        val seq = sequenceOf(uuidWithNullable.id, uuidWithoutNullable.id)
        assertThat(repository.selectAllByUuidNotNullIn(seq).toList())
                .hasSize(2)
                .containsExactlyInAnyOrder(uuidWithNullable, uuidWithoutNullable)
    }

    @Test
    fun `Verify selectAllByUuidNullable finds uuidWithNullable`() = runTest {
        assertThat(repository.selectAllByUuidNullable(uuidWithNullable.uuidNullable).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(uuidWithNullable)
    }

    @Test
    fun `Verify selectAllByUuidNullable finds uuidWithoutNullable`() = runTest {
        assertThat(repository.selectAllByUuidNullable(null).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(uuidWithoutNullable)
    }

    @Test
    fun `Verify selectAllByUuidNullableNotEq finds uuidWithoutNullable`() = runTest {
        assertThat(repository.selectAllByUuidNullableNotEq(uuidWithNullable.uuidNullable).toList())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByUuidNullableNotEq finds no results`() = runTest {
        assertThat(repository.selectAllByUuidNullableNotEq(null).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(uuidWithNullable)
    }
}


class UuidRepositoryH2Select(connection: Connection) : Repository {

    private val sqlClient = connection.sqlClient(h2Tables)

    override fun init() = runBlocking {
        createTables()
        insertUuids()
    }

    override fun delete() = runBlocking<Unit> {
        deleteAll()
    }

    private suspend fun createTables() {
        sqlClient createTable H2_UUID
    }

    private suspend fun insertUuids() {
        sqlClient.insert(uuidWithNullable, uuidWithoutNullable)
    }

    private suspend fun deleteAll() = sqlClient deleteAllFrom H2_UUID

    fun selectAllByUuidIdNotNull(uuid: UUID) =
            (sqlClient selectFrom H2_UUID
                    where H2_UUID.uuidNotNull eq uuid
                    ).fetchAll()

    fun selectAllByUuidNotNullNotEq(uuid: UUID) =
            (sqlClient selectFrom H2_UUID
                    where H2_UUID.uuidNotNull notEq uuid
                    ).fetchAll()

    fun selectAllByUuidNotNullIn(uuids: Sequence<UUID>) =
            (sqlClient selectFrom H2_UUID
                    where H2_UUID.id `in` uuids
                    ).fetchAll()

    fun selectAllByUuidNullable(uuid: UUID?) =
            (sqlClient selectFrom H2_UUID
                    where H2_UUID.uuidNullable eq uuid
                    ).fetchAll()

    fun selectAllByUuidNullableNotEq(uuid: UUID?) =
            (sqlClient selectFrom H2_UUID
                    where H2_UUID.uuidNullable notEq uuid
                    ).fetchAll()
}