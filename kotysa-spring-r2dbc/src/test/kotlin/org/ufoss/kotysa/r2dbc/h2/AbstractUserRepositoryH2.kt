/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.*


abstract class AbstractUserRepositoryH2(
        protected val sqlClient: ReactorSqlClient,
) : Repository {

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
            (sqlClient createTableIfNotExists H2_ROLE)
                    .then(sqlClient createTableIfNotExists H2_USER)
                    .then(sqlClient createTableIfNotExists H2_USER_ROLE)

    private fun insertRoles() =
            sqlClient.insert(roleUser, roleAdmin, roleGod, roleGodBis)

    private fun insertUsers() =
            sqlClient.insert(userJdoe, userBboss)

    private fun insertUserRoles() = sqlClient insert userRoleBboss

    private fun deleteAllFromUsers() = sqlClient deleteAllFrom H2_USER

    private fun deleteAllFromRole() = sqlClient deleteAllFrom H2_ROLE

    fun deleteAllFromUserRoles() = sqlClient deleteAllFrom H2_USER_ROLE

    fun countAllUserRoles() = sqlClient selectCountAllFrom H2_USER_ROLE

    fun selectAllUsers() = sqlClient selectAllFrom H2_USER

    fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom H2_USER
                    where H2_USER.firstname eq firstname
                    ).fetchFirst()
}
