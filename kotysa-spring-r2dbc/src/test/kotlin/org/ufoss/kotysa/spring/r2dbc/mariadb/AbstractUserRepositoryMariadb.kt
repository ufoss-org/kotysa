/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.ufoss.kotysa.spring.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.*


abstract class AbstractUserRepositoryMariadb(protected val sqlClient: ReactorSqlClient) : Repository {

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
            (sqlClient createTableIfNotExists MariadbRoles)
                    .then(sqlClient createTableIfNotExists MariadbUsers)
                    .then(sqlClient createTableIfNotExists MariadbUserRoles)

    private fun insertRoles() = sqlClient.insert(roleUser, roleAdmin, roleGod)

    private fun insertUsers() = sqlClient.insert(userJdoe, userBboss)

    private fun insertUserRoles() = sqlClient insert userRoleBboss

    private fun deleteAllFromRole() = sqlClient deleteAllFrom MariadbRoles

    private fun deleteAllFromUsers() = sqlClient deleteAllFrom MariadbUsers

    fun deleteAllFromUserRoles() = sqlClient deleteAllFrom MariadbUserRoles

    fun countAllUserRoles() = sqlClient selectCountAllFrom MariadbUserRoles

    fun selectAllUsers() = sqlClient selectAllFrom MariadbUsers

    fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom MariadbUsers
                    where MariadbUsers.firstname eq firstname
                    ).fetchFirst()
}
