/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import io.r2dbc.spi.Connection
import kotlinx.coroutines.runBlocking
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.test.*

abstract class AbstractUserRepositoryR2dbcPostgresql(connection: Connection) : Repository {

    protected val sqlClient = connection.sqlClient(postgresqlTables)

    override fun init() = runBlocking {
        createTables()
        insertRoles()
        insertUsers()
        insertUserRoles()
    }

    override fun delete() = runBlocking<Unit> {
        deleteAllFromUserRoles()
        deleteAllFromUsers()
        deleteAllFromRole()
    }

    private suspend fun createTables() {
        sqlClient createTableIfNotExists POSTGRESQL_ROLE
        sqlClient createTableIfNotExists POSTGRESQL_USER
        sqlClient createTableIfNotExists POSTGRESQL_USER_ROLE
    }

    private suspend fun insertRoles() {
        sqlClient.insert(roleUser, roleAdmin, roleGod, roleGodBis)
    }

    private suspend fun insertUsers() {
        sqlClient.insert(userJdoe, userBboss)
    }

    private suspend fun insertUserRoles() {
        sqlClient insert userRoleBboss
    }

    private suspend fun deleteAllFromUsers() = sqlClient deleteAllFrom POSTGRESQL_USER

    private suspend fun deleteAllFromRole() = sqlClient deleteAllFrom POSTGRESQL_ROLE

    suspend fun deleteAllFromUserRoles() = sqlClient deleteAllFrom POSTGRESQL_USER_ROLE

    suspend fun countAllUserRoles() = sqlClient selectCountAllFrom POSTGRESQL_USER_ROLE

    fun selectAllUsers() = sqlClient selectAllFrom POSTGRESQL_USER

    suspend fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom POSTGRESQL_USER
                    where POSTGRESQL_USER.firstname eq firstname
                    ).fetchFirstOrNull()
}
