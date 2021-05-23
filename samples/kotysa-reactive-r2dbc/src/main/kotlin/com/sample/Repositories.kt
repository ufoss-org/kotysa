package com.sample

import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import java.util.*

private val role_user_uuid = UUID.fromString("79e9eb45-2835-49c8-ad3b-c951b591bc7f")
private val role_admin_uuid = UUID.fromString("67d4306e-d99d-4e54-8b1d-5b1e92691a4e")

class UserRepository(private val client: ReactorSqlClient) {

    fun count() = client selectCountAllFrom USER

    fun findAll() = client selectAllFrom USER

    fun findOne(id: Int) =
            (client selectFrom USER
                    where USER.id eq id
                    ).fetchOne()

    fun selectWithJoin() =
            (client.select {
                UserDto("${it[USER.firstname]} ${it[USER.lastname]}", it[USER.alias], it[ROLE.label]!!)
            }
                    from USER innerJoin ROLE on USER.roleId eq ROLE.id
                    ).fetchAll()

    fun deleteAll() = client deleteAllFrom USER

    fun save(user: User) = client insert user

    fun init() {
        (client createTableIfNotExists USER)
                .then(deleteAll())
                .then(save(User("John", "Doe", false, role_user_uuid, id = 123)))
                .then(save(User("Big", "Boss", true, role_admin_uuid, "TheBoss")))
                .block()
    }
}

class RoleRepository(private val client: ReactorSqlClient) {
    fun deleteAll() = client deleteAllFrom ROLE

    fun save(role: Role) = client insert role

    fun init() {
        (client createTableIfNotExists ROLE)
                .then(deleteAll())
                .then(save(Role("user", role_user_uuid)))
                .then(save(Role("admin", role_admin_uuid)))
                .then(save(Role("god")))
                .block()
    }
}
