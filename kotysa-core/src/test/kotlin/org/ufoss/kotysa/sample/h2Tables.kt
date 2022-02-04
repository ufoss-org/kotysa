/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.sample

import org.ufoss.kotysa.h2.H2Table
import org.ufoss.kotysa.tables
import java.util.*

data class H2User(
        val firstname: String,
        val lastname: String,
        val isAdmin: Boolean,
        val alias: String? = null,
        val id: UUID
)

object H2Users : H2Table<H2User>() {
    val id = uuid(H2User::id)
            .primaryKey()
    val firstname = varchar(H2User::firstname, "fname")
    val lastname = varchar(H2User::lastname, "lname")
    val isAdmin = boolean(H2User::isAdmin)
    val alias = varchar(H2User::alias)
}

fun h2Tables() = tables().h2(H2Users)
