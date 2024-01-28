/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mariadb

import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.MutinySqlClient

abstract class AbstractUserRepositoryMariadb(protected val sqlClient: MutinySqlClient) : Repository {

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
        (sqlClient createTableIfNotExists MariadbCompanies)
            .chain { -> sqlClient createTableIfNotExists MariadbRoles }
            .chain { -> sqlClient createTableIfNotExists MariadbUsers }
            .chain { -> sqlClient createTableIfNotExists MariadbUserRoles }

    private fun insertRoles() = sqlClient.insert(roleUser, roleAdmin, roleGod)

    private fun insertUsers() = sqlClient.insert(userJdoe, userBboss)

    private fun insertUserRoles() = sqlClient insert userRoleBboss

    private fun insertCompanies() = sqlClient.insert(companyBigPharma, companyBigBrother)

    private fun deleteAllFromUsers() = sqlClient deleteAllFrom MariadbUsers

    private fun deleteAllFromRole() = sqlClient deleteAllFrom MariadbRoles

    private fun deleteAllFromCompanies() = sqlClient deleteAllFrom MariadbCompanies

    fun deleteAllFromUserRoles() = sqlClient deleteAllFrom MariadbUserRoles

    fun countAllUserRoles() = sqlClient selectCountAllFrom MariadbUserRoles

    fun selectAllUsers() = sqlClient selectAllFrom MariadbUsers

    fun selectFirstByFirstname(firstname: String) =
        (sqlClient selectFrom MariadbUsers
                where MariadbUsers.firstname eq firstname
                ).fetchFirst()
}
