/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.test.*

abstract class AbstractUserRepositoryJdbcMysql(private val sqlClient: JdbcSqlClient) : Repository {

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
        sqlClient createTableIfNotExists MysqlRoles
        sqlClient createTableIfNotExists MysqlUsers
        sqlClient createTableIfNotExists MysqlUserRoles
    }

    private fun insertRoles() = sqlClient.insert(roleUser, roleAdmin, roleGod)

    private fun insertUsers() = sqlClient.insert(userJdoe, userBboss)

    private fun insertUserRoles() {
        sqlClient insert userRoleBboss
    }

    private fun deleteAllFromRole() = sqlClient deleteAllFrom MysqlRoles

    private fun deleteAllFromUsers() = sqlClient deleteAllFrom MysqlUsers

    fun deleteAllFromUserRoles() = sqlClient deleteAllFrom MysqlUserRoles

    fun countAllUserRoles() = sqlClient selectCountAllFrom MysqlUserRoles

    fun selectAllUsers() = sqlClient selectAllFrom MysqlUsers

    fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom MysqlUsers
                    where MysqlUsers.firstname eq firstname
                    ).fetchFirstOrNull()
}
