/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.sample

import org.ufoss.kotysa.sqlite.SqLiteTable
import org.ufoss.kotysa.tables

data class SqliteUser(
        val firstname: String,
        val lastname: String,
        val isAdmin: Boolean,
        val alias: String? = null,
        val id: Int?
)

object SqliteUsers : SqLiteTable<SqliteUser>() {
    val id = autoIncrementInteger(SqliteUser::id)
            .primaryKey()
    val firstname = text(SqliteUser::firstname, "fname")
    val lastname = text(SqliteUser::lastname, "lname")
    val isAdmin = integer(SqliteUser::isAdmin)
    val alias = text(SqliteUser::alias)
}

fun sqLiteTables() = tables().sqlite(SqliteUsers)
