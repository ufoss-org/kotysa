/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.sample

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.tables
import java.util.*


@Suppress("UNUSED_VARIABLE")
class UserRepositorySpringJdbc(client: JdbcOperations) {

    private data class Role(
            val label: String,
            val id: UUID = UUID.randomUUID()
    )

    private data class User(
            val firstname: String,
            val lastname: String,
            val isAdmin: Boolean,
            val roleId: UUID,
            val alias: String? = null,
            val id: UUID = UUID.randomUUID()
    )

    private val tables =
            tables().h2 {
                table<Role> {
                    name = "roles"
                    column { it[Role::id].uuid() }
                            .primaryKey()
                    column { it[Role::label].varchar() }
                }
                table<User> {
                    name = "users"
                    column { it[User::id].uuid() }
                            .primaryKey()
                    column { it[User::firstname].varchar().name("fname") }
                    column { it[User::lastname].varchar().name("lname") }
                    column { it[User::isAdmin].boolean() }
                    column { it[User::roleId].uuid() }
                            .foreignKey<Role>()
                    column { it[User::alias].varchar() }
                }
            }

    private val roleUser = Role("user")
    private val roleAdmin = Role("admin")

    private val userJdoe = User("John", "Doe", false, roleUser.id)
    private val userBboss = User("Big", "Boss", true, roleAdmin.id, "TheBoss")

    private class UserWithRoleDto(
            val lastname: String,
            val role: String
    )

    private val sqlClient = client.sqlClient(tables)

    fun simplifiedExample() = sqlClient.apply {
        createTable<User>() // CREATE TABLE IF NOT EXISTS
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
