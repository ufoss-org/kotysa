/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.*


abstract class AbstractUserRepositoryMysql(protected val sqlClient: ReactorSqlClient) : Repository {

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
            sqlClient.createTable<MysqlRole>()
                    .then(sqlClient.createTable<MysqlUser>())

    private fun insertRoles() = sqlClient.insert(mysqlUser, mysqlAdmin, mysqlGod)

    private fun insertUsers() = sqlClient.insert(mysqlJdoe, mysqlBboss)

    private fun deleteAllFromRole() = sqlClient.deleteAllFromTable<MysqlRole>()

    fun deleteAllFromUsers() = sqlClient.deleteAllFromTable<MysqlUser>()

    fun selectAllUsers() = sqlClient.selectAll<MysqlUser>()

    fun selectFirstByFirstname(firstname: String) = sqlClient.select<MysqlUser>()
            .where { it[MysqlUser::firstname] eq firstname }
            .fetchFirst()
}
