/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.ufoss.kotysa.ReactorSqlClient
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
            (sqlClient createTableIfNotExists H2Roles)
                    .then(sqlClient createTableIfNotExists H2Users)
                    .then(sqlClient createTableIfNotExists H2UserRoles)

    private fun insertRoles() =
            sqlClient.insert(roleUser, roleAdmin, roleGod)

    private fun insertUsers() =
            sqlClient.insert(userJdoe, userBboss)

    private fun insertUserRoles() = sqlClient insert userRoleBboss

    private fun deleteAllFromUsers() = sqlClient deleteAllFrom H2Users

    private fun deleteAllFromRole() = sqlClient deleteAllFrom H2Roles

    fun deleteAllFromUserRoles() = sqlClient deleteAllFrom H2UserRoles

    fun countAllUserRoles() = sqlClient selectCountAllFrom H2UserRoles

    fun selectAllUsers() = sqlClient selectAllFrom H2Users

    fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom H2Users
                    where H2Users.firstname eq firstname
                    ).fetchFirst()
}
