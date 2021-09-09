/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.ufoss.kotysa.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import java.sql.Connection


abstract class AbstractUserRepositoryJdbcMssql(connection: Connection) : Repository {

    protected val sqlClient = connection.sqlClient(mssqlTables)

    override fun init() {
        createTables()
        insertRoles()
        insertUsers()
        insertUserRoles()
    }

    override fun delete() {
        deleteAllFromUserRoles()
        deleteAllFromUsers()
        deleteAllFromRole()
    }

    private fun createTables() {
        sqlClient createTableIfNotExists MSSQL_ROLE
        sqlClient createTableIfNotExists MSSQL_USER
        sqlClient createTableIfNotExists MSSQL_USER_ROLE
    }

    private fun insertRoles() = sqlClient.insert(roleUser, roleAdmin, roleGod, roleGodBis)

    private fun insertUsers() = sqlClient.insert(userJdoe, userBboss)

    private fun insertUserRoles() {
        sqlClient insert userRoleBboss
    }

    private fun deleteAllFromRole() = sqlClient deleteAllFrom MSSQL_ROLE

    private fun deleteAllFromUsers() = sqlClient deleteAllFrom MSSQL_USER

    fun deleteAllFromUserRoles() = sqlClient deleteAllFrom MSSQL_USER_ROLE

    fun countAllUserRoles() = sqlClient selectCountAllFrom MSSQL_USER_ROLE

    fun selectAllUsers() = sqlClient selectAllFrom MSSQL_USER

    fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom MSSQL_USER
                    where MSSQL_USER.firstname eq firstname
                    ).fetchFirstOrNull()
}
