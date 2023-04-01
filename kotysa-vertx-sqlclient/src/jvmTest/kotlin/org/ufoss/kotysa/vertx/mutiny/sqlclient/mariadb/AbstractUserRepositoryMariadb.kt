/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mariadb

import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySqlClient

abstract class AbstractUserRepositoryMariadb(protected val sqlClient: MutinySqlClient) : Repository {

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
        (sqlClient createTableIfNotExists MariadbRoles)
            .chain { -> sqlClient createTableIfNotExists MariadbUsers }
            .chain { -> sqlClient createTableIfNotExists MariadbUserRoles }

    private fun insertRoles() = sqlClient.insert(roleUser, roleAdmin, roleGod)

    private fun insertUsers() = sqlClient.insert(userJdoe, userBboss)

    private fun insertUserRoles() = sqlClient insert userRoleBboss

    private fun deleteAllFromUsers() = sqlClient deleteAllFrom MariadbUsers

    private fun deleteAllFromRole() = sqlClient deleteAllFrom MariadbRoles

    fun deleteAllFromUserRoles() = sqlClient deleteAllFrom MariadbUserRoles

    fun countAllUserRoles() = sqlClient selectCountAllFrom MariadbUserRoles

    fun selectAllUsers() = sqlClient selectAllFrom MariadbUsers

    fun selectFirstByFirstname(firstname: String) =
        (sqlClient selectFrom MariadbUsers
                where MariadbUsers.firstname eq firstname
                ).fetchFirst()
}
