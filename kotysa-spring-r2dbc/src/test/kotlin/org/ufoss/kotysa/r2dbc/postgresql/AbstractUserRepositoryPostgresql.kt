/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql
/*
import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.*


abstract class AbstractUserRepositoryPostgresql(protected val sqlClient: ReactorSqlClient) : Repository {

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

    private fun insertUsers() = sqlClient.insert(postgresqlJdoe, postgresqlBboss)

    private fun deleteAllFromRole() = sqlClient.deleteAllFromTable<PostgresqlRole>()

    fun deleteAllFromUsers() = sqlClient.deleteAllFromTable<PostgresqlUser>()

    fun selectAllUsers() = sqlClient.selectAll<PostgresqlUser>()

    fun selectFirstByFirstname(firstname: String) = sqlClient.select<PostgresqlUser>()
            .where { it[PostgresqlUser::firstname] eq firstname }
            .fetchFirst()
}
*/