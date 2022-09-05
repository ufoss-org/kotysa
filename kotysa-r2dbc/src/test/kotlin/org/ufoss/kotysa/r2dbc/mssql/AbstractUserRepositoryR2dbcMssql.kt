/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import kotlinx.coroutines.runBlocking
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.test.*

abstract class AbstractUserRepositoryR2dbcMssql(private val sqlClient: R2dbcSqlClient) : Repository {

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
        sqlClient createTableIfNotExists MssqlRoles
        sqlClient createTableIfNotExists MssqlUsers
        sqlClient createTableIfNotExists MssqlUserRoles
    }

    private suspend fun insertRoles() = sqlClient.insert(roleUser, roleAdmin, roleGod, roleGodBis)

    private suspend fun insertUsers() = sqlClient.insert(userJdoe, userBboss)

    private suspend fun insertUserRoles() {
        sqlClient insert userRoleBboss
    }

    private suspend fun deleteAllFromRole() = sqlClient deleteAllFrom MssqlRoles

    private suspend fun deleteAllFromUsers() = sqlClient deleteAllFrom MssqlUsers

    suspend fun deleteAllFromUserRoles() = sqlClient deleteAllFrom MssqlUserRoles

    suspend fun countAllUserRoles() = sqlClient selectCountAllFrom MssqlUserRoles

    fun selectAllUsers() = sqlClient selectAllFrom MssqlUsers

    suspend fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom MssqlUsers
                    where MssqlUsers.firstname eq firstname
                    ).fetchFirstOrNull()
}
