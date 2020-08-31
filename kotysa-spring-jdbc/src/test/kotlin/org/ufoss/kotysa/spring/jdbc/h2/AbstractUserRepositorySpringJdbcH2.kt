/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.springframework.jdbc.core.JdbcTemplate
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*


abstract class AbstractUserRepositorySpringJdbcH2(client: JdbcTemplate) : Repository {

    protected val sqlClient = client.sqlClient(h2Tables)

    override fun init() {
        createTables()
        insertRoles()
        insertUsers()
    }

    override fun delete() {
        deleteAllFromUsers()
        deleteAllFromRole()
    }

    private fun createTables() {
        sqlClient.createTable<H2Role>()
        sqlClient.createTable<H2User>()
    }

    private fun insertRoles() = sqlClient.insert(h2User, h2Admin, h2God)

    fun insertUsers() = sqlClient.insert(h2Jdoe, h2Bboss)

    fun insertJDoe() = sqlClient.insert(h2Jdoe)

    private fun deleteAllFromRole() = sqlClient.deleteAllFromTable<H2Role>()

    fun deleteAllFromUsers() = sqlClient.deleteAllFromTable<H2User>()

    fun selectAllUsers() = sqlClient.selectAll<H2User>()

    fun selectFirstByFirstame(firstname: String) =
            sqlClient.select<H2User>()
            .where { it[H2User::firstname] eq firstname }
            .fetchFirstOrNull()
}
