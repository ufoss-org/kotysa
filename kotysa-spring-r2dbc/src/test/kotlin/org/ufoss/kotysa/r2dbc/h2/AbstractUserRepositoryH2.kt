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
        (sqlClient createTable H2_ROLE)
            .then(sqlClient createTable H2_USER)

    private fun insertRoles() =
        sqlClient.insert(roleUser, roleAdmin, roleGod)

    private fun insertUsers() =
        sqlClient.insert(userJdoe, userBboss)

    fun deleteAllFromUsers() = sqlClient deleteAllFrom H2_USER

    private fun deleteAllFromRole() = sqlClient deleteAllFrom H2_ROLE

    fun selectAllUsers() = sqlClient selectAllFrom H2_USER

    fun selectFirstByFirstname(firstname: String) =
        (sqlClient selectFrom H2_USER
                where H2_USER.firstname eq firstname
                ).fetchFirst()
}
