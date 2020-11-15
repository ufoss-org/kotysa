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

object H2_USER : H2Table<H2User>() {
    val id = column { it[H2User::id].uuid() }
            .primaryKey()
    val firstname = column { it[H2User::firstname].varchar {
        name = "fname"
    } }
    val lastname = column { it[H2User::lastname].varchar {
        name = "lname"
    } }
    val isAdmin = column { it[H2User::isAdmin].boolean() }
    val alias = column { it[H2User::alias].varchar() }
}

fun h2Tables() = tables().h2(H2_USER)
