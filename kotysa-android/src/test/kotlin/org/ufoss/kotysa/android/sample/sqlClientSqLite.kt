/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android.sample

import android.database.sqlite.SQLiteOpenHelper
import org.ufoss.kotysa.android.sqlClient
import org.ufoss.kotysa.sqlite.SqLiteTable
import org.ufoss.kotysa.tables

@Suppress("UNUSED_VARIABLE")
class UserRepositorySqLite(sqLiteOpenHelper: SQLiteOpenHelper) {

    data class Role(
            val label: String,
            val id: String
    )

    data class User(
            val firstname: String,
            val lastname: String,
            val isAdmin: Boolean,
            val roleId: String,
            val alias: String? = null,
            val id: String
    )

    object SQLITE_ROLE : SqLiteTable<Role>("roles") {
        val id = text(Role::id).primaryKey()
        val label = text(Role::label)
    }

    object SQLITE_USER : SqLiteTable<User>("users") {
        val id = text(User::id).primaryKey()
        val firstname = text(User::firstname, "fname")
        val lastname = text(User::lastname, "lname")
        val isAdmin = integer(User::isAdmin)
        val roleId = text(User::roleId)
                .foreignKey(SQLITE_ROLE.id, "FK_users_roles")
        val alias = text(User::alias)
    }

    val tables = tables().sqlite(
            SQLITE_ROLE,
            SQLITE_USER,
    )

    private val roleUser = Role("user", "ghi")
    private val roleAdmin = Role("admin", "jkl")

    private val userJdoe = User("John", "Doe", false, roleUser.id, id = "abc")
    private val userBboss = User("Big", "Boss", true, roleAdmin.id, "TheBoss", "def")

    private class UserWithRoleDto(
            val lastname: String,
            val role: String
    )

    private val sqlClient = sqLiteOpenHelper.sqlClient(tables)

    fun simplifiedExample() {
        sqlClient createTable SQLITE_ROLE
        sqlClient deleteAllFrom SQLITE_ROLE
        sqlClient.insert(roleUser, roleAdmin)

        sqlClient createTable SQLITE_USER
        sqlClient deleteAllFrom SQLITE_USER
        sqlClient.insert(userJdoe, userBboss)

        // val count = sqlClient countAll<User>()

        val all = sqlClient selectAllFrom SQLITE_USER

        val johny = (sqlClient select { UserWithRoleDto(it[SQLITE_USER.lastname], it[SQLITE_ROLE.label]) }
                from SQLITE_USER innerJoin SQLITE_ROLE on SQLITE_USER.roleId eq SQLITE_ROLE.id
                where SQLITE_USER.alias eq "Johny"
                // null String accepted        ^^^^^ , if alias=null, gives "WHERE user.alias IS NULL"
                or SQLITE_USER.alias eq "Johnny"
                ).fetchFirst()

        val nbUpdated = (sqlClient update SQLITE_USER
                set SQLITE_USER.lastname eq "NewLastName"
                innerJoin SQLITE_ROLE on SQLITE_USER.roleId eq SQLITE_ROLE.id
                where SQLITE_ROLE.label eq roleUser.label
                // null String forbidden      ^^^^^^^^^^^^
                ).execute()

        val nbDeleted = (sqlClient deleteFrom SQLITE_USER
                innerJoin SQLITE_ROLE on SQLITE_USER.roleId eq SQLITE_ROLE.id
                where SQLITE_ROLE.label eq roleUser.label
                ).execute()
    }
}
