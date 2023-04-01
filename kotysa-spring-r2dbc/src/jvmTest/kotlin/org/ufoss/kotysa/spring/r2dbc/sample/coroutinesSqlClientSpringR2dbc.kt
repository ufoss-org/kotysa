/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.sample

import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.h2.H2Table
import org.ufoss.kotysa.spring.r2dbc.coSqlClient
import org.ufoss.kotysa.tables
import java.util.*


@Suppress("UNUSED_VARIABLE")
class UserRepositorySpringR2dbcCoroutines(dbClient: DatabaseClient) {

    data class Role(
            val label: String,
            val id: UUID = UUID.randomUUID()
    )

    data class User(
            val firstname: String,
            val lastname: String,
            val isAdmin: Boolean,
            val roleId: UUID,
            val alias: String? = null,
            val id: UUID = UUID.randomUUID()
    )

    object Roles : H2Table<Role>("roles") {
        val id = uuid(Role::id)
                .primaryKey()
        val label = varchar(Role::label)
    }

    object Users : H2Table<User>("users") {
        val id = uuid(User::id)
                .primaryKey("PK_users")
        val firstname = varchar(User::firstname, "fname")
        val lastname = varchar(User::lastname, "lname")
        val isAdmin = boolean(User::isAdmin)
        val roleId = uuid(User::roleId)
                .foreignKey(Roles.id, "FK_users_roles")
        val alias = varchar(User::alias)
    }

    private val tables = tables().h2(Roles, Users)

    private val roleUser = Role("user")
    private val roleAdmin = Role("admin")

    private val userJdoe = User("John", "Doe", false, roleUser.id)
    private val userBboss = User("Big", "Boss", true, roleAdmin.id, "TheBoss")

    private class UserWithRoleDto(
            val lastname: String,
            val role: String
    )

    private val sqlClient = dbClient.coSqlClient(tables)

    suspend fun simplifiedExample() = sqlClient.apply {
        sqlClient createTable Roles
        sqlClient deleteAllFrom Roles
        sqlClient.insert(roleUser, roleAdmin)

        sqlClient createTable Users
        sqlClient deleteAllFrom Users
        sqlClient.insert(userJdoe, userBboss)

        val count = sqlClient selectCountAllFrom Users

        val all = sqlClient selectAllFrom Users

        val johny = (sqlClient selectAndBuild { UserWithRoleDto(it[Users.lastname]!!, it[Roles.label]!!) }
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
