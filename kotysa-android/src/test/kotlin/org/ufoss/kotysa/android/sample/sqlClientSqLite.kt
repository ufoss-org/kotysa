/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android.sample

import android.database.sqlite.SQLiteOpenHelper
import org.ufoss.kotysa.android.sqlClient

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

    val tables =
            org.ufoss.kotysa.tables().sqlite {
                // choose database type
                table<Role> {
                    name = "roles"
                    column { it[Role::id].text().primaryKey() }
                    column { it[Role::label].text() }
                }
                table<User> {
                    name = "users"
                    column { it[User::id].text().primaryKey() }
                    column { it[User::firstname].text().name("fname") }
                    column { it[User::lastname].text().name("lname") }
                    column { it[User::isAdmin].integer() }
                    column { it[User::roleId].text() }
                    column { it[User::alias].text() }
                    foreignKey<Role> { columns(it[User::roleId]) }
                }
            }

    val roleUser = Role("user", "ghi")
    val roleAdmin = Role("admin", "jkl")

    val userJdoe = User("John", "Doe", false, roleUser.id, id = "abc")
    val userBboss = User("Big", "Boss", true, roleAdmin.id, "TheBoss", "def")

    private class UserWithRoleDto(
            val lastname: String,
            val role: String
    )

    private val sqlClient = sqLiteOpenHelper.sqlClient(tables)

    fun simplifiedExample() = sqlClient.run {
        createTable<User>()
        deleteAllFromTable<User>()
        insert(userJdoe, userBboss)

        val count = countAll<User>()

        val all = selectAll<User>()

        val johny = select { UserWithRoleDto(it[User::lastname], it[Role::label]) }
                .innerJoin<Role>().on { it[User::roleId] }
                .where { it[User::alias] eq "Johny" }
                // null String accepted        ^^^^^ , if alias=null, gives "WHERE user.alias IS NULL"
                .or { it[User::alias] eq "Johnny" }
                .fetchFirst()

        val nbUpdated = updateTable<User>()
                .set { it[User::lastname] = "NewLastName" }
                .innerJoin<Role>().on { it[User::roleId] }
                .where { it[Role::label] eq roleUser.label }
                // null String forbidden      ^^^^^^^^^^^^
                .execute()

        val nbDeleted = deleteFromTable<User>()
                .innerJoin<Role>().on { it[User::roleId] }
                .where { it[Role::label] eq roleUser.label }
                .execute()
    }
}
