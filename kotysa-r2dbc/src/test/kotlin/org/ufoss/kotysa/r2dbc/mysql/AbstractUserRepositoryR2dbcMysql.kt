/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import kotlinx.coroutines.runBlocking
import org.ufoss.kotysa.r2dbc.R2dbcSqlClient
import org.ufoss.kotysa.test.*

abstract class AbstractUserRepositoryR2dbcMysql(private val sqlClient: R2dbcSqlClient) : Repository {

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
        sqlClient createTableIfNotExists MYSQL_ROLE
        sqlClient createTableIfNotExists MYSQL_USER
        sqlClient createTableIfNotExists MYSQL_USER_ROLE
    }

    private suspend fun insertRoles() = sqlClient.insert(roleUser, roleAdmin, roleGod, roleGodBis)

    private suspend fun insertUsers() = sqlClient.insert(userJdoe, userBboss)

    private suspend fun insertUserRoles() {
        sqlClient insert userRoleBboss
    }

    private suspend fun deleteAllFromRole() = sqlClient deleteAllFrom MYSQL_ROLE

    private suspend fun deleteAllFromUsers() = sqlClient deleteAllFrom MYSQL_USER

    suspend fun deleteAllFromUserRoles() = sqlClient deleteAllFrom MYSQL_USER_ROLE

    suspend fun countAllUserRoles() = sqlClient selectCountAllFrom MYSQL_USER_ROLE

    fun selectAllUsers() = sqlClient selectAllFrom MYSQL_USER

    suspend fun selectFirstByFirstname(firstname: String) =
        (sqlClient selectFrom MYSQL_USER
                where MYSQL_USER.firstname eq firstname
                ).fetchFirstOrNull()
}
