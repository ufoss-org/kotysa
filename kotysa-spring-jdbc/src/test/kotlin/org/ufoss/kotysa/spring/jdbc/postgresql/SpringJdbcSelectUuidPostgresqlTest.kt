/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import java.util.*


class SpringJdbcSelectUuidPostgresqlTest : AbstractSpringJdbcPostgresqlTest<UuidRepositoryPostgresqlSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UuidRepositoryPostgresqlSelect>(resource)
        repository = getContextRepository()
    }

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


class UuidRepositoryPostgresqlSelect(client: JdbcOperations) : Repository {

    private val sqlClient = client.sqlClient(postgresqlTables)

    override fun init() {
        createTables()
        insertUuids()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() {
        sqlClient createTable POSTGRESQL_UUID
    }

    private fun insertUuids() {
        sqlClient.insert(uuidWithNullable, uuidWithoutNullable)
    }

    private fun deleteAll() = sqlClient deleteAllFrom POSTGRESQL_UUID

    fun selectAllByUuidIdNotNull(uuid: UUID) =
            (sqlClient selectFrom POSTGRESQL_UUID
                    where POSTGRESQL_UUID.uuidNotNull eq uuid
                    ).fetchAll()

    fun selectAllByUuidNotNullNotEq(uuid: UUID) =
            (sqlClient selectFrom POSTGRESQL_UUID
                    where POSTGRESQL_UUID.uuidNotNull notEq uuid
                    ).fetchAll()

    fun selectAllByUuidNotNullIn(uuids: Sequence<UUID>) =
            (sqlClient selectFrom POSTGRESQL_UUID
                    where POSTGRESQL_UUID.id `in` uuids
                    ).fetchAll()

    fun selectAllByUuidNullable(uuid: UUID?) =
            (sqlClient selectFrom POSTGRESQL_UUID
                    where POSTGRESQL_UUID.uuidNullable eq uuid
                    ).fetchAll()

    fun selectAllByUuidNullableNotEq(uuid: UUID?) =
            (sqlClient selectFrom POSTGRESQL_UUID
                    where POSTGRESQL_UUID.uuidNullable notEq uuid
                    ).fetchAll()
}
