/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*


abstract class AbstractUserRepositorySpringJdbcMssql(client: JdbcOperations) : Repository {

    protected val sqlClient = client.sqlClient(mssqlTables)

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
        sqlClient createTableIfNotExists MssqlRoles
        sqlClient createTableIfNotExists MssqlUsers
        sqlClient createTableIfNotExists MssqlUserRoles
    }

    private fun insertRoles() = sqlClient.insert(roleUser, roleAdmin, roleGod)

    private fun insertUsers() = sqlClient.insert(userJdoe, userBboss)

    private fun insertUserRoles() {
        sqlClient insert userRoleBboss
    }

    private fun deleteAllFromRole() = sqlClient deleteAllFrom MssqlRoles

    private fun deleteAllFromUsers() = sqlClient deleteAllFrom MssqlUsers

    fun deleteAllFromUserRoles() = sqlClient deleteAllFrom MssqlUserRoles

    fun countAllUserRoles() = sqlClient selectCountAllFrom MssqlUserRoles

    fun selectAllUsers() = sqlClient selectAllFrom MssqlUsers

    fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom MssqlUsers
                    where MssqlUsers.firstname eq firstname
                    ).fetchFirstOrNull()
}
