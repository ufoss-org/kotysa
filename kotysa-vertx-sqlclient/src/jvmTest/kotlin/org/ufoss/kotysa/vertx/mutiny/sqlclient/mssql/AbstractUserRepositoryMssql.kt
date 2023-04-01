/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mssql

import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySqlClient

abstract class AbstractUserRepositoryMssql(protected val sqlClient: MutinySqlClient) : Repository {

    override fun init() {
        createTables()
            .chain { -> insertRoles() }
            .chain { -> insertUsers() }
            .chain { -> insertUserRoles() }
            .await().indefinitely()
    }

    override fun delete() {
        deleteAllFromUserRoles()
            .chain { -> deleteAllFromUsers() }
            .chain { -> deleteAllFromRole() }
            .await().indefinitely()
    }

    private fun createTables() =
        (sqlClient createTableIfNotExists MssqlRoles)
            .chain { -> sqlClient createTableIfNotExists MssqlUsers }
            .chain { -> sqlClient createTableIfNotExists MssqlUserRoles }

    private fun insertRoles() = sqlClient.insert(roleUser, roleAdmin, roleGod)

    private fun insertUsers() = sqlClient.insert(userJdoe, userBboss)

    private fun insertUserRoles() = sqlClient insert userRoleBboss

    private fun deleteAllFromUsers() = sqlClient deleteAllFrom MssqlUsers

    private fun deleteAllFromRole() = sqlClient deleteAllFrom MssqlRoles

    fun deleteAllFromUserRoles() = sqlClient deleteAllFrom MssqlUserRoles

    fun countAllUserRoles() = sqlClient selectCountAllFrom MssqlUserRoles

    fun selectAllUsers() = sqlClient selectAllFrom MssqlUsers

    fun selectFirstByFirstname(firstname: String) =
        (sqlClient selectFrom MssqlUsers
                where MssqlUsers.firstname eq firstname
                ).fetchFirst()
}
