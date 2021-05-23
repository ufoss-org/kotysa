/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.*


abstract class AbstractUserRepositoryPostgresql(protected val sqlClient: ReactorSqlClient) : Repository {

    override fun init() {
        createTables()
                .then(insertRoles())
                .then(insertUsers())
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
            (sqlClient createTableIfNotExists POSTGRESQL_ROLE)
                    .then(sqlClient createTableIfNotExists POSTGRESQL_USER)
                    .then(sqlClient createTableIfNotExists POSTGRESQL_USER_ROLE)

    private fun insertRoles() = sqlClient.insert(roleUser, roleAdmin, roleGod, roleGodBis)

    private fun insertUsers() = sqlClient.insert(userJdoe, userBboss)

    private fun insertUserRoles() = sqlClient insert userRoleBboss

    private fun deleteAllFromUsers() = sqlClient deleteAllFrom POSTGRESQL_USER

    private fun deleteAllFromRole() = sqlClient deleteAllFrom POSTGRESQL_ROLE

    fun deleteAllFromUserRoles() = sqlClient deleteAllFrom POSTGRESQL_USER_ROLE

    fun countAllUserRoles() = sqlClient selectCountAllFrom POSTGRESQL_USER_ROLE

    fun selectAllUsers() = sqlClient selectAllFrom POSTGRESQL_USER

    fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom POSTGRESQL_USER
                    where POSTGRESQL_USER.firstname eq firstname
                    ).fetchFirst()
}
