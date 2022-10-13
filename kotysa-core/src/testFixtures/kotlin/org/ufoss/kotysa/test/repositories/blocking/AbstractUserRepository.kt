/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import org.ufoss.kotysa.SqlClient
import org.ufoss.kotysa.test.*

abstract class AbstractUserRepository<T : Roles, U : Users, V : UserRoles>(
    protected val sqlClient: SqlClient,
    protected val tableRoles: T,
    protected val tableUsers: U,
    protected val tableUserRoles: V,
) : Repository {

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
        sqlClient createTableIfNotExists tableRoles
        sqlClient createTableIfNotExists tableUsers
        sqlClient createTableIfNotExists tableUserRoles
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

    private fun deleteAllFromUsers() = sqlClient deleteAllFrom tableUsers

    private fun deleteAllFromRole() = sqlClient deleteAllFrom tableRoles

    fun deleteAllFromUserRoles() = sqlClient deleteAllFrom tableUserRoles

    fun countAllUserRoles() = sqlClient selectCountAllFrom tableUserRoles

    fun selectAllUsers() = sqlClient selectAllFrom tableUsers

    fun selectFirstByFirstname(firstname: String) =
        (sqlClient selectFrom tableUsers
                where tableUsers.firstname eq firstname
                ).fetchFirstOrNull()
}
