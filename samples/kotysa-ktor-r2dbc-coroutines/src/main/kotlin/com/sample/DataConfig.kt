package com.sample

import io.ktor.server.application.*
import io.ktor.serialization.kotlinx.json.*
import io.r2dbc.spi.ConnectionFactories
import kotlinx.serialization.json.Json
import org.kodein.di.*
import org.kodein.di.ktor.closestDI
import org.kodein.di.ktor.di
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.tables

fun Application.dataConfig() {
    di {
        import(dataModule)
    }
    // init DB
    val roleRepository by closestDI().instance<RoleRepository>()
    roleRepository.init()
    val userRepository by closestDI().instance<UserRepository>()
    userRepository.init()
}

private val h2Tables = tables().h2(Roles, Users)

private val dataModule = DI.Module(name = "data") {
    // create Kotysa SqlClient from r2dbc ConnectionFactory
    bind<R2dbcSqlClient>() with singleton {
        ConnectionFactories.get("r2dbc:h2:mem:///testdb;DB_CLOSE_DELAY=-1").sqlClient(h2Tables)
    }
    bind<Json>() with singleton { DefaultJson }
    // Repositories
    bind<RoleRepository>() with provider { RoleRepository(instance()) }
    bind<UserRepository>() with provider { UserRepository(instance()) }
}   
