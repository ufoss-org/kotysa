/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*


abstract class AbstractUserRepositorySpringJdbcMysql(client: JdbcOperations) : Repository {

    protected val sqlClient = client.sqlClient(mysqlTables)

    override fun init() {
        createTables()
        insertRoles()
        insertUsers()
    }

    override fun delete() {
        deleteAllFromUsers()
        deleteAllFromRole()
    }

    private fun createTables() {
        sqlClient.createTable<MysqlRole>()
        sqlClient.createTable<MysqlUser>()
    }

    private fun insertRoles() = sqlClient.insert(mysqlUser, mysqlAdmin, mysqlGod)

    private fun insertUsers() = sqlClient.insert(mysqlJdoe, mysqlBboss)

    private fun deleteAllFromRole() = sqlClient.deleteAllFromTable<MysqlRole>()

    fun deleteAllFromUsers() = sqlClient.deleteAllFromTable<MysqlUser>()

    fun selectAllUsers() = sqlClient.selectAll<MysqlUser>()

    fun selectFirstByFirstname(firstname: String) =
            sqlClient.select<MysqlUser>()
                    .where { it[MysqlUser::firstname] eq firstname }
                    .fetchFirstOrNull()
}
