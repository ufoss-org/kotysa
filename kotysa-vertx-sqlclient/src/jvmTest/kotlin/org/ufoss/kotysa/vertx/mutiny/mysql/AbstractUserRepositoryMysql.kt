/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mysql

import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.MutinySqlClient

abstract class AbstractUserRepositoryMysql(protected val sqlClient: MutinySqlClient) : Repository {

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
        (sqlClient createTableIfNotExists MysqlCompanies)
            .chain { -> sqlClient createTableIfNotExists MysqlRoles }
            .chain { -> sqlClient createTableIfNotExists MysqlUsers }
            .chain { -> sqlClient createTableIfNotExists MysqlUserRoles }

    private fun insertRoles() = sqlClient.insert(roleUser, roleAdmin, roleGod)

    private fun insertUsers() = sqlClient.insert(userJdoe, userBboss)

    private fun insertUserRoles() = sqlClient insert userRoleBboss

    private fun insertCompanies() = sqlClient.insert(companyBigPharma, companyBigBrother)

    private fun deleteAllFromUsers() = sqlClient deleteAllFrom MysqlUsers

    private fun deleteAllFromRole() = sqlClient deleteAllFrom MysqlRoles

    fun deleteAllFromUserRoles() = sqlClient deleteAllFrom MysqlUserRoles

    private fun deleteAllFromCompanies() = sqlClient deleteAllFrom MysqlCompanies

    fun countAllUserRoles() = sqlClient selectCountAllFrom MysqlUserRoles

    fun selectAllUsers() = sqlClient selectAllFrom MysqlUsers

    fun selectFirstByFirstname(firstname: String) =
        (sqlClient selectFrom MysqlUsers
                where MysqlUsers.firstname eq firstname
                ).fetchFirst()
}
