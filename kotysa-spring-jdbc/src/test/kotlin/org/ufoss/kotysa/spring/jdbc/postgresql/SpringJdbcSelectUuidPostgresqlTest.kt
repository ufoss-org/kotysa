/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcTemplate
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import java.util.*


class SpringJdbcSelectUuidPostgresqlTest : AbstractSpringJdbcPostgresqlTest<UuidRepositoryPostgresqlSelect>() {
    override val context = startContext<UuidRepositoryPostgresqlSelect>()

    override val repository = getContextRepository<UuidRepositoryPostgresqlSelect>()

    @Test
    fun `Verify selectAllByRoleIdNotNull finds both results`() {
        assertThat(repository.selectAllByRoleIdNotNull(postgresqlUser.id))
                .hasSize(2)
                .containsExactlyInAnyOrder(postgresqlUuidWithNullable, postgresqlUuidWithoutNullable)
    }

    @Test
    fun `Verify selectAllByRoleIdNotNullNotEq finds no results`() {
        assertThat(repository.selectAllByRoleIdNotNullNotEq(postgresqlUser.id))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByRoleIdNullable finds postgresqlUuidWithNullable`() {
        assertThat(repository.selectAllByRoleIdNullable(postgresqlAdmin.id))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlUuidWithNullable)
    }

    @Test
    fun `Verify selectAllByRoleIdNullable finds postgresqlUuidWithoutNullable`() {
        assertThat(repository.selectAllByRoleIdNullable(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlUuidWithoutNullable)
    }

    @Test
    fun `Verify selectAllByRoleIdNullableNotEq finds postgresqlUuidWithoutNullable`() {
        assertThat(repository.selectAllByRoleIdNullableNotEq(postgresqlAdmin.id))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByRoleIdNullableNotEq finds no results`() {
        assertThat(repository.selectAllByRoleIdNullableNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlUuidWithNullable)
    }
}


class UuidRepositoryPostgresqlSelect(client: JdbcTemplate) : Repository {

    private val sqlClient = client.sqlClient(postgresqlTables)

    override fun init() {
        createTables()
        insertRoles()
        insertUuids()
    }

    override fun delete() {
        deleteAllFromUuid()
        deleteAllFromRole()
    }

    private fun createTables() {
        sqlClient.createTable<PostgresqlRole>()
        sqlClient.createTable<PostgresqlUuid>()
    }

    private fun insertRoles() = sqlClient.insert(postgresqlUser, postgresqlAdmin)

    private fun insertUuids() = sqlClient.insert(postgresqlUuidWithNullable, postgresqlUuidWithoutNullable)

    private fun deleteAllFromRole() = sqlClient.deleteAllFromTable<PostgresqlRole>()

    private fun deleteAllFromUuid() = sqlClient.deleteAllFromTable<PostgresqlUuid>()

    fun selectAllByRoleIdNotNull(roleId: UUID) = sqlClient.select<PostgresqlUuid>()
            .where { it[PostgresqlUuid::roleIdNotNull] eq roleId }
            .fetchAll()

    fun selectAllByRoleIdNotNullNotEq(roleId: UUID) = sqlClient.select<PostgresqlUuid>()
            .where { it[PostgresqlUuid::roleIdNotNull] notEq roleId }
            .fetchAll()

    fun selectAllByRoleIdNullable(roleId: UUID?) = sqlClient.select<PostgresqlUuid>()
            .where { it[PostgresqlUuid::roleIdNullable] eq roleId }
            .fetchAll()

    fun selectAllByRoleIdNullableNotEq(roleId: UUID?) = sqlClient.select<PostgresqlUuid>()
            .where { it[PostgresqlUuid::roleIdNullable] notEq roleId }
            .fetchAll()
}
