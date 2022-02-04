/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.ufoss.kotysa.spring.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.*


abstract class AbstractUserRepositoryPostgresql(protected val sqlClient: ReactorSqlClient) : Repository {

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
            (sqlClient createTableIfNotExists PostgresqlRoles)
                    .then(sqlClient createTableIfNotExists PostgresqlUsers)
                    .then(sqlClient createTableIfNotExists PostgresqlUserRoles)

    private fun insertRoles() = sqlClient.insert(roleUser, roleAdmin, roleGod, roleGodBis)

    private fun insertUsers() = sqlClient.insert(userJdoe, userBboss)

    private fun insertUserRoles() = sqlClient insert userRoleBboss

    private fun deleteAllFromUsers() = sqlClient deleteAllFrom PostgresqlUsers

    private fun deleteAllFromRole() = sqlClient deleteAllFrom PostgresqlRoles

    fun deleteAllFromUserRoles() = sqlClient deleteAllFrom PostgresqlUserRoles

    fun countAllUserRoles() = sqlClient selectCountAllFrom PostgresqlUserRoles

    fun selectAllUsers() = sqlClient selectAllFrom PostgresqlUsers

    fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom PostgresqlUsers
                    where PostgresqlUsers.firstname eq firstname
                    ).fetchFirst()
}
