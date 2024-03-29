/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.reactor

import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.*

abstract class AbstractReactorUserRepository<T : Roles, U : Users, V : UserRoles, W : Companies>(
    protected val sqlClient: ReactorSqlClient,
    protected val tableRoles: T,
    protected val tableUsers: U,
    protected val tableUserRoles: V,
    protected val tableCompanies: W,
) : Repository {

    override fun init() {
        createTables()
            .then(insertCompanies())
            .then(insertRoles())
            .then(insertUsers())
            .then(insertUserRoles())
            .block()
    }

    override fun delete() {
        deleteAllFromUserRoles()
            .then(deleteAllFromUsers())
            .then(deleteAllFromRole())
            .then(deleteAllFromCompanies())
            .block()
    }

    private fun createTables() =
        (sqlClient createTableIfNotExists tableCompanies)
            .then(sqlClient createTableIfNotExists tableRoles)
            .then(sqlClient createTableIfNotExists tableUsers)
            .then(sqlClient createTableIfNotExists tableUserRoles)

    private fun insertCompanies() = sqlClient.insert(companyBigPharma, companyBigBrother)

    private fun insertRoles() = sqlClient.insert(roleUser, roleAdmin, roleGod)

    private fun insertUsers() = sqlClient.insert(userJdoe, userBboss)

    private fun insertUserRoles() = sqlClient insert userRoleBboss

    private fun deleteAllFromUsers() = sqlClient deleteAllFrom tableUsers

    private fun deleteAllFromRole() = sqlClient deleteAllFrom tableRoles

    private fun deleteAllFromCompanies() = sqlClient deleteAllFrom tableCompanies

    fun deleteAllFromUserRoles() = sqlClient deleteAllFrom tableUserRoles

    fun countAllUserRoles() = sqlClient selectCountAllFrom tableUserRoles

    fun selectAllUsers() = sqlClient selectAllFrom tableUsers

    fun selectFirstByFirstname(firstname: String) =
        (sqlClient selectFrom tableUsers
                where tableUsers.firstname eq firstname
                ).fetchFirst()
}
