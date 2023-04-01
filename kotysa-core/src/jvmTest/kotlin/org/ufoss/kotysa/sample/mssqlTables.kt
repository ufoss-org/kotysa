/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.sample

import org.ufoss.kotysa.mssql.MssqlTable
import org.ufoss.kotysa.tables

data class MssqlUser(
    val firstname: String,
    val lastname: String,
    val isAdmin: Boolean,
    val alias: String? = null,
    val id: Int?
)

object MssqlUsers : MssqlTable<MssqlUser>() {
    val id = integer(MssqlUser::id).identity()
        .primaryKey()
    val firstname = varchar(MssqlUser::firstname, "fname")
    val lastname = varchar(MssqlUser::lastname, "lname")
    val isAdmin = bit(MssqlUser::isAdmin)
    val alias = varchar(MssqlUser::alias)
}

fun mssqlTables() = tables().mssql(MssqlUsers)
