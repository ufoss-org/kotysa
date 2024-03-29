package com.sample

import io.ktor.server.application.*
import io.ktor.serialization.kotlinx.json.*
import io.r2dbc.spi.ConnectionFactories
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.kodein.di.*
import org.kodein.di.ktor.closestDI
import org.kodein.di.ktor.di
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.r2dbc.coSqlClient
import org.ufoss.kotysa.tables

fun Application.dataConfig() {
    di {
        import(dataModule)
    }
    // init DB
    val roleRepository by closestDI().instance<RoleRepository>()
    val userRepository by closestDI().instance<UserRepository>()
    runBlocking {
        roleRepository.createTable()
        userRepository.createTable()

        userRepository.deleteAll()
        roleRepository.deleteAll()

        roleRepository.insert()
        userRepository.insert()
    }
}

private val h2Tables = tables().h2(Roles, Users)

private val dataModule = DI.Module(name = "data") {
    // create Kotysa SqlClient from R2DBC ConnectionFactory
    bind<R2dbcSqlClient>() with singleton {
        ConnectionFactories.get("r2dbc:h2:mem:///testdb;DB_CLOSE_DELAY=-1").coSqlClient(h2Tables)
    }
    bind<Json>() with singleton { DefaultJson }
    // Repositories
    bind<RoleRepository>() with provider { RoleRepository(instance()) }
    bind<UserRepository>() with provider { UserRepository(instance()) }
}   
