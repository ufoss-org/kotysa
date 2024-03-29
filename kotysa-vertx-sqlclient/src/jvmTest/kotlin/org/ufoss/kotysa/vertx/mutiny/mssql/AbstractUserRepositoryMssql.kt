/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mssql

import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.MutinySqlClient

abstract class AbstractUserRepositoryMssql(protected val sqlClient: MutinySqlClient) : Repository {

    override fun init() {
        createTables()
            .chain { -> insertCompanies() }
            .chain { -> insertRoles() }
            .chain { -> insertUsers() }
            .chain { -> insertUserRoles() }
            .await().indefinitely()
    }

    override fun delete() {
        deleteAllFromUserRoles()
            .chain { -> deleteAllFromUsers() }
            .chain { -> deleteAllFromRole() }
            .chain { -> deleteAllFromCompanies() }
            .await().indefinitely()
    }

    private fun createTables() =
        (sqlClient createTableIfNotExists MssqlCompanies)
            .chain { -> sqlClient createTableIfNotExists MssqlRoles }
            .chain { -> sqlClient createTableIfNotExists MssqlUsers }
            .chain { -> sqlClient createTableIfNotExists MssqlUserRoles }

    private fun insertRoles() = sqlClient.insert(roleUser, roleAdmin, roleGod)

    private fun insertUsers() = sqlClient.insert(userJdoe, userBboss)

    private fun insertUserRoles() = sqlClient insert userRoleBboss

    private fun insertCompanies() = sqlClient.insert(companyBigPharma, companyBigBrother)

    private fun deleteAllFromUsers() = sqlClient deleteAllFrom MssqlUsers

    private fun deleteAllFromRole() = sqlClient deleteAllFrom MssqlRoles

    private fun deleteAllFromCompanies() = sqlClient deleteAllFrom MssqlCompanies

    fun deleteAllFromUserRoles() = sqlClient deleteAllFrom MssqlUserRoles

    fun countAllUserRoles() = sqlClient selectCountAllFrom MssqlUserRoles

    fun selectAllUsers() = sqlClient selectAllFrom MssqlUsers

    fun selectFirstByFirstname(firstname: String) =
        (sqlClient selectFrom MssqlUsers
                where MssqlUsers.firstname eq firstname
                ).fetchFirst()
}
