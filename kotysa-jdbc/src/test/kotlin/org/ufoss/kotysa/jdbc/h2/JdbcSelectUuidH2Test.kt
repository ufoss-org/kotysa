/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import java.sql.Connection
import java.util.*


class JdbcSelectUuidH2Test : AbstractJdbcH2Test<UuidRepositoryH2Select>() {
    override fun instantiateRepository(connection: Connection) = UuidRepositoryH2Select(connection)

    @Test
    fun `Verify selectAllByUuidNotNull finds uuidWithNullable`() {
        assertThat(repository.selectAllByUuidIdNotNull(uuidWithNullable.uuidNotNull))
                .hasSize(1)
                .containsExactlyInAnyOrder(uuidWithNullable)
    }

    @Test
    fun `Verify selectAllByUuidNotNullNotEq finds uuidWithoutNullable`() {
        assertThat(repository.selectAllByUuidNotNullNotEq(uuidWithNullable.uuidNotNull))
                .hasSize(1)
                .containsExactlyInAnyOrder(uuidWithoutNullable)
    }

    @Test
    fun `Verify selectAllByUuidNotNullIn finds both`() {
        val seq = sequenceOf(uuidWithNullable.id, uuidWithoutNullable.id)
        assertThat(repository.selectAllByUuidNotNullIn(seq))
                .hasSize(2)
                .containsExactlyInAnyOrder(uuidWithNullable, uuidWithoutNullable)
    }

    @Test
    fun `Verify selectAllByUuidNullable finds uuidWithNullable`() {
        assertThat(repository.selectAllByUuidNullable(uuidWithNullable.uuidNullable))
                .hasSize(1)
                .containsExactlyInAnyOrder(uuidWithNullable)
    }

    @Test
    fun `Verify selectAllByUuidNullable finds uuidWithoutNullable`() {
        assertThat(repository.selectAllByUuidNullable(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(uuidWithoutNullable)
    }

    @Test
    fun `Verify selectAllByUuidNullableNotEq finds uuidWithoutNullable`() {
        assertThat(repository.selectAllByUuidNullableNotEq(uuidWithNullable.uuidNullable))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByUuidNullableNotEq finds no results`() {
        assertThat(repository.selectAllByUuidNullableNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(uuidWithNullable)
    }
}


class UuidRepositoryH2Select(connection: Connection) : Repository {

    private val sqlClient = connection.sqlClient(h2Tables)

    override fun init() {
        createTables()
        insertUuids()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() {
        sqlClient createTable H2_UUID
    }

    private fun insertUuids() {
        sqlClient.insert(uuidWithNullable, uuidWithoutNullable)
    }

    private fun deleteAll() = sqlClient deleteAllFrom H2_UUID

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