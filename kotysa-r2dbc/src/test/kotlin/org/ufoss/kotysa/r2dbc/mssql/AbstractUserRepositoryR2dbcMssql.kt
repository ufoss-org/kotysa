/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import io.r2dbc.spi.Connection
import kotlinx.coroutines.runBlocking
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.test.*


abstract class AbstractUserRepositoryR2dbcMssql(connection: Connection) : Repository {

    protected val sqlClient = connection.sqlClient(mssqlTables)

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
        sqlClient createTableIfNotExists MSSQL_ROLE
        sqlClient createTableIfNotExists MSSQL_USER
        sqlClient createTableIfNotExists MSSQL_USER_ROLE
    }

    private suspend fun insertRoles() = sqlClient.insert(roleUser, roleAdmin, roleGod, roleGodBis)

    private suspend fun insertUsers() = sqlClient.insert(userJdoe, userBboss)

    private suspend fun insertUserRoles() {
        sqlClient insert userRoleBboss
    }

    private suspend fun deleteAllFromRole() = sqlClient deleteAllFrom MSSQL_ROLE

    private suspend fun deleteAllFromUsers() = sqlClient deleteAllFrom MSSQL_USER

    suspend fun deleteAllFromUserRoles() = sqlClient deleteAllFrom MSSQL_USER_ROLE

    suspend fun countAllUserRoles() = sqlClient selectCountAllFrom MSSQL_USER_ROLE

    fun selectAllUsers() = sqlClient selectAllFrom MSSQL_USER

    suspend fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom MSSQL_USER
                    where MSSQL_USER.firstname eq firstname
                    ).fetchFirstOrNull()
}
