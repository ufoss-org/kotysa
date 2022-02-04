/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*


abstract class AbstractUserRepositorySpringJdbcH2(client: JdbcOperations) : Repository {

    protected val sqlClient = client.sqlClient(h2Tables)

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
        sqlClient createTableIfNotExists H2Roles
        sqlClient createTableIfNotExists H2Users
        sqlClient createTableIfNotExists H2UserRoles
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

    private fun deleteAllFromUsers() = sqlClient deleteAllFrom H2Users
    
    private fun deleteAllFromRole() = sqlClient deleteAllFrom H2Roles

    fun deleteAllFromUserRoles() = sqlClient deleteAllFrom H2UserRoles

    fun countAllUserRoles() = sqlClient selectCountAllFrom H2UserRoles

    fun selectAllUsers() = sqlClient selectAllFrom H2Users

    fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom H2Users
                    where H2Users.firstname eq firstname
                    ).fetchFirstOrNull()
}
