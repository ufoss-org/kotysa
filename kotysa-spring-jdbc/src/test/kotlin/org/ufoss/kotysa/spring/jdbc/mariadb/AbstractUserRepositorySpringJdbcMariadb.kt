/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*


abstract class AbstractUserRepositorySpringJdbcMariadb(client: JdbcOperations) : Repository {

    protected val sqlClient = client.sqlClient(mariadbTables)

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
        sqlClient createTableIfNotExists MARIADB_ROLE
        sqlClient createTableIfNotExists MARIADB_USER
        sqlClient createTableIfNotExists MARIADB_USER_ROLE
    }

    private fun insertRoles() = sqlClient.insert(roleUser, roleAdmin, roleGod, roleGodBis)

    private fun insertUsers() = sqlClient.insert(userJdoe, userBboss)

    private fun insertUserRoles() {
        sqlClient insert userRoleBboss
    }

    private fun deleteAllFromRole() = sqlClient deleteAllFrom MARIADB_ROLE

    private fun deleteAllFromUsers() = sqlClient deleteAllFrom MARIADB_USER

    fun deleteAllFromUserRoles() = sqlClient deleteAllFrom MARIADB_USER_ROLE

    fun countAllUserRoles() = sqlClient selectCountAllFrom MARIADB_USER_ROLE

    fun selectAllUsers() = sqlClient selectAllFrom MARIADB_USER

    fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom MARIADB_USER
                    where MARIADB_USER.firstname eq firstname
                    ).fetchFirstOrNull()
}
