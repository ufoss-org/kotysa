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

    private object Roles : SqLiteTable<Role>("roles") {
        val id = text(Role::id).primaryKey()
        val label = text(Role::label)
    }

    private object Users : SqLiteTable<User>("users") {
        val id = text(User::id).primaryKey()
        val firstname = text(User::firstname, "fname")
        val lastname = text(User::lastname, "lname")
        val isAdmin = integer(User::isAdmin)
        val roleId = text(User::roleId)
                .foreignKey(Roles.id, "FK_users_roles")
        val alias = text(User::alias)
    }

    private val tables = tables().sqlite(
            Roles,
            Users,
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
        sqlClient createTable Roles
        sqlClient deleteAllFrom Roles
        sqlClient.insert(roleUser, roleAdmin)

        sqlClient createTable Users
        sqlClient deleteAllFrom Users
        sqlClient.insert(userJdoe, userBboss)

        val count = sqlClient selectCountAllFrom Users

        val all = sqlClient selectAllFrom Users

        val johny = (sqlClient select { UserWithRoleDto(it[Users.lastname]!!, it[Roles.label]!!) }
                from Users innerJoin Roles on Users.roleId eq Roles.id
                where Users.alias eq "Johny"
                // null String accepted        ^^^^^ , if alias=null, gives "WHERE user.alias IS NULL"
                or Users.alias eq "Johnny"
                ).fetchFirst()

        val nbUpdated = (sqlClient update Users
                set Users.lastname eq "NewLastName"
                innerJoin Roles on Users.roleId eq Roles.id
                where Roles.label eq roleUser.label
                // null String forbidden      ^^^^^^^^^^^^
                ).execute()

        val nbDeleted = (sqlClient deleteFrom Users
                innerJoin Roles on Users.roleId eq Roles.id
                where Roles.label eq roleUser.label
                ).execute()

        val admins = (sqlClient selectFrom Users
                innerJoin Roles on Users.roleId eq Roles.id
                where Roles.label eq "admin"
                ).fetchAll() // returns all admin users
    }
}
