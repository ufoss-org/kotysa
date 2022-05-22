package com.sample

import org.springframework.stereotype.Repository
import org.ufoss.kotysa.SqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransactionalOp
import java.util.*

private val role_user_uuid = UUID.fromString("79e9eb45-2835-49c8-ad3b-c951b591bc7f")
private val role_admin_uuid = UUID.fromString("67d4306e-d99d-4e54-8b1d-5b1e92691a4e")

@Repository
class UserRepository(
    private val client: SqlClient,
    private val operator: SpringJdbcTransactionalOp,
) {

    fun count() = client selectCountAllFrom Users

    fun findAll() = client selectAllFrom Users

    fun findOne(id: Int) =
        (client selectFrom Users
                where Users.id eq id
                ).fetchOne()!!

    fun selectWithJoin() =
        (client.selectAndBuild {
            UserDto("${it[Users.firstname]} ${it[Users.lastname]}", it[Users.alias], it[Roles.label]!!)
        } from Users innerJoin Roles on Users.roleId eq Roles.id
                ).fetchAll()

    fun deleteAll() = client deleteAllFrom Users

    fun save(user: User) = operator.transactional {
        client insert user
    }

    fun init() {
        client createTableIfNotExists Users
        deleteAll()
        save(User("John", "Doe", false, role_user_uuid, id = 123))
        save(User("Big", "Boss", true, role_admin_uuid, "TheBoss"))
    }
}

@Repository
class RoleRepository(private val client: SqlClient) {
    fun deleteAll() = client deleteAllFrom Roles

    fun save(role: Role) = client insert role

    fun init() {
        client createTableIfNotExists Roles
        deleteAll()
        save(Role("user", role_user_uuid))
        save(Role("admin", role_admin_uuid))
        save(Role("god"))
    }
}
