/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.postgresql

import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.MutinySqlClient

abstract class AbstractUserRepositoryPostgresql(protected val sqlClient: MutinySqlClient) : Repository {

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
        (sqlClient createTableIfNotExists PostgresqlCompanies)
            .chain { -> sqlClient createTableIfNotExists PostgresqlRoles }
            .chain { -> sqlClient createTableIfNotExists PostgresqlUsers }
            .chain { -> sqlClient createTableIfNotExists PostgresqlUserRoles }

    private fun insertRoles() = sqlClient.insert(roleUser, roleAdmin, roleGod)

    private fun insertUsers() = sqlClient.insert(userJdoe, userBboss)

    private fun insertUserRoles() = sqlClient insert userRoleBboss

    private fun insertCompanies() = sqlClient.insert(companyBigPharma, companyBigBrother)

    private fun deleteAllFromUsers() = sqlClient deleteAllFrom PostgresqlUsers

    private fun deleteAllFromRole() = sqlClient deleteAllFrom PostgresqlRoles

    fun deleteAllFromUserRoles() = sqlClient deleteAllFrom PostgresqlUserRoles

    private fun deleteAllFromCompanies() = sqlClient deleteAllFrom PostgresqlCompanies

    fun countAllUserRoles() = sqlClient selectCountAllFrom PostgresqlUserRoles

    fun selectAllUsers() = sqlClient selectAllFrom PostgresqlUsers

    fun selectFirstByFirstname(firstname: String) =
        (sqlClient selectFrom PostgresqlUsers
                where PostgresqlUsers.firstname eq firstname
                ).fetchFirst()
}
