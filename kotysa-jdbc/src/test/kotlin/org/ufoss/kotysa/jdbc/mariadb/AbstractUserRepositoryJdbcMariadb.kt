/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.*

abstract class AbstractUserRepositoryJdbcMariadb(private val sqlClient: JdbcSqlClient) : Repository {

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
        sqlClient createTableIfNotExists MariadbRoles
        sqlClient createTableIfNotExists MariadbUsers
        sqlClient createTableIfNotExists MariadbUserRoles
    }

    private fun insertRoles() = sqlClient.insert(roleUser, roleAdmin, roleGod, roleGodBis)

    private fun insertUsers() = sqlClient.insert(userJdoe, userBboss)

    private fun insertUserRoles() {
        sqlClient insert userRoleBboss
    }

    private fun deleteAllFromRole() = sqlClient deleteAllFrom MariadbRoles

    private fun deleteAllFromUsers() = sqlClient deleteAllFrom MariadbUsers

    fun deleteAllFromUserRoles() = sqlClient deleteAllFrom MariadbUserRoles

    fun countAllUserRoles() = sqlClient selectCountAllFrom MariadbUserRoles

    fun selectAllUsers() = sqlClient selectAllFrom MariadbUsers

    fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom MariadbUsers
                    where MariadbUsers.firstname eq firstname
                    ).fetchFirstOrNull()
}
