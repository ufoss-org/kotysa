/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.test.*

abstract class AbstractUserRepositoryJdbcPostgresql(private val sqlClient: JdbcSqlClient) : Repository {

    override fun init() {
        createTables()
        insertRoles()
        insertUsers()
        insertUserRoles()
    }

    override fun delete() {
        deleteAllFromUserRoles()
        deleteAllFromUsers()
        deleteAllFromRole()
    }

    private fun createTables() {
        sqlClient createTableIfNotExists PostgresqlRoles
        sqlClient createTableIfNotExists PostgresqlUsers
        sqlClient createTableIfNotExists PostgresqlUserRoles
    }

    private fun insertRoles() {
        sqlClient.insert(roleUser, roleAdmin, roleGod, roleGodBis)
    }

    private fun insertUsers() {
        sqlClient.insert(userJdoe, userBboss)
    }

    private fun insertUserRoles() {
        sqlClient insert userRoleBboss
    }

    private fun deleteAllFromUsers() = sqlClient deleteAllFrom PostgresqlUsers

    private fun deleteAllFromRole() = sqlClient deleteAllFrom PostgresqlRoles

    fun deleteAllFromUserRoles() = sqlClient deleteAllFrom PostgresqlUserRoles

    fun countAllUserRoles() = sqlClient selectCountAllFrom PostgresqlUserRoles

    fun selectAllUsers() = sqlClient selectAllFrom PostgresqlUsers

    fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom PostgresqlUsers
                    where PostgresqlUsers.firstname eq firstname
                    ).fetchFirstOrNull()
}
