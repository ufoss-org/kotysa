/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import kotlinx.coroutines.runBlocking
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.test.*

abstract class AbstractUserRepositoryR2dbcPostgresql(private val sqlClient: R2dbcSqlClient) : Repository {

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
        sqlClient createTableIfNotExists PostgresqlRoles
        sqlClient createTableIfNotExists PostgresqlUsers
        sqlClient createTableIfNotExists PostgresqlUserRoles
    }

    private suspend fun insertRoles() {
        sqlClient.insert(roleUser, roleAdmin, roleGod)
    }

    private suspend fun insertUsers() {
        sqlClient.insert(userJdoe, userBboss)
    }

    private suspend fun insertUserRoles() {
        sqlClient insert userRoleBboss
    }

    private suspend fun deleteAllFromUsers() = sqlClient deleteAllFrom PostgresqlUsers

    private suspend fun deleteAllFromRole() = sqlClient deleteAllFrom PostgresqlRoles

    suspend fun deleteAllFromUserRoles() = sqlClient deleteAllFrom PostgresqlUserRoles

    suspend fun countAllUserRoles() = sqlClient selectCountAllFrom PostgresqlUserRoles

    fun selectAllUsers() = sqlClient selectAllFrom PostgresqlUsers

    suspend fun selectFirstByFirstname(firstname: String) =
        (sqlClient selectFrom PostgresqlUsers
                where PostgresqlUsers.firstname eq firstname
                ).fetchFirstOrNull()
}
