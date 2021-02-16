/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.sample

import org.ufoss.kotysa.postgresql.PostgresqlTable
import org.ufoss.kotysa.tables
import java.util.*

data class PostgresqlUser(
        val firstname: String,
        val lastname: String,
        val isAdmin: Boolean,
        val alias: String? = null,
        val id: UUID
)

object POSTGRESQL_USER : PostgresqlTable<PostgresqlUser>() {
    val id = uuid(PostgresqlUser::id)
            .primaryKey()
    val firstname = varchar(PostgresqlUser::firstname, "fname")
    val lastname = varchar(PostgresqlUser::lastname, "lname")
    val isAdmin = boolean(PostgresqlUser::isAdmin)
    val alias = varchar(PostgresqlUser::alias)
}

fun postgresqlTables() = tables().postgresql(POSTGRESQL_USER)
