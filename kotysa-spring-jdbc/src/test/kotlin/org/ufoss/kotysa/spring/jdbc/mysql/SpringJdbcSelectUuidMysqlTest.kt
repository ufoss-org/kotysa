/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import java.util.*


class SpringJdbcSelectUuidMysqlTest : AbstractSpringJdbcMysqlTest<UuidRepositoryMysqlSelect>() {
    override val context = startContext<UuidRepositoryMysqlSelect>()

    override val repository = getContextRepository<UuidRepositoryMysqlSelect>()

    @Test
    fun `Verify selectAllByRoleIdNotNull finds both results`() {
        assertThat(repository.selectAllByRoleIdNotNull(mysqlUser.id))
                .hasSize(2)
                .containsExactlyInAnyOrder(mysqlUuidWithNullable, mysqlUuidWithoutNullable)
    }

    @Test
    fun `Verify selectAllByRoleIdNotNullNotEq finds no results`() {
        assertThat(repository.selectAllByRoleIdNotNullNotEq(mysqlUser.id))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByRoleIdNullable finds mysqlUuidWithNullable`() {
        assertThat(repository.selectAllByRoleIdNullable(mysqlAdmin.id))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlUuidWithNullable)
    }

    @Test
    fun `Verify selectAllByRoleIdNullable finds mysqlUuidWithoutNullable`() {
        assertThat(repository.selectAllByRoleIdNullable(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlUuidWithoutNullable)
    }

    @Test
    fun `Verify selectAllByRoleIdNullableNotEq finds mysqlUuidWithoutNullable`() {
        assertThat(repository.selectAllByRoleIdNullableNotEq(mysqlAdmin.id))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByRoleIdNullableNotEq finds no results`() {
        assertThat(repository.selectAllByRoleIdNullableNotEq(null))
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

    fun selectAllByRoleIdNotNull(roleId: UUID) = sqlClient.select<MysqlUuid>()
            .where { it[MysqlUuid::roleIdNotNull] eq roleId }
            .fetchAll()

    fun selectAllByRoleIdNotNullNotEq(roleId: UUID) = sqlClient.select<MysqlUuid>()
            .where { it[MysqlUuid::roleIdNotNull] notEq roleId }
            .fetchAll()

    fun selectAllByRoleIdNullable(roleId: UUID?) = sqlClient.select<MysqlUuid>()
            .where { it[MysqlUuid::roleIdNullable] eq roleId }
            .fetchAll()

    fun selectAllByRoleIdNullableNotEq(roleId: UUID?) = sqlClient.select<MysqlUuid>()
            .where { it[MysqlUuid::roleIdNullable] notEq roleId }
            .fetchAll()
}
