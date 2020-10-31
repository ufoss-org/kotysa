/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*


abstract class AbstractUserRepositorySpringJdbcPostgresql(client: JdbcOperations) : Repository {

    protected val sqlClient = client.sqlClient(postgresqlTables)

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
        sqlClient.createTable<PostgresqlRole>()
        sqlClient.createTable<PostgresqlUser>()
    }

    private fun insertRoles() = sqlClient.insert(postgresqlUser, postgresqlAdmin, postgresqlGod)

    private fun insertUsers() = sqlClient.insert(postgresqlJdoe, postgresqlBboss)

    private fun deleteAllFromRole() = sqlClient.deleteAllFromTable<PostgresqlRole>()

    fun deleteAllFromUsers() = sqlClient.deleteAllFromTable<PostgresqlUser>()

    fun selectAllUsers() = sqlClient.selectAll<PostgresqlUser>()

    fun selectFirstByFirstname(firstname: String) = sqlClient.select<PostgresqlUser>()
            .where { it[PostgresqlUser::firstname] eq firstname }
            .fetchFirstOrNull()
}
