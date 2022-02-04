/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import kotlinx.coroutines.runBlocking
import org.ufoss.kotysa.r2dbc.R2dbcSqlClient
import org.ufoss.kotysa.test.*

abstract class AbstractUserRepositoryR2dbcH2(private val sqlClient: R2dbcSqlClient) : Repository {

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
        sqlClient createTableIfNotExists H2Roles
        sqlClient createTableIfNotExists H2Users
        sqlClient createTableIfNotExists H2UserRoles
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

    private suspend fun deleteAllFromUsers() = sqlClient deleteAllFrom H2Users
    
    private suspend fun deleteAllFromRole() = sqlClient deleteAllFrom H2Roles

    suspend fun deleteAllFromUserRoles() = sqlClient deleteAllFrom H2UserRoles

    suspend fun countAllUserRoles() = sqlClient selectCountAllFrom H2UserRoles

    fun selectAllUsers() = sqlClient selectAllFrom H2Users

    suspend fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom H2Users
                    where H2Users.firstname eq firstname
                    ).fetchFirstOrNull()
}
