/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import io.r2dbc.spi.Connection
import kotlinx.coroutines.runBlocking
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.test.*

abstract class AbstractUserRepositoryR2dbcMariadb(connection: Connection) : Repository {

    protected val sqlClient = connection.sqlClient(mariadbTables)

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
        sqlClient createTableIfNotExists MARIADB_ROLE
        sqlClient createTableIfNotExists MARIADB_USER
        sqlClient createTableIfNotExists MARIADB_USER_ROLE
    }

    private suspend fun insertRoles() = sqlClient.insert(roleUser, roleAdmin, roleGod, roleGodBis)

    private suspend fun insertUsers() = sqlClient.insert(userJdoe, userBboss)

    private suspend fun insertUserRoles() {
        sqlClient insert userRoleBboss
    }

    private suspend fun deleteAllFromRole() = sqlClient deleteAllFrom MARIADB_ROLE

    private suspend fun deleteAllFromUsers() = sqlClient deleteAllFrom MARIADB_USER

    suspend fun deleteAllFromUserRoles() = sqlClient deleteAllFrom MARIADB_USER_ROLE

    suspend fun countAllUserRoles() = sqlClient selectCountAllFrom MARIADB_USER_ROLE

    fun selectAllUsers() = sqlClient selectAllFrom MARIADB_USER

    suspend fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom MARIADB_USER
                    where MARIADB_USER.firstname eq firstname
                    ).fetchFirstOrNull()
}
