/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.sample

import org.ufoss.kotysa.tables
import java.util.*

fun postgresqlTables() =
        tables().postgresql { // choose database type
            table<PostgresUser> {
                name = "users"
                column { it[PostgresUser::id].uuid().primaryKey() }
                column { it[PostgresUser::firstname].varchar().name("fname") }
                column { it[PostgresUser::lastname].varchar().name("lname") }
                column { it[PostgresUser::isAdmin].boolean() }
                column { it[PostgresUser::alias].varchar() }
            }
        }

data class PostgresUser(
        val firstname: String,
        val lastname: String,
        val isAdmin: Boolean,
        val alias: String? = null,
        val id: UUID
)
