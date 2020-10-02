/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.sample

import org.ufoss.kotysa.tables
import java.util.*

fun mysqlTables() =
        tables().mysql { // choose database type
            table<MySqlUser> {
                name = "users"
                column { it[MySqlUser::id].uuid() }
                        .primaryKey()
                column { it[MySqlUser::firstname].varchar().name("fname") }
                column { it[MySqlUser::lastname].varchar().name("lname") }
                column { it[MySqlUser::isAdmin].boolean() }
                column { it[MySqlUser::alias].varchar() }
            }
        }

data class MySqlUser(
        val firstname: String,
        val lastname: String,
        val isAdmin: Boolean,
        val alias: String? = null,
        val id: UUID
)
