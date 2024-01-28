/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import kotlinx.coroutines.runBlocking
import org.ufoss.kotysa.CoroutinesSqlClient
import org.ufoss.kotysa.test.*

abstract class AbstractCoroutinesUserRepository<T : Roles, U : Users, V : UserRoles, W : Companies>(
    protected val sqlClient: CoroutinesSqlClient,
    protected val tableRoles: T,
    protected val tableUsers: U,
    protected val tableUserRoles: V,
    protected val tableCompanies: W,
) : Repository {

    override fun init() = runBlocking {
        createTables()
        insertCompanies()
        insertRoles()
        insertUsers()
        insertUserRoles()
    }

    override fun delete() = runBlocking<Unit> {
        deleteAllFromUserRoles()
        deleteAllFromUsers()
        deleteAllFromRole()
        deleteAllFromCompanies()
    }

    private suspend fun createTables() {
        sqlClient createTableIfNotExists tableCompanies
        sqlClient createTableIfNotExists tableRoles
        sqlClient createTableIfNotExists tableUsers
        sqlClient createTableIfNotExists tableUserRoles
    }

    private suspend fun insertCompanies() {
        sqlClient.insert(companyBigPharma, companyBigBrother)
    }

    private suspend fun insertRoles() {
        sqlClient.insert(roleUser, roleAdmin, roleGod)
    }

    private suspend fun insertUsers() {
        sqlClient.insert(userJdoe, userBboss)
    }

    private suspend fun insertUserRoles() {
        sqlClient insert userRoleBboss
    }

    private suspend fun deleteAllFromUsers() = sqlClient deleteAllFrom tableUsers

    private suspend fun deleteAllFromRole() = sqlClient deleteAllFrom tableRoles

    suspend fun deleteAllFromUserRoles() = sqlClient deleteAllFrom tableUserRoles

    private suspend fun deleteAllFromCompanies() = sqlClient deleteAllFrom tableCompanies

    suspend fun countAllUserRoles() = sqlClient selectCountAllFrom tableUserRoles

    fun selectAllUsers() = sqlClient selectAllFrom tableUsers

    suspend fun selectFirstByFirstname(firstname: String) =
        (sqlClient selectFrom tableUsers
                where tableUsers.firstname eq firstname
                ).fetchFirstOrNull()
}
