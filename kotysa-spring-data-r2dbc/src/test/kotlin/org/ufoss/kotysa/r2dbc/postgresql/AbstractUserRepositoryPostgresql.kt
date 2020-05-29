/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import org.ufoss.kotysa.r2dbc.Repository
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.test.*
import org.springframework.data.r2dbc.core.DatabaseClient


abstract class AbstractUserRepositoryPostgresql(dbClient: DatabaseClient) : Repository {

    protected val sqlClient = dbClient.sqlClient(postgresqlTables)

    override fun init() {
        createTables()
                .then(insertRoles())
                .then(insertUsers())
                .block()
    }

    override fun delete() {
        deleteAllFromUsers()
                .then(deleteAllFromRole())
                .block()
    }

    private fun createTables() =
            sqlClient.createTable<PostgresqlRole>()
                    .then(sqlClient.createTable<PostgresqlUser>())

    private fun insertRoles() = sqlClient.insert(postgresqlUser, postgresqlAdmin, postgresqlGod)

    fun insertUsers() = sqlClient.insert(postgresqlJdoe, postgresqlBboss)

    fun insertJDoe() = sqlClient.insert(postgresqlJdoe)

    private fun deleteAllFromRole() = sqlClient.deleteAllFromTable<PostgresqlRole>()

    fun deleteAllFromUsers() = sqlClient.deleteAllFromTable<PostgresqlUser>()

    fun selectAllUsers() = sqlClient.selectAll<PostgresqlUser>()

    fun selectFirstByFirstame(firstname: String) = sqlClient.select<PostgresqlUser>()
            .where { it[PostgresqlUser::firstname] eq firstname }
            .fetchFirst()
}
