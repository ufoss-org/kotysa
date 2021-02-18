/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2
/*
import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.*


abstract class AbstractUserRepositoryH2(
        protected val sqlClient: ReactorSqlClient,
) : Repository {

    override fun init() {
        createTables()
                .then(insertRoles())
                .then(insertUsers())
                .block()
    }

    override fun delete() {
        deleteAllFromUsers()
                .then(deleteAllFromRole())
                .block()
    }

    private fun createTables() =
            sqlClient.createTable<H2Role>()
                    .then(sqlClient.createTable<H2User>())

    private fun insertRoles() = sqlClient.insert(h2User, h2Admin, h2God)

    private fun insertUsers() = sqlClient.insert(h2Jdoe, h2Bboss)

    private fun deleteAllFromRole() = sqlClient.deleteAllFromTable<H2Role>()

    fun deleteAllFromUsers() = sqlClient.deleteAllFromTable<H2User>()

    fun selectAllUsers() = sqlClient.selectAll<H2User>()

    fun selectFirstByFirstname(firstname: String) = sqlClient.select<H2User>()
            .where { it[H2User::firstname] eq firstname }
            .fetchFirst()
}
*/