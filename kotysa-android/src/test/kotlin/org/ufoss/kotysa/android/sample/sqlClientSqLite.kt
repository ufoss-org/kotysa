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

    private data class Role(
            val label: String,
            val id: String
    )

    private data class User(
            val firstname: String,
            val lastname: String,
            val isAdmin: Boolean,
            val roleId: String,
            val alias: String? = null,
            val id: String
    )

    private object ROLE : SqLiteTable<Role>("roles") {
        val id = text(Role::id).primaryKey()
        val label = text(Role::label)
    }

    private object USER : SqLiteTable<User>("users") {
        val id = text(User::id).primaryKey()
        val firstname = text(User::firstname, "fname")
        val lastname = text(User::lastname, "lname")
        val isAdmin = integer(User::isAdmin)
        val roleId = text(User::roleId)
                .foreignKey(ROLE.id, "FK_users_roles")
        val alias = text(User::alias)
    }

    private val tables = tables().sqlite(
            ROLE,
            USER,
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
        sqlClient createTable ROLE
        sqlClient deleteAllFrom ROLE
        sqlClient.insert(roleUser, roleAdmin)

        sqlClient createTable USER
        sqlClient deleteAllFrom USER
        sqlClient.insert(userJdoe, userBboss)

        // val count = sqlClient countAll<User>()

        val all = sqlClient selectAllFrom USER

        val johny = (sqlClient select { UserWithRoleDto(it[USER.lastname], it[ROLE.label]) }
                from USER innerJoin ROLE on USER.roleId eq ROLE.id
                where USER.alias eq "Johny"
                // null String accepted        ^^^^^ , if alias=null, gives "WHERE user.alias IS NULL"
                or USER.alias eq "Johnny"
                ).fetchFirst()

        val nbUpdated = (sqlClient update USER
                set USER.lastname eq "NewLastName"
                innerJoin ROLE on USER.roleId eq ROLE.id
                where ROLE.label eq roleUser.label
                // null String forbidden      ^^^^^^^^^^^^
                ).execute()

        val nbDeleted = (sqlClient deleteFrom USER
                innerJoin ROLE on USER.roleId eq ROLE.id
                where ROLE.label eq roleUser.label
                ).execute()
    }
}
