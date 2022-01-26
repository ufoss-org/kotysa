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
            (sqlClient createTableIfNotExists MARIADB_ROLE)
                    .then(sqlClient createTableIfNotExists MARIADB_USER)
                    .then(sqlClient createTableIfNotExists MARIADB_USER_ROLE)

    private fun insertRoles() = sqlClient.insert(roleUser, roleAdmin, roleGod, roleGodBis)

    private fun insertUsers() = sqlClient.insert(userJdoe, userBboss)

    private fun insertUserRoles() = sqlClient insert userRoleBboss

    private fun deleteAllFromRole() = sqlClient deleteAllFrom MARIADB_ROLE

    private fun deleteAllFromUsers() = sqlClient deleteAllFrom MARIADB_USER

    fun deleteAllFromUserRoles() = sqlClient deleteAllFrom MARIADB_USER_ROLE

    fun countAllUserRoles() = sqlClient selectCountAllFrom MARIADB_USER_ROLE

    fun selectAllUsers() = sqlClient selectAllFrom MARIADB_USER

    fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom MARIADB_USER
                    where MARIADB_USER.firstname eq firstname
                    ).fetchFirst()
}
