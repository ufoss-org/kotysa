package com.sample

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import org.ufoss.kotysa.h2.H2Table
import org.ufoss.kotysa.h2.dateTime
import java.util.*

data class Role(
    val label: String,
    val id: UUID = UUID.randomUUID()
)

@Serializable
data class User(
    val firstname: String,
    val lastname: String,
    val isAdmin: Boolean,
    @Serializable(with = UuidSerializer::class)
    val roleId: UUID,
    val alias: String? = null,
    val creationTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC),
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

object Users : H2Table<User>("users") {
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
