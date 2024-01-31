package com.sample

import org.ufoss.kotysa.h2.H2Table
import java.time.LocalDateTime
import java.util.*

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
    val creationTime: LocalDateTime = LocalDateTime.now(),
    val id: Int = 0
)

/**
 * Not an entity
 * name = first_name and last_name
 */
data class UserDto(
    val name: String,
    val alias: String?,
    val role: String
)

// Mapping

object Roles : H2Table<Role>() {
    val id = uuid(Role::id)
        .primaryKey()
    val label = varchar(Role::label)
        .unique()
}

object Users : H2Table<User>() {
    val id = integer(User::id)
        .identity()
        .primaryKey()
    val firstname = varchar(User::firstname)
    val lastname = varchar(User::lastname)
    val isAdmin = boolean(User::isAdmin)
    val roleId = uuid(User::roleId)
        .foreignKey(Roles.id)
    val alias = varchar(User::alias)
    val creationTime = dateTime(User::creationTime)
}
