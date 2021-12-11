/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.*
import java.util.*


class R2DbcSelectUuidH2Test : AbstractR2dbcH2Test<UuidRepositoryH2Select>() {
    override val context = startContext<UuidRepositoryH2Select>()
    override val repository = getContextRepository<UuidRepositoryH2Select>()

    @Test
    fun `Verify selectAllByUuidNotNull finds uuidWithNullable`() {
        assertThat(repository.selectAllByUuidIdNotNull(uuidWithNullable.uuidNotNull).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(uuidWithNullable)
    }

    @Test
    fun `Verify selectAllByUuidNotNullNotEq finds uuidWithoutNullable`() {
        assertThat(repository.selectAllByUuidNotNullNotEq(uuidWithNullable.uuidNotNull).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(uuidWithoutNullable)
    }

    @Test
    fun `Verify selectAllByUuidNotNullIn finds both`() {
        val seq = sequenceOf(uuidWithNullable.id, uuidWithoutNullable.id)
        assertThat(repository.selectAllByUuidNotNullIn(seq).toIterable())
                .hasSize(2)
                .containsExactlyInAnyOrder(uuidWithNullable, uuidWithoutNullable)
    }

    @Test
    fun `Verify selectAllByUuidNullable finds uuidWithNullable`() {
        assertThat(repository.selectAllByUuidNullable(uuidWithNullable.uuidNullable).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(uuidWithNullable)
    }

    @Test
    fun `Verify selectAllByUuidNullable finds uuidWithoutNullable`() {
        assertThat(repository.selectAllByUuidNullable(null).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(uuidWithoutNullable)
    }

    @Test
    fun `Verify selectAllByUuidNullableNotEq finds uuidWithoutNullable`() {
        assertThat(repository.selectAllByUuidNullableNotEq(uuidWithNullable.uuidNullable).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByUuidNullableNotEq finds no results`() {
        assertThat(repository.selectAllByUuidNullableNotEq(null).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(uuidWithNullable)
    }
}


class UuidRepositoryH2Select(private val sqlClient: ReactorSqlClient) : Repository {

    override fun init() {
        createTables()
                .then(insertUuids().then())
                .block()
    }

    override fun delete() {
        deleteAll()
                .block()
    }

    private fun createTables() = sqlClient createTable H2_UUID

    private fun insertUuids() = sqlClient.insert(uuidWithNullable, uuidWithoutNullable)

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
