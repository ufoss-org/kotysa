package com.sample

import kotlinx.coroutines.runBlocking
import org.ufoss.kotysa.R2dbcSqlClient
import java.util.*

private val role_user_uuid = UUID.fromString("79e9eb45-2835-49c8-ad3b-c951b591bc7f")
private val role_admin_uuid = UUID.fromString("67d4306e-d99d-4e54-8b1d-5b1e92691a4e")

class UserRepository(private val client: R2dbcSqlClient) {

    suspend fun count() = client selectCountAllFrom Users

    fun findAll() = client selectAllFrom Users

    suspend fun findOne(id: Int) =
        (client selectFrom Users
                where Users.id eq id
                ).fetchOne()!!

    fun selectWithJoin() =
        (client.selectAndBuild {
            UserDto("${it[Users.firstname]} ${it[Users.lastname]}", it[Users.alias], it[Roles.label]!!)
        } from Users innerJoin Roles on Users.roleId eq Roles.id
                ).fetchAll()

    suspend fun deleteAll() = client deleteAllFrom Users

    suspend fun save(user: User) = client.transactional {
        client insert user
    }

    fun init() = runBlocking {
        client createTableIfNotExists Users
        deleteAll()
        save(User("John", "Doe", false, role_user_uuid, id = 123))
        save(User("Big", "Boss", true, role_admin_uuid, "TheBoss"))
    }
}

class RoleRepository(private val client: R2dbcSqlClient) {
    suspend fun deleteAll() = client deleteAllFrom Roles

    suspend fun save(role: Role) = client insert role

    fun init() = runBlocking {
        client createTableIfNotExists Roles
        deleteAll()
        save(Role("user", role_user_uuid))
        save(Role("admin", role_admin_uuid))
        save(Role("god"))
    }
}
