/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.repositories

import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.MutinySqlClient

abstract class AbstractMutinyUserRepository<T : Roles, U : Users, V : UserRoles, W : Companies>(
    protected val sqlClient: MutinySqlClient,
    protected val tableRoles: T,
    protected val tableUsers: U,
    protected val tableUserRoles: V,
    protected val tableCompanies: W,
) : Repository {

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
        (sqlClient createTableIfNotExists tableCompanies)
            .chain { -> sqlClient createTableIfNotExists tableRoles }
            .chain { -> sqlClient createTableIfNotExists tableUsers }
            .chain { -> sqlClient createTableIfNotExists tableUserRoles }

    private fun insertCompanies() = sqlClient.insert(companyBigPharma, companyBigBrother)

    private fun insertRoles() = sqlClient.insert(roleUser, roleAdmin, roleGod)

    private fun insertUsers() = sqlClient.insert(userJdoe, userBboss)

    private fun insertUserRoles() = sqlClient insert userRoleBboss

    private fun deleteAllFromUsers() = sqlClient deleteAllFrom tableUsers

    private fun deleteAllFromRole() = sqlClient deleteAllFrom tableRoles

    fun deleteAllFromUserRoles() = sqlClient deleteAllFrom tableUserRoles

    private fun deleteAllFromCompanies() = sqlClient deleteAllFrom tableCompanies

    fun countAllUserRoles() = sqlClient selectCountAllFrom tableUserRoles

    fun selectAllUsers() = sqlClient selectAllFrom tableUsers

    fun selectFirstByFirstname(firstname: String) =
        (sqlClient selectFrom tableUsers
                where tableUsers.firstname eq firstname
                ).fetchFirst()
}
