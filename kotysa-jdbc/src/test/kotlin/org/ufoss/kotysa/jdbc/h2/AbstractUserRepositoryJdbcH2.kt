/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.ufoss.kotysa.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import java.sql.Connection


abstract class AbstractUserRepositoryJdbcH2(connection: Connection) : Repository {

    protected val sqlClient = connection.sqlClient(h2Tables)

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
        sqlClient createTableIfNotExists H2_ROLE
        sqlClient createTableIfNotExists H2_USER
        sqlClient createTableIfNotExists H2_USER_ROLE
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

    private fun deleteAllFromUsers() = sqlClient deleteAllFrom H2_USER
    
    private fun deleteAllFromRole() = sqlClient deleteAllFrom H2_ROLE

    fun deleteAllFromUserRoles() = sqlClient deleteAllFrom H2_USER_ROLE

    fun countAllUserRoles() = sqlClient selectCountAllFrom H2_USER_ROLE

    fun selectAllUsers() = sqlClient selectAllFrom H2_USER

    fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom H2_USER
                    where H2_USER.firstname eq firstname
                    ).fetchFirstOrNull()
}