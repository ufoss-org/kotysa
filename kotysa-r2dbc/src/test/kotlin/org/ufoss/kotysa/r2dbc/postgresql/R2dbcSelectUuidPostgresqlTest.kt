/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.R2dbcSqlClient
import org.ufoss.kotysa.test.PostgresqlUuids
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.uuidWithNullable
import org.ufoss.kotysa.test.uuidWithoutNullable
import java.util.*

class R2dbcSelectUuidPostgresqlTest : AbstractR2dbcPostgresqlTest<UuidRepositoryPostgresqlSelect>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UuidRepositoryPostgresqlSelect(sqlClient)

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


class UuidRepositoryPostgresqlSelect(private val sqlClient: R2dbcSqlClient) : Repository {

    override fun init() = runBlocking {
        createTables()
        insertUuids()
    }

    override fun delete() = runBlocking<Unit> {
        deleteAll()
    }

    private suspend fun createTables() {
        sqlClient createTable PostgresqlUuids
    }

    private suspend fun insertUuids() {
        sqlClient.insert(uuidWithNullable, uuidWithoutNullable)
    }

    private suspend fun deleteAll() = sqlClient deleteAllFrom PostgresqlUuids

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
