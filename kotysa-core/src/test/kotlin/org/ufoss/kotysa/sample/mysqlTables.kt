/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.sample

import org.ufoss.kotysa.mysql.MysqlTable
import org.ufoss.kotysa.tables

data class MySqlUser(
        val firstname: String,
        val lastname: String,
        val isAdmin: Boolean,
        val alias: String? = null,
        val id: Int?
)

object MYSQL_USER : MysqlTable<MySqlUser>() {
    val id = autoIncrementInteger(MySqlUser::id)
            .primaryKey()
    val firstname = varchar(MySqlUser::firstname, "fname")
    val lastname = varchar(MySqlUser::lastname, "lname")
    val isAdmin = boolean(MySqlUser::isAdmin)
    val alias = varchar(MySqlUser::alias)
}

fun mysqlTables() = tables().mysql(MYSQL_USER)
