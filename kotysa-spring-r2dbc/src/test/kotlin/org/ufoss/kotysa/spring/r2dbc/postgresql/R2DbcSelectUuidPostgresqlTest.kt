/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import java.util.*


class R2DbcSelectUuidPostgresqlTest : AbstractR2dbcPostgresqlTest<UuidRepositoryPostgresqlSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UuidRepositoryPostgresqlSelect>(resource)
    }

    override val repository: UuidRepositoryPostgresqlSelect by lazy {
        getContextRepository()
    }

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


class UuidRepositoryPostgresqlSelect(dbClient: DatabaseClient) : Repository {

    private val sqlClient = dbClient.sqlClient(postgresqlTables)

    override fun init() {
        createTables()
                .then(insertUuids().then())
                .block()
    }

    override fun delete() {
        deleteAll()
                .block()
    }

    private fun createTables() = sqlClient createTable PostgresqlUuids

    private fun insertUuids() = sqlClient.insert(uuidWithNullable, uuidWithoutNullable)

    private fun deleteAll() = sqlClient deleteAllFrom PostgresqlUuids

    fun selectAllByUuidIdNotNull(uuid: UUID) =
            (sqlClient selectFrom PostgresqlUuids
                    where PostgresqlUuids.uuidNotNull eq uuid
                    ).fetchAll()

    fun selectAllByUuidNotNullNotEq(uuid: UUID) =
            (sqlClient selectFrom PostgresqlUuids
                    where PostgresqlUuids.uuidNotNull notEq uuid
                    ).fetchAll()

    fun selectAllByUuidNotNullIn(uuids: Sequence<UUID>) =
            (sqlClient selectFrom PostgresqlUuids
                    where PostgresqlUuids.id `in` uuids
                    ).fetchAll()

    fun selectAllByUuidNullable(uuid: UUID?) =
            (sqlClient selectFrom PostgresqlUuids
                    where PostgresqlUuids.uuidNullable eq uuid
                    ).fetchAll()

    fun selectAllByUuidNullableNotEq(uuid: UUID?) =
            (sqlClient selectFrom PostgresqlUuids
                    where PostgresqlUuids.uuidNullable notEq uuid
                    ).fetchAll()
}
