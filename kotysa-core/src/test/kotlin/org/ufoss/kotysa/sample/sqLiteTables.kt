/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.sample

import org.ufoss.kotysa.tables

fun sqLiteTables() =
        tables().sqlite { // choose database type
            table<SqLiteUser> {
                name = "users"
                column { it[SqLiteUser::id].text() }
                        .primaryKey()
                column { it[SqLiteUser::firstname].text().name("fname") }
                column { it[SqLiteUser::lastname].text().name("lname") }
                column { it[SqLiteUser::isAdmin].integer() }
                column { it[SqLiteUser::alias].text() }
            }
        }

data class SqLiteUser(
        val firstname: String,
        val lastname: String,
        val isAdmin: Boolean,
        val alias: String? = null,
        val id: String
)
