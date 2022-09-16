/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.ufoss.kotysa.spring.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.*


abstract class AbstractUserRepositoryMysql(protected val sqlClient: ReactorSqlClient) : Repository {

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
            (sqlClient createTableIfNotExists MysqlRoles)
                    .then(sqlClient createTableIfNotExists MysqlUsers)
                    .then(sqlClient createTableIfNotExists MysqlUserRoles)

    private fun insertRoles() = sqlClient.insert(roleUser, roleAdmin, roleGod)

    private fun insertUsers() = sqlClient.insert(userJdoe, userBboss)

    private fun insertUserRoles() = sqlClient insert userRoleBboss

    private fun deleteAllFromRole() = sqlClient deleteAllFrom MysqlRoles

    private fun deleteAllFromUsers() = sqlClient deleteAllFrom MysqlUsers

    fun deleteAllFromUserRoles() = sqlClient deleteAllFrom MysqlUserRoles

    fun countAllUserRoles() = sqlClient selectCountAllFrom MysqlUserRoles

    fun selectAllUsers() = sqlClient selectAllFrom MysqlUsers

    fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom MysqlUsers
                    where MysqlUsers.firstname eq firstname
                    ).fetchFirst()
}
