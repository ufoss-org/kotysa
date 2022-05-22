package com.sample

import org.springframework.stereotype.Repository
import org.ufoss.kotysa.spring.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.SpringR2dbcReactorTransactionalOp
import org.ufoss.kotysa.spring.r2dbc.transaction.transactional
import java.util.*

private val role_user_uuid = UUID.fromString("79e9eb45-2835-49c8-ad3b-c951b591bc7f")
private val role_admin_uuid = UUID.fromString("67d4306e-d99d-4e54-8b1d-5b1e92691a4e")

@Repository
class UserRepository(
    private val client: ReactorSqlClient,
    private val operator: SpringR2dbcReactorTransactionalOp,
) {

    fun count() = client selectCountAllFrom Users

    fun findAll() = client selectAllFrom Users

    fun findOne(id: Int) =
        (client selectFrom Users
                where Users.id eq id
                ).fetchOne()

    fun selectWithJoin() =
        (client.selectAndBuild {
            UserDto("${it[Users.firstname]} ${it[Users.lastname]}", it[Users.alias], it[Roles.label]!!)
        } from Users innerJoin Roles on Users.roleId eq Roles.id
                ).fetchAll()

    fun deleteAll() = client deleteAllFrom Users

    fun save(user: User) = (client insert user).transactional(operator)

    fun init() {
        (client createTableIfNotExists Users)
            .then(deleteAll())
            .then(save(User("John", "Doe", false, role_user_uuid, id = 123)))
            .then(save(User("Big", "Boss", true, role_admin_uuid, "TheBoss")))
            .block()
    }
}

@Repository
class RoleRepository(private val client: ReactorSqlClient) {
    fun deleteAll() = client deleteAllFrom Roles

    fun save(role: Role) = client insert role

    fun init() {
        (client createTableIfNotExists Roles)
            .then(deleteAll())
            .then(save(Role("user", role_user_uuid)))
            .then(save(Role("admin", role_admin_uuid)))
            .then(save(Role("god")))
            .block()
    }
}
