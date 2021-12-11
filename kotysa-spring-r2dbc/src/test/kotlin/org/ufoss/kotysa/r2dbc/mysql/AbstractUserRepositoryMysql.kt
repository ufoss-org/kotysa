/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.ufoss.kotysa.r2dbc.ReactorSqlClient
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
            (sqlClient createTableIfNotExists MYSQL_ROLE)
                    .then(sqlClient createTableIfNotExists MYSQL_USER)
                    .then(sqlClient createTableIfNotExists MYSQL_USER_ROLE)

    private fun insertRoles() = sqlClient.insert(roleUser, roleAdmin, roleGod, roleGodBis)

    private fun insertUsers() = sqlClient.insert(userJdoe, userBboss)

    private fun insertUserRoles() = sqlClient insert userRoleBboss

    private fun deleteAllFromRole() = sqlClient deleteAllFrom MYSQL_ROLE

    private fun deleteAllFromUsers() = sqlClient deleteAllFrom MYSQL_USER

    fun deleteAllFromUserRoles() = sqlClient deleteAllFrom MYSQL_USER_ROLE

    fun countAllUserRoles() = sqlClient selectCountAllFrom MYSQL_USER_ROLE

    fun selectAllUsers() = sqlClient selectAllFrom MYSQL_USER

    fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom MYSQL_USER
                    where MYSQL_USER.firstname eq firstname
                    ).fetchFirst()
}
