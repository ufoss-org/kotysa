/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.*


abstract class AbstractUserRepositoryMssql(protected val sqlClient: ReactorSqlClient) : Repository {

    override fun init() {
        createTables()
                .then(insertRoles().then())
                .then(insertUsers().then())
                .then(insertUserRoles())
                .block()
    }

    override fun delete() {
        deleteAllFromUserRoles()
                .then(deleteAllFromUsers())
                .then(deleteAllFromRole())
                .block()
    }

    private fun createTables() =
            (sqlClient createTableIfNotExists MssqlRoles)
                    .then(sqlClient createTableIfNotExists MssqlUsers)
                    .then(sqlClient createTableIfNotExists MssqlUserRoles)

    private fun insertRoles() = sqlClient.insert(roleUser, roleAdmin, roleGod)

    private fun insertUsers() = sqlClient.insert(userJdoe, userBboss)

    private fun insertUserRoles() = sqlClient insert userRoleBboss

    private fun deleteAllFromRole() = sqlClient deleteAllFrom MssqlRoles

    private fun deleteAllFromUsers() = sqlClient deleteAllFrom MssqlUsers

    fun deleteAllFromUserRoles() = sqlClient deleteAllFrom MssqlUserRoles

    fun countAllUserRoles() = sqlClient selectCountAllFrom MssqlUserRoles

    fun selectAllUsers() = sqlClient selectAllFrom MssqlUsers

    fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom MssqlUsers
                    where MssqlUsers.firstname eq firstname
                    ).fetchFirst()
}
