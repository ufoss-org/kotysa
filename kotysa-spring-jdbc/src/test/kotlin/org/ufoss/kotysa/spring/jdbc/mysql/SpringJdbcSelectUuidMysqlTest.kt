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
import java.util.*


class SpringJdbcSelectUuidMysqlTest : AbstractSpringJdbcMysqlTest<UuidRepositoryMysqlSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UuidRepositoryMysqlSelect>(resource)
        repository = getContextRepository()
    }

    @Test
    fun `Verify selectAllByRoleIdNotNull finds both results`() {
        assertThat(repository.selectAllByUuidNotNull(UUID.fromString(defaultUuid)))
                .hasSize(2)
                .containsExactlyInAnyOrder(mysqlUuidWithNullable, mysqlUuidWithoutNullable)
    }

    @Test
    fun `Verify selectAllByRoleIdNotNullNotEq finds no results`() {
        assertThat(repository.selectAllByUuidNotNullNotEq(UUID.fromString(defaultUuid)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByRoleIdNullable finds mysqlUuidWithNullable`() {
        assertThat(repository.selectAllByUuidNullable(mysqlUuidWithNullable.uuidNullable))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlUuidWithNullable)
    }

    @Test
    fun `Verify selectAllByRoleIdNullable finds mysqlUuidWithoutNullable`() {
        assertThat(repository.selectAllByUuidNullable(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlUuidWithoutNullable)
    }

    @Test
    fun `Verify selectAllByRoleIdNullableNotEq finds mysqlUuidWithoutNullable`() {
        assertThat(repository.selectAllByUuidNullableNotEq(mysqlUuidWithNullable.uuidNullable))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByRoleIdNullableNotEq finds no results`() {
        assertThat(repository.selectAllByUuidNullableNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlUuidWithNullable)
    }
}


class UuidRepositoryMysqlSelect(client: JdbcOperations) : Repository {

    private val sqlClient = client.sqlClient(mysqlTables)

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
        sqlClient.createTable<MysqlRole>()
        sqlClient.createTable<MysqlUuid>()
    }

    private fun insertRoles() = sqlClient.insert(mysqlUser, mysqlAdmin)

    private fun insertUuids() = sqlClient.insert(mysqlUuidWithNullable, mysqlUuidWithoutNullable)

    private fun deleteAllFromRole() = sqlClient.deleteAllFromTable<MysqlRole>()

    private fun deleteAllFromUuid() = sqlClient.deleteAllFromTable<MysqlUuid>()

    fun selectAllByUuidNotNull(uuid: UUID) = sqlClient.select<MysqlUuid>()
            .where { it[MysqlUuid::uuidNotNull] eq uuid }
            .fetchAll()

    fun selectAllByUuidNotNullNotEq(uuid: UUID) = sqlClient.select<MysqlUuid>()
            .where { it[MysqlUuid::uuidNotNull] notEq uuid }
            .fetchAll()

    fun selectAllByUuidNullable(uuid: UUID?) = sqlClient.select<MysqlUuid>()
            .where { it[MysqlUuid::uuidNullable] eq uuid }
            .fetchAll()

    fun selectAllByUuidNullableNotEq(uuid: UUID?) = sqlClient.select<MysqlUuid>()
            .where { it[MysqlUuid::uuidNullable] notEq uuid }
            .fetchAll()
}
