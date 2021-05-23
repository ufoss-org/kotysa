/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.sample

import org.ufoss.kotysa.mssql.MssqlTable
import org.ufoss.kotysa.tables

data class MsSqlUser(
        val firstname: String,
        val lastname: String,
        val isAdmin: Boolean,
        val alias: String? = null,
        val id: Int?
)

object MSSQL_USER : MssqlTable<MsSqlUser>() {
    val id = identityInteger(MsSqlUser::id)
            .primaryKey()
    val firstname = varchar(MsSqlUser::firstname, "fname")
    val lastname = varchar(MsSqlUser::lastname, "lname")
    val isAdmin = bit(MsSqlUser::isAdmin)
    val alias = varchar(MsSqlUser::alias)
}

fun mssqlTables() = tables().mssql(MSSQL_USER)
