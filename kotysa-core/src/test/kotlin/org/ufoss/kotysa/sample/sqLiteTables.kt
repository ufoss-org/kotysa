/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.sample

import org.ufoss.kotysa.sqlite.SqLiteTable
import org.ufoss.kotysa.tables

data class SqLiteUser(
        val firstname: String,
        val lastname: String,
        val isAdmin: Boolean,
        val alias: String? = null,
        val id: Int?
)

object SQLITE_USER : SqLiteTable<SqLiteUser>() {
    val id = autoIncrementInteger(SqLiteUser::id)
            .primaryKey()
    val firstname = text(SqLiteUser::firstname, "fname")
    val lastname = text(SqLiteUser::lastname, "lname")
    val isAdmin = integer(SqLiteUser::isAdmin)
    val alias = text(SqLiteUser::alias)
}

fun sqLiteTables() = tables().sqlite(SQLITE_USER)
