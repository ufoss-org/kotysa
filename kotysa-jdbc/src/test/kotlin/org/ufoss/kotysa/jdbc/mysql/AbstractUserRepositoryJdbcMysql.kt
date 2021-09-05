/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.ufoss.kotysa.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import java.sql.Connection


abstract class AbstractUserRepositoryJdbcMysql(connection: Connection) : Repository {

    protected val sqlClient = connection.sqlClient(mysqlTables)

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
        sqlClient createTableIfNotExists MYSQL_ROLE
        sqlClient createTableIfNotExists MYSQL_USER
        sqlClient createTableIfNotExists MYSQL_USER_ROLE
    }

    private fun insertRoles() = sqlClient.insert(roleUser, roleAdmin, roleGod, roleGodBis)

    private fun insertUsers() = sqlClient.insert(userJdoe, userBboss)

    private fun insertUserRoles() {
        sqlClient insert userRoleBboss
    }

    private fun deleteAllFromRole() = sqlClient deleteAllFrom MYSQL_ROLE

    private fun deleteAllFromUsers() = sqlClient deleteAllFrom MYSQL_USER

    fun deleteAllFromUserRoles() = sqlClient deleteAllFrom MYSQL_USER_ROLE

    fun countAllUserRoles() = sqlClient selectCountAllFrom MYSQL_USER_ROLE

    fun selectAllUsers() = sqlClient selectAllFrom MYSQL_USER

    fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom MYSQL_USER
                    where MYSQL_USER.firstname eq firstname
                    ).fetchFirstOrNull()
}
