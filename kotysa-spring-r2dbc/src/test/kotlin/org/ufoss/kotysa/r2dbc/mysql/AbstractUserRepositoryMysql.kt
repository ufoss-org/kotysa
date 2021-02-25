/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.*


abstract class AbstractUserRepositoryMysql(protected val sqlClient: ReactorSqlClient) : Repository {

    override fun init() {
        createTables()
                .then(insertRoles())
                .then(insertUsers())
                .block()
    }

    override fun delete() {
        deleteAllFromUsers()
                .then(deleteAllFromRole())
                .block()
    }

    private fun createTables() =
            (sqlClient createTable MYSQL_ROLE)
                    .then(sqlClient createTable MYSQL_USER)

    private fun insertRoles() = sqlClient.insert(roleUser, roleAdmin, roleGod)

    private fun insertUsers() = sqlClient.insert(userJdoe, userBboss)

    private fun deleteAllFromRole() = sqlClient deleteAllFrom MYSQL_ROLE

    fun deleteAllFromUsers() = sqlClient deleteAllFrom MYSQL_USER

    fun selectAllUsers() = sqlClient selectAllFrom MYSQL_USER

    fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom MYSQL_USER
                    where MYSQL_USER.firstname eq firstname
                    ).fetchFirst()
}
