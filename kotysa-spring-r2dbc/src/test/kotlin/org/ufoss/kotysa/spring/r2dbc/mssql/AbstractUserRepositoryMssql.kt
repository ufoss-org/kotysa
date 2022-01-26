/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.ufoss.kotysa.spring.r2dbc.ReactorSqlClient
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
            (sqlClient createTableIfNotExists MSSQL_ROLE)
                    .then(sqlClient createTableIfNotExists MSSQL_USER)
                    .then(sqlClient createTableIfNotExists MSSQL_USER_ROLE)

    private fun insertRoles() = sqlClient.insert(roleUser, roleAdmin, roleGod, roleGodBis)

    private fun insertUsers() = sqlClient.insert(userJdoe, userBboss)

    private fun insertUserRoles() = sqlClient insert userRoleBboss

    private fun deleteAllFromRole() = sqlClient deleteAllFrom MSSQL_ROLE

    private fun deleteAllFromUsers() = sqlClient deleteAllFrom MSSQL_USER

    fun deleteAllFromUserRoles() = sqlClient deleteAllFrom MSSQL_USER_ROLE

    fun countAllUserRoles() = sqlClient selectCountAllFrom MSSQL_USER_ROLE

    fun selectAllUsers() = sqlClient selectAllFrom MSSQL_USER

    fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom MSSQL_USER
                    where MSSQL_USER.firstname eq firstname
                    ).fetchFirst()
}
