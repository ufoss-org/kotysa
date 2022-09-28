package com.sample

import io.ktor.server.application.*
import org.h2.jdbcx.JdbcConnectionPool
import org.kodein.di.*
import org.kodein.di.ktor.closestDI
import org.kodein.di.ktor.di
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.jdbc.sqlClient
import org.ufoss.kotysa.tables

fun Application.dataConfig() {
    di {
        import(dataModule)
    }
    // init DB
    val roleRepository by closestDI().instance<RoleRepository>()
    val userRepository by closestDI().instance<UserRepository>()

    roleRepository.createTable()
    userRepository.createTable()

    userRepository.deleteAll()
    roleRepository.deleteAll()

    roleRepository.insert()
    userRepository.insert()
}

private val h2Tables = tables().h2(Roles, Users)

private val dataModule = DI.Module(name = "data") {
    // create Kotysa SqlClient from jdbc DataSource
    bind<JdbcSqlClient>() with singleton {
        JdbcConnectionPool.create(
            "jdbc:h2:mem:///testdb;DB_CLOSE_DELAY=-1", "sa", "sa"
        )
            .sqlClient(h2Tables)
    }
    // Repositories
    bind<RoleRepository>() with provider { RoleRepository(instance()) }
    bind<UserRepository>() with provider { UserRepository(instance()) }
}   
