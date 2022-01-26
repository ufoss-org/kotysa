/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import io.r2dbc.spi.Connection
import kotlinx.coroutines.runBlocking
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.test.*


abstract class AbstractUserRepositoryR2dbcH2(connection: Connection) : Repository {

    protected val sqlClient = connection.sqlClient(h2Tables)

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
        sqlClient createTableIfNotExists H2_ROLE
        sqlClient createTableIfNotExists H2_USER
        sqlClient createTableIfNotExists H2_USER_ROLE
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

    private suspend fun deleteAllFromUsers() = sqlClient deleteAllFrom H2_USER
    
    private suspend fun deleteAllFromRole() = sqlClient deleteAllFrom H2_ROLE

    suspend fun deleteAllFromUserRoles() = sqlClient deleteAllFrom H2_USER_ROLE

    suspend fun countAllUserRoles() = sqlClient selectCountAllFrom H2_USER_ROLE

    fun selectAllUsers() = sqlClient selectAllFrom H2_USER

    suspend fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom H2_USER
                    where H2_USER.firstname eq firstname
                    ).fetchFirstOrNull()
}
