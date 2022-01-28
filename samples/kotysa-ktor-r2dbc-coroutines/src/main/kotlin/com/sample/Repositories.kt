package com.sample

import kotlinx.coroutines.runBlocking
import org.ufoss.kotysa.CoroutinesSqlClient
import org.ufoss.kotysa.r2dbc.transaction.R2dbcTransactionalOp
import java.util.*

private val role_user_uuid = UUID.fromString("79e9eb45-2835-49c8-ad3b-c951b591bc7f")
private val role_admin_uuid = UUID.fromString("67d4306e-d99d-4e54-8b1d-5b1e92691a4e")

class UserRepository(
    private val client: CoroutinesSqlClient,
    private val operator: R2dbcTransactionalOp,
) {

    suspend fun count() = client selectCountAllFrom USER

    fun findAll() = client selectAllFrom USER

    suspend fun findOne(id: Int) =
        (client selectFrom USER
                where USER.id eq id
                ).fetchOne()!!

    fun selectWithJoin() =
        (client.select {
            UserDto("${it[USER.firstname]} ${it[USER.lastname]}", it[USER.alias], it[ROLE.label]!!)
        } from USER innerJoin ROLE on USER.roleId eq ROLE.id
                ).fetchAll()

    suspend fun deleteAll() = client deleteAllFrom USER

    suspend fun save(user: User) = operator.execute {
        client insert user
    }

    fun init() = runBlocking {
        client createTableIfNotExists USER
        deleteAll()
        save(User("John", "Doe", false, role_user_uuid, id = 123))
        save(User("Big", "Boss", true, role_admin_uuid, "TheBoss"))
    }
}

class RoleRepository(private val client: CoroutinesSqlClient) {
    suspend fun deleteAll() = client deleteAllFrom ROLE

    suspend fun save(role: Role) = client insert role

    fun init() = runBlocking {
        client createTableIfNotExists ROLE
        deleteAll()
        save(Role("user", role_user_uuid))
        save(Role("admin", role_admin_uuid))
        save(Role("god"))
    }
}
