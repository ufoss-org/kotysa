/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import kotlinx.coroutines.runBlocking
import org.ufoss.kotysa.R2dbcSqlClient
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
        sqlClient createTableIfNotExists MysqlRoles
        sqlClient createTableIfNotExists MysqlUsers
        sqlClient createTableIfNotExists MysqlUserRoles
    }

    private suspend fun insertRoles() = sqlClient.insert(roleUser, roleAdmin, roleGod, roleGodBis)

    private suspend fun insertUsers() = sqlClient.insert(userJdoe, userBboss)

    private suspend fun insertUserRoles() {
        sqlClient insert userRoleBboss
    }

    private suspend fun deleteAllFromRole() = sqlClient deleteAllFrom MysqlRoles

    private suspend fun deleteAllFromUsers() = sqlClient deleteAllFrom MysqlUsers

    suspend fun deleteAllFromUserRoles() = sqlClient deleteAllFrom MysqlUserRoles

    suspend fun countAllUserRoles() = sqlClient selectCountAllFrom MysqlUserRoles

    fun selectAllUsers() = sqlClient selectAllFrom MysqlUsers

    suspend fun selectFirstByFirstname(firstname: String) =
        (sqlClient selectFrom MysqlUsers
                where MysqlUsers.firstname eq firstname
                ).fetchFirstOrNull()
}
