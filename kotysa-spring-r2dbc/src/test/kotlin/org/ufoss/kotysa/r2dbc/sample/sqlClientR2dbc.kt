/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.sample

import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.h2.H2Table
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.tables
import java.util.*


@Suppress("UNUSED_VARIABLE")
class UserRepositoryR2dbc(dbClient: DatabaseClient) {

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

    object ROLE : H2Table<Role>("roles") {
        val id = ROLE.uuid(Role::id)
                .primaryKey()
        val label = ROLE.varchar(Role::label)
    }

    object USER : H2Table<User>("users") {
        val id = USER.uuid(User::id)
                .primaryKey("PK_users")
        val firstname = USER.varchar(User::firstname, "fname")
        val lastname = USER.varchar(User::lastname, "lname")
        val isAdmin = USER.boolean(User::isAdmin)
        val roleId = USER.uuid(User::roleId)
                .foreignKey(ROLE.id, "FK_users_roles")
        val alias = USER.varchar(User::alias)
    }

    private val tables = tables().h2(ROLE, USER)

    private val roleUser = Role("user")
    private val roleAdmin = Role("admin")

    private val userJdoe = User("John", "Doe", false, roleUser.id)
    private val userBboss = User("Big", "Boss", true, roleAdmin.id, "TheBoss")

    private class UserWithRoleDto(
            val lastname: String,
            val role: String
    )

    private val sqlClient = dbClient.sqlClient(tables)

    @Suppress("ReactiveStreamsUnusedPublisher")
    fun simplifiedExample() {
        (sqlClient createTable ROLE) // CREATE TABLE IF NOT EXISTS
                .then(sqlClient deleteAllFrom ROLE)
                .then(sqlClient.insert(roleUser, roleAdmin))

        val count = sqlClient selectCountAllFrom USER

        val all = sqlClient selectAllFrom USER

        val johny = (sqlClient select { UserWithRoleDto(it[USER.lastname]!!, it[ROLE.label]!!) }
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

        val admins = (sqlClient selectFrom USER
                innerJoin ROLE on USER.roleId eq ROLE.id
                where ROLE.label eq "admin"
                ).fetchAll() // returns all admin users
    }
}
