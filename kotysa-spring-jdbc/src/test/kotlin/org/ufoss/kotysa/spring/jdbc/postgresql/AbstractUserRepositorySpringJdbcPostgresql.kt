/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*


abstract class AbstractUserRepositorySpringJdbcPostgresql(client: JdbcOperations) : Repository {

    protected val sqlClient = client.sqlClient(postgresqlTables)

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
        sqlClient createTable POSTGRESQL_ROLE
        sqlClient createTable POSTGRESQL_USER
        sqlClient createTable POSTGRESQL_USER_ROLE
    }

    private fun insertRoles() {
        sqlClient.insert(roleUser, roleAdmin, roleGod)
    }

    private fun insertUsers() {
        sqlClient.insert(userJdoe, userBboss)
    }

    private fun insertUserRoles() {
        sqlClient insert userRoleBboss
    }

    private fun deleteAllFromUsers() = sqlClient deleteAllFrom POSTGRESQL_USER

    private fun deleteAllFromRole() = sqlClient deleteAllFrom POSTGRESQL_ROLE

    fun deleteAllFromUserRoles() = sqlClient deleteAllFrom POSTGRESQL_USER_ROLE

    fun countAllUserRoles() = sqlClient selectCountAllFrom POSTGRESQL_USER_ROLE

    fun selectAllUsers() = sqlClient selectAllFrom POSTGRESQL_USER

    fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom POSTGRESQL_USER
                    where POSTGRESQL_USER.firstname eq firstname
                    ).fetchFirstOrNull()
}
