/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.*


abstract class AbstractUserRepositoryPostgresql(protected val sqlClient: ReactorSqlClient) : Repository {

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
            (sqlClient createTable POSTGRESQL_ROLE)
                    .then(sqlClient createTable POSTGRESQL_USER)

    private fun insertRoles() = sqlClient.insert(roleUser, roleAdmin, roleGod)

    private fun insertUsers() = sqlClient.insert(userJdoe, userBboss)

    fun deleteAllFromUsers() = sqlClient deleteAllFrom POSTGRESQL_USER

    private fun deleteAllFromRole() = sqlClient deleteAllFrom POSTGRESQL_ROLE

    fun selectAllUsers() = sqlClient selectAllFrom POSTGRESQL_USER

    fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom POSTGRESQL_USER
                    where POSTGRESQL_USER.firstname eq firstname
                    ).fetchFirst()
}
