/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.sample

import org.ufoss.kotysa.mariadb.MariadbTable
import org.ufoss.kotysa.tables

data class MariadblUser(
        val firstname: String,
        val lastname: String,
        val isAdmin: Boolean,
        val alias: String? = null,
        val id: Int?
)

object MariadbUsers : MariadbTable<MariadblUser>() {
    val id = autoIncrementInteger(MariadblUser::id)
            .primaryKey()
    val firstname = varchar(MariadblUser::firstname, "fname")
    val lastname = varchar(MariadblUser::lastname, "lname")
    val isAdmin = boolean(MariadblUser::isAdmin)
    val alias = varchar(MariadblUser::alias)
}

fun mariadbTables() = tables().mariadb(MariadbUsers)
