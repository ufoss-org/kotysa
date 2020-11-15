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
    val id = column { it[SqLiteUser::id].autoIncrementInteger() }
            .primaryKey()
    val firstname = column { it[SqLiteUser::firstname].text {
        name = "fname"
    } }
    val lastname = column { it[SqLiteUser::lastname].text {
        name = "lname"
    } }
    val isAdmin = column { it[SqLiteUser::isAdmin].integer() }
    val alias = column { it[SqLiteUser::alias].text() }
}

fun sqLiteTables() = tables().sqlite(SQLITE_USER)
