/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.sample

import org.ufoss.kotysa.mysql.MysqlTable
import org.ufoss.kotysa.tables

data class MysqlUser(
        val firstname: String,
        val lastname: String,
        val isAdmin: Boolean,
        val alias: String? = null,
        val id: Int?
)

object MysqlUsers : MysqlTable<MysqlUser>() {
    val id = autoIncrementInteger(MysqlUser::id)
            .primaryKey()
    val firstname = varchar(MysqlUser::firstname, "fname")
    val lastname = varchar(MysqlUser::lastname, "lname")
    val isAdmin = boolean(MysqlUser::isAdmin)
    val alias = varchar(MysqlUser::alias)
}

fun mysqlTables() = tables().mysql(MysqlUsers)
