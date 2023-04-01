/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.sample

import org.ufoss.kotysa.oracle.OracleTable
import org.ufoss.kotysa.tables

data class OracleUser(
    val firstname: String,
    val lastname: String,
    val isAdmin: Boolean,
    val alias: String? = null,
    val id: Int?
)

object OracleUsers : OracleTable<OracleUser>() {
    val id = number(OracleUser::id).identity()
        .primaryKey()
    val firstname = varchar2(OracleUser::firstname, "fname")
    val lastname = varchar2(OracleUser::lastname, "lname")
    val isAdmin = number(OracleUser::isAdmin)
    val alias = varchar2(OracleUser::alias)
}

fun oracleTables() = tables().oracle(OracleUsers)
