/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import java.util.*


class SpringJdbcSelectUuidH2Test : AbstractSpringJdbcH2Test<UuidRepositoryH2Select>() {
    override val context = startContext<UuidRepositoryH2Select>()

    override val repository = getContextRepository<UuidRepositoryH2Select>()

    @Test
    fun `Verify selectAllByRoleIdNotNull finds both results`() {
        assertThat(repository.selectAllByRoleIdNotNull(h2User.id))
                .hasSize(2)
                .containsExactlyInAnyOrder(h2UuidWithNullable, h2UuidWithoutNullable)
    }

    @Test
    fun `Verify selectAllByRoleIdNotNullNotEq finds no results`() {
        assertThat(repository.selectAllByRoleIdNotNullNotEq(h2User.id))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByRoleIdNotNullIn finds both`() {
        val seq = sequenceOf(h2UuidWithNullable.roleIdNotNull, h2UuidWithoutNullable.roleIdNotNull)
        assertThat(repository.selectAllByRoleIdNotNullIn(seq))
                .hasSize(2)
                .containsExactlyInAnyOrder(h2UuidWithNullable, h2UuidWithoutNullable)
    }

    @Test
    fun `Verify selectAllByRoleIdNullable finds h2UuidWithNullable`() {
        assertThat(repository.selectAllByRoleIdNullable(h2Admin.id))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2UuidWithNullable)
    }

    @Test
    fun `Verify selectAllByRoleIdNullable finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByRoleIdNullable(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2UuidWithoutNullable)
    }

    @Test
    fun `Verify selectAllByRoleIdNullableNotEq finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByRoleIdNullableNotEq(h2Admin.id))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByRoleIdNullableNotEq finds no results`() {
        assertThat(repository.selectAllByRoleIdNullableNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2UuidWithNullable)
    }
}


class UuidRepositoryH2Select(client: JdbcOperations) : Repository {

    private val sqlClient = client.sqlClient(h2Tables)

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
        sqlClient.createTable<H2Role>()
        sqlClient.createTable<H2Uuid>()
    }

    private fun insertRoles() = sqlClient.insert(h2User, h2Admin)

    private fun insertUuids() = sqlClient.insert(h2UuidWithNullable, h2UuidWithoutNullable)

    private fun deleteAllFromRole() = sqlClient.deleteAllFromTable<H2Role>()

    private fun deleteAllFromUuid() = sqlClient.deleteAllFromTable<H2Uuid>()

    fun selectAllByRoleIdNotNull(roleId: UUID) = sqlClient.select<H2Uuid>()
            .where { it[H2Uuid::roleIdNotNull] eq roleId }
            .fetchAll()

    fun selectAllByRoleIdNotNullNotEq(roleId: UUID) = sqlClient.select<H2Uuid>()
            .where { it[H2Uuid::roleIdNotNull] notEq roleId }
            .fetchAll()

    fun selectAllByRoleIdNotNullIn(roleIds: Sequence<UUID>) = sqlClient.select<H2Uuid>()
            .where { it[H2Uuid::roleIdNotNull] `in` roleIds }
            .fetchAll()

    fun selectAllByRoleIdNullable(roleId: UUID?) = sqlClient.select<H2Uuid>()
            .where { it[H2Uuid::roleIdNullable] eq roleId }
            .fetchAll()

    fun selectAllByRoleIdNullableNotEq(roleId: UUID?) = sqlClient.select<H2Uuid>()
            .where { it[H2Uuid::roleIdNullable] notEq roleId }
            .fetchAll()
}
